import { Component, OnInit } from '@angular/core';
import { Jury } from 'src/app/core/data/models/jury.model';
import { Project } from 'src/app/core/data/models/project.model';
import { ApiJuryService } from 'src/app/core/services/api-jury.service';
import { ApiProjectService } from 'src/app/core/services/api-project.service';

@Component({
  selector: 'app-planification-page',
  templateUrl: './planification-page.component.html',
  styleUrls: ['./planification-page.component.scss']
})
export class PlanificationPageComponent implements OnInit {
  juries!: Jury[];
  projects!: Project[];

  refresh: any;


  constructor(
    private apiJuryService: ApiJuryService,
    private apiProjectService: ApiProjectService
  ) { }

  ngOnInit(): void {
    this.getAllData();
    this.refresh = setInterval(() => { this.getAllData() },  5000 );
  }

  getAllData(): void {
    this.getAllJuries();
    this.getAllProjects();
  }

  getAllJuries(): void {
    this.apiJuryService.getAllJuries().subscribe(data => {
        this.juries = data;
      }
    );
  }

  getAllProjects(): void {
    this.apiProjectService.getAllProjects().subscribe(data => {
        this.projects = data;
      }
    );
  }

  addCreatedJury(jury: Jury): void {
    this.juries.push(jury);
  }

}