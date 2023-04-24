import { Component, OnDestroy, OnInit } from '@angular/core';
import { CalendarEvent } from 'angular-calendar';
import { ApiConsultingService } from 'src/app/core/services/api-consulting.service';

@Component({
  selector: 'app-consulting-calendar',
  templateUrl: './consulting-calendar.component.html',
  styleUrls: ['./consulting-calendar.component.scss']
})
export class ConsultingCalendarComponent implements OnInit, OnDestroy {
  viewDate: Date = new Date();
  events: CalendarEvent[] = [];

  refresh: any;
  

  constructor(private apiConsultingService: ApiConsultingService) { }

  ngOnInit() {
    this.updateConsultings();
    this.refresh = setInterval(() => { this.updateConsultings() },  5000 );
  }

  ngOnDestroy() {
    if (this.refresh) {
      clearInterval(this.refresh);
    }
  }

  updateConsultings() {
    this.apiConsultingService.getAllConsultings().subscribe(data => {
      console.log(data);
      this.events = data;
    });
  }

}
