import { Component, OnDestroy, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { CalendarEvent } from 'angular-calendar';
import { ApiConsultingService } from 'src/app/core/services/api-consulting.service';
import { ClickedEventDialogComponent } from '../clicked-event-dialog/clicked-event-dialog.component';
import { PlannedTimingConsulting } from 'src/app/core/data/models/planned-timing-consulting.model';

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
  viewDate: Date = new Date();
  events: PlannedTimingConsulting[] = [];
  refresh: any;
  

  constructor(private apiConsultingService: ApiConsultingService, public dialog: MatDialog) { }

  ngOnInit() {
    this.loadConsultings();
    this.refresh = setInterval(() => { this.loadConsultings() },  5000 );
  }

  ngOnDestroy() {
    if (this.refresh) {
      clearInterval(this.refresh);
    }
  }

  loadConsultings() {
    this.apiConsultingService.getAllConsultings().subscribe(data => {
      console.log(data);
      this.events = data;
    });
  }

  clickOnEvent(clickEvent : ClickEvent) {
    this.dialog.open(ClickedEventDialogComponent, { data: { event: clickEvent.event }});
  }

}