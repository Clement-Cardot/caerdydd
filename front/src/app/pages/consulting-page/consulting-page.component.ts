import { Component, OnInit, OnDestroy } from '@angular/core';
import { Consulting } from 'src/app/core/data/models/consulting.model';
import { ApiConsultingService } from 'src/app/core/services/api-consulting.service';

@Component({
  selector: 'app-consulting-page',
  templateUrl: './consulting-page.component.html',
  styleUrls: ['./consulting-page.component.scss'],
})
export class ConsultingPageComponent implements OnInit, OnDestroy{
  consultingFinishedList: Consulting[] = [];
  consultingProgrammedList: Consulting[] = [];
  consultingWaitingAcceptation: Consulting[] = [];

  refresh: any;
  
  constructor(private apiConsultingService: ApiConsultingService) { }

  ngOnInit(): void {
    this.getAllConsultingsForCurrentTeachingStaff();
    this.refresh = setInterval(() => { this.getAllConsultingsForCurrentTeachingStaff() }, 5000);
  }

  ngOnDestroy(): void {
    clearInterval(this.refresh);
  }

  getAllConsultingsForCurrentTeachingStaff(){
    this.apiConsultingService.getConsultingForCurrentTeachingStaff()
        .subscribe(data => {
            this.sortConsulting(data);
          }
        );

    this.apiConsultingService.getAllWaitingAcceptationConsultings()
    .subscribe(data => {
      data.forEach(consulting => {
          this.addOrUpdateToList(this.consultingWaitingAcceptation, consulting);
        });
      }
    );
  }

  addOrUpdateToList(consultingList: Consulting[] ,consulting: Consulting) : void {
    let index = consultingList.findIndex(c => c.idConsulting === consulting.idConsulting);
    if(index == -1) {
      consultingList.push(consulting);
    } else {
      consultingList[index] = consulting;
    }
  }

  sortConsulting(consultingList: Consulting[]) : void {
    for(let consulting of consultingList) {
      if(consulting.plannedTimingConsulting.datetimeEnd.getTime() < new Date().getTime()) {
        this.addOrUpdateToList(this.consultingFinishedList, consulting);
      } else {
        this.addOrUpdateToList(this.consultingProgrammedList, consulting);
      }
    }
    this.consultingFinishedList.sort((a, b) => a.plannedTimingConsulting.datetimeEnd.getTime() - b.plannedTimingConsulting.datetimeEnd.getTime());
    this.consultingProgrammedList.sort((a, b) => a.plannedTimingConsulting.datetimeEnd.getTime() - b.plannedTimingConsulting.datetimeEnd.getTime());
  }

}
