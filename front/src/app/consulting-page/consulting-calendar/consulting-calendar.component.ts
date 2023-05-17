import { Component, OnDestroy, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { CalendarEvent } from 'angular-calendar';
import { ApiConsultingService } from 'src/app/core/services/api-consulting.service';
import { ClickedEventDialogComponent } from '../clicked-event-dialog/clicked-event-dialog.component';
import { PlannedTimingConsulting } from 'src/app/core/data/models/planned-timing-consulting.model';
import { UserDataService } from 'src/app/core/services/user-data.service';
import { User } from 'src/app/core/data/models/user.model';

class ClickEvent {
  event!: CalendarEvent;
  sourceEvent!: MouseEvent | KeyboardEvent;
}
@Component({
  selector: 'app-consulting-calendar',
  templateUrl: './consulting-calendar.component.html',
  styleUrls: ['./consulting-calendar.component.scss']
})
export class ConsultingCalendarComponent implements OnInit, OnDestroy {
  
  currentUser: User | null = null;
  
  viewDate: Date = new Date();
  events: PlannedTimingConsulting[] = [];
  refresh: any;

  constructor(
    private userDataService: UserDataService,
    private apiConsultingService: ApiConsultingService, 
    public dialog: MatDialog
  ) {}

  ngOnInit() {
    this.userDataService.getCurrentUser().subscribe((user: User | null) => {
      this.currentUser = user;
      this.loadConsultings();
      this.refresh = setInterval(() => { this.loadConsultings() },  5000 );
    });
  }

  ngOnDestroy() {
    if (this.refresh) {
      clearInterval(this.refresh);
    }
  }

  loadConsultings() {
    this.apiConsultingService.getAllConsultings().subscribe(data => {
      //console.log(data);
      this.events = data;
      this.filterConsultingsByRole();
    });
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
      let result = event.availabilities.find(availability => availability.teachingStaff.user.id == this.currentUser?.id);
      if (result?.isAvailable) {
        event.color = {primary: '#1e90ff', secondary: '#D1E8FF'};
      }
      else {
        event.color = {primary: '#ad2121', secondary: '#FAE3E3'};
      }
    });
  }

  filterConsultingsPlanning() {
    // TODO
  }

  clickOnEvent(clickEvent : ClickEvent) {
    this.dialog.open(ClickedEventDialogComponent, { data: { event: clickEvent.event }});
  }

}