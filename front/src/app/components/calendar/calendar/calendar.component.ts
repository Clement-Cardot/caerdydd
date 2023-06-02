import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { CalendarEvent } from 'angular-calendar';
import { ApiConsultingService } from 'src/app/core/services/api-consulting.service';
import { PlannedTimingConsulting } from 'src/app/core/data/models/planned-timing-consulting.model';
import { UserDataService } from 'src/app/core/services/user-data.service';
import { User } from 'src/app/core/data/models/user.model';
import { Team } from 'src/app/core/data/models/team.model';
import { ApiPresentationService } from 'src/app/core/services/api-presentation.service';
import { Presentation } from 'src/app/core/data/models/presentation.model';
import { PlannedTimingAvailability } from 'src/app/core/data/models/planned-timing-availability.model';
import { TeamMember } from 'src/app/core/data/models/team-member.model';
import { ApiTeamMemberService } from 'src/app/core/services/api-team-member.service';
import { ApiTeamService } from 'src/app/core/services/api-team.service';
import { ClickedConsultingDialogComponent } from '../clicked-consulting-dialog/clicked-consulting-dialog.component';

class ClickEvent {
  event!: CalendarEvent;
  sourceEvent!: MouseEvent | KeyboardEvent;
}

@Component({
  selector: 'app-calendar',
  templateUrl: './calendar.component.html',
  styleUrls: ['./calendar.component.scss']
})
export class CalendarComponent implements OnInit {
  
  currentUser: User | null = null;
  currentTeam: Team | null = null;
  
  viewDate: Date = new Date();
  events: CalendarEvent[] = [];
  refresh: any;

  constructor(
    private userDataService: UserDataService,
    private apiConsultingService: ApiConsultingService, 
    private apiPresentationService: ApiPresentationService,
    private apiTeamMemberService: ApiTeamMemberService,
    private apiTeamService: ApiTeamService,
    public dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.userDataService.getCurrentUser().subscribe((user: User | null) => {
      this.currentUser = user;
      if (this.currentUser) {
        let currentUserRoles = this.currentUser.getRoles();
        if (currentUserRoles.includes("TEAM_MEMBER_ROLE")) {
          this.getTeamMember(this.currentUser.id);
        } else {
          this.loadEvents();
          this.refresh = setInterval(() => { this.loadEvents() },  10000 );
        }
      }
    });
  }

  getTeamMember(userId: number) {
    this.apiTeamMemberService.getTeamMemberById(userId).subscribe(
      (teamMember: TeamMember) => {
        if (teamMember.idTeam !== null) {
          this.getTeam(teamMember.idTeam);
        } else {
          console.error("Current user is not a team member");
        }
      },
      (error) => {
        console.error("Error getting team member:", error);
      }
    );
  }
  
  getTeam(teamId: number) {
    this.apiTeamService.getTeam(teamId).subscribe(
      (team: Team) => {
        this.currentTeam = team;
        this.loadEvents();
        this.refresh = setInterval(() => { this.loadEvents() }, 10000 );
      },
      (error) => {
        console.error("Error getting team:", error);
      }
    );
  }

  ngOnDestroy() {
    if (this.refresh) {
      clearInterval(this.refresh);
    }
  }

  loadEvents() {
    this.events=[];
    this.apiConsultingService.getAllConsultings().subscribe(data => {
      this.events = [...this.events, ...data];
      this.filterConsultingsByRole();
    });

    this.filterPresentationsByRole();
    
}

  filterConsultingsByRole() {
    let currentUserRoles = this.currentUser?.getRoles();
    if (currentUserRoles == null || currentUserRoles == undefined || currentUserRoles.length == 0) {
      return;
    }
    else if (currentUserRoles.includes("TEAM_MEMBER_ROLE")) {
      this.filterConsultingsTeamMember();

    }
    else if (currentUserRoles.includes("TEACHING_STAFF_ROLE")) {
      this.filterConsultingsTeachingStaff();
    }
    else if (currentUserRoles.includes("PLANNING_ROLE")) {
      this.filterConsultingsPlanning();
    }
  }

  filterConsultingsTeamMember() {
    // TODO
  }

  filterConsultingsTeachingStaff() {
    this.events.forEach(event => {
        if (event instanceof PlannedTimingConsulting) {
            let result = event.availabilities.find((availability: PlannedTimingAvailability) => 
                availability.teachingStaff.user.id == this.currentUser?.id
            );
            if (result?.isAvailable) {
                event.color = {primary: '#1e90ff', secondary: '#D1E8FF'};
            }
            else {
                event.color = {primary: '#ad2121', secondary: '#FAE3E3'};
            }
        }
    });
}

  filterPresentationsByRole() {
    let currentUserRoles = this.currentUser?.getRoles();
    if (currentUserRoles == null || currentUserRoles == undefined || currentUserRoles.length == 0) {
      return;
    }
  
    this.events = [];
    
    if (currentUserRoles.includes("TEAM_MEMBER_ROLE")) {
      this.filterPresentationsTeamMember();
    }
    else if (currentUserRoles.includes("TEACHING_STAFF_ROLE")) {
      this.filterPresentationsTeachingStaff();
    }
    else if (currentUserRoles.includes("PLANNING_ROLE")) {
      this.filterPresentationsPlanning();
    }
  }
  

 filterPresentationsTeamMember() {
    if (this.currentTeam) {
      this.apiPresentationService.getTeamPresentations(this.currentTeam.idTeam).subscribe(data => {
        this.events = [...this.events, ...data];
      });
    }
  }

filterPresentationsTeachingStaff() {
    if (this.currentUser) {
      this.apiPresentationService.getTeachingStaffPresentations(this.currentUser.id).subscribe(data => {
        this.events = [...this.events, ...data];      });
    }
}

filterPresentationsPlanning() {
    this.apiPresentationService.listAllPresentations().subscribe(data => {
      this.events = [...this.events, ...data];    });
}

  filterConsultingsPlanning() {
    // TODO
  }


  clickOnEvent(clickEvent : ClickEvent) {
    if (clickEvent.event instanceof Presentation) {
    
    } else if (clickEvent.event instanceof PlannedTimingConsulting) {
      this.dialog.open(ClickedConsultingDialogComponent, { data: { event: clickEvent.event }});
    }
  }

}