import { Component, OnInit } from '@angular/core';
import { CalendarEvent } from 'angular-calendar';
import { ApiConsultingService } from 'src/app/core/services/api-consulting.service';

@Component({
  selector: 'app-consulting-calendar',
  templateUrl: './consulting-calendar.component.html',
  styleUrls: ['./consulting-calendar.component.scss']
})
export class ConsultingCalendarComponent implements OnInit {
  viewDate: Date = new Date();
  events: CalendarEvent[] = [];

  fileControl: any;
  file!: File;
  

  constructor(private apiConsultingService: ApiConsultingService) { }

  ngOnInit() {
  
  }

  selectFile(event: any) {
    this.file = event.target.files.item(0);
  }

  upload() {
    this.apiConsultingService.upload(this.file).subscribe(data => {
      this.events = data
    });
  }

}
