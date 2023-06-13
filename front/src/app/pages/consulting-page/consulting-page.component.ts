import { Component } from '@angular/core';
import { Consulting } from 'src/app/core/data/models/consulting.model';
import { ApiConsultingService } from 'src/app/core/services/api-consulting.service';

@Component({
  selector: 'app-consulting-page',
  templateUrl: './consulting-page.component.html',
  styleUrls: ['./consulting-page.component.scss']
})
export class ConsultingPageComponent {
  consultingFinishedList: Consulting[] = [];
  consultingProgrammedList: Consulting[] = [];
  
  constructor(private consultingService: ApiConsultingService) { }

  ngOnInit(): void {
    this.getAllConsultingsForCurrentTeachingStaff();
  }

  getAllConsultingsForCurrentTeachingStaff(){
    this.consultingService.getConsultingForCurrentTeachingStaff()
        .subscribe(data => {
            this.sortConsulting(data);
          }
        );
  }

  sortConsulting(consultingList: Consulting[]) : void {
    for(let consulting of consultingList) {
      if(consulting.plannedTimingConsulting.datetimeEnd.getTime() < new Date().getTime()) {
        this.consultingFinishedList.push(consulting);
      } else {
        console.log("Consulting à venir")
        this.consultingProgrammedList.push(consulting);
      }
    }
    this.consultingFinishedList.sort((a, b) => a.plannedTimingConsulting.datetimeEnd.getTime() - b.plannedTimingConsulting.datetimeEnd.getTime());
    this.consultingProgrammedList.sort((a, b) => a.plannedTimingConsulting.datetimeEnd.getTime() - b.plannedTimingConsulting.datetimeEnd.getTime());
  }
  
}
