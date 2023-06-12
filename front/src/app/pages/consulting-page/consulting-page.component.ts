import { Component } from '@angular/core';
import { Consulting } from 'src/app/core/data/models/consulting.model';
import { ApiConsultingService } from 'src/app/core/services/api-consulting.service';

@Component({
  selector: 'app-consulting-page',
  templateUrl: './consulting-page.component.html',
  styleUrls: ['./consulting-page.component.scss'],
})
export class ConsultingPageComponent {
  consultingsInfra: Consulting[] = [];
  consultingsDev: Consulting[] = [];
  consultingsModeling: Consulting[] = [];

  consultings: Consulting[] = [];

  constructor(private apiConsultingService: ApiConsultingService) {}

  ngOnInit(): void {
    this.getAllConsultings();
  }

  getAllConsultings() {
    this.apiConsultingService.getAllConsultings().subscribe((data) => {
      console.log(data);
      this.consultingsDev = data.filter((c) => c.speciality == 'development');
      this.consultingsInfra = data.filter(
        (c) => c.speciality == 'infrastructure'
      );
      this.consultingsModeling = data.filter((c) => c.speciality == 'modeling');
      console.log('Infra :' + this.consultingsInfra);
      console.log('Dev :' + this.consultingsDev);
      console.log('Model :' + this.consultingsModeling);
      this.consultings = this.consultings.concat(
        this.consultingsInfra,
        this.consultingsDev,
        this.consultingsModeling
      );
      console.log('Consultings :' + this.consultings);
    });
  }
}
