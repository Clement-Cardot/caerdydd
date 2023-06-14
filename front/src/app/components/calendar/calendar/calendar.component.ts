import { Component, OnDestroy, OnInit } from '@angular/core';
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
import { Observable, Subject, interval } from 'rxjs';


class ClickEvent {
  event!: CalendarEvent;
  sourceEvent!: MouseEvent | KeyboardEvent;
}

@Component({
  selector: 'app-calendar',
  templateUrl: './calendar.component.html',
  styleUrls: ['./calendar.component.scss'],
})
export class CalendarComponent implements OnInit, OnDestroy {
  
  currentUser: User | undefined = undefined;
  
  viewDate: Date = new Date();
  events: CalendarEvent[] = [];
  refresh: any;
  refreshCalendar: Subject<void> = new Subject();
  currentUserSubscription: any;


  constructor(
    private userDataService: UserDataService,
    private apiConsultingService: ApiConsultingService, 
    private apiPresentationService: ApiPresentationService,
    public dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.currentUserSubscription = this.userDataService.getCurrentUser()
    .subscribe((user: User | undefined) => {
      this.currentUser = user;
      this.getData();
    });
    this.refresh = setInterval(() => { this.getData() }, 10000 );
  }
  
  ngOnDestroy(): void {
    clearInterval(this.refresh);
    this.currentUserSubscription.unsubscribe();
  }

  getData() {
    if (this.currentUser) {
      let currentUserRoles = this.currentUser.getRoles();
      if (currentUserRoles.includes("PLANNING_ROLE")) {
        this.loadAllEvents();
      } else if (currentUserRoles.includes("OPTION_LEADER_ROLE")) {
        this.loadOptionLeaderEvents();
      } else if (currentUserRoles.includes("TEACHING_STAFF_ROLE")) {
        this.loadTeachingStaffEvents(this.currentUser.id);
      } else if (currentUserRoles.includes("TEAM_MEMBER_ROLE")) {
        this.loadTeamMemberEvents(this.currentUser.id);
      }
    }
  }

  loadAllEvents() {
    this.apiConsultingService.getAllPlannedTimingConsulting()
    .subscribe(data => {
      this.updateEvents(data);
    });
    this.apiPresentationService.getAllPresentations()
    .subscribe(data => {
      this.updateEvents(data);
    });
  }

  loadOptionLeaderEvents() {
    this.apiConsultingService.getAllPlannedTimingConsulting()
    .subscribe(data => {
      this.updateEvents(this.showTeachingStaffAvailabilitiesForConsulting(data));
    });
    this.apiPresentationService.getAllPresentations()
    .subscribe(data => {
      this.updateEvents(data);
    });
  }

  loadTeachingStaffEvents(idUser: number) {
    this.apiConsultingService.getAllPlannedTimingConsulting()
    .subscribe(data => {
      this.updateEvents(this.showTeachingStaffAvailabilitiesForConsulting(data));
    });
    this.apiPresentationService.getTeachingStaffPresentations(idUser)
      .subscribe(data => {
        this.updateEvents(data);
      });
  }

  loadTeamMemberEvents(idUser: number) {
    this.apiConsultingService.getAllPlannedTimingConsulting()
    .subscribe(data => {
      this.updateEvents(data);
    });
    this.apiPresentationService.getTeamMemberPresentations(idUser)
      .subscribe(data => {
        this.updateEvents(data);
      });
  }

  updateEvents(newEvents: CalendarEvent[]) {
    newEvents.forEach(newEvent => {
      let correspondingIndex = this.events.findIndex(event => event.id == newEvent.id);
      if (correspondingIndex != -1) {
        this.events.splice(correspondingIndex, 1);
        this.events.push(newEvent);
        this.refreshCalendar.next();
      }
      else {
        this.events.push(newEvent);
        this.refreshCalendar.next();
      }
    });
  }

  showTeachingStaffAvailabilitiesForConsulting(events: PlannedTimingConsulting[]): PlannedTimingConsulting[] {
    events.forEach(event => {
        let result = event.availabilities.find((availability: PlannedTimingAvailability) => 
            availability.teachingStaff.user.id == this.currentUser?.id
        );
        if (result?.isAvailable) {
            event.color = {primary: '#1e90ff', secondary: '#D1E8FF'};
        }
        else {
            event.color = {primary: '#ad2121', secondary: '#FAE3E3'};
        }
    });
    return events;
  }

  clickOnEvent(clickEvent : ClickEvent) {
    if (clickEvent.event instanceof Presentation) {
      //TODO : open presentation dialog
    } else if (clickEvent.event instanceof PlannedTimingConsulting) {
      this.dialog.open(ClickedConsultingDialogComponent, { data: { event: clickEvent.event }})
            .afterClosed().subscribe(result => {
              if (result) {
                this.getData()
              }
      });
    }
  }
}
