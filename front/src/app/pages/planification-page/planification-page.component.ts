import { Component, OnInit } from '@angular/core';
import { Jury } from 'src/app/core/data/models/jury.model';
import { ApiJuryService } from 'src/app/core/services/api-jury.service';

@Component({
  selector: 'app-planification-page',
  templateUrl: './planification-page.component.html',
  styleUrls: ['./planification-page.component.scss']
})
export class PlanificationPageComponent implements OnInit {
  juries!: Jury[];

  refresh: any;


  constructor(
    private apiJuryService: ApiJuryService,
  ) { }

  ngOnInit(): void {
    this.getAllJuries();
    //this.refresh = setInterval(() => { this.getAllJuries() },  5000 );
  }

  getAllJuries(): void {
    this.apiJuryService.getAllJuries().subscribe(data => {
        this.juries = data;
      }
    );
  }

  addCreatedJury(jury: Jury): void {
    this.juries.push(jury);
  }

}