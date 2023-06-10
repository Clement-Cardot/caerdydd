import { Component, OnInit, OnDestroy } from '@angular/core';
import { Project } from 'src/app/core/data/models/project.model';
import { ApiProjectService } from 'src/app/core/services/api-project.service';
import { ApiTeamService } from 'src/app/core/services/api-team.service';

@Component({
  selector: 'app-validate-subject-page',
  templateUrl: './validate-subject-page.component.html',
  styleUrls: ['./validate-subject-page.component.scss']
})
export class ValidateSubjectPageComponent implements OnInit, OnDestroy {
  projectList!: Project[];

  refresh: any;

  constructor(private projectService: ApiProjectService, private teamService: ApiTeamService) {}

  ngOnInit(): void {
    this.getAllData();
    this.refresh = setInterval(() => { this.getAllData() },  5000 );
  }

  ngOnDestroy(): void {
    clearInterval(this.refresh);
  }

  getAllData(){
    // recuperer les projets
    this.projectService.getAllProjects()
        .subscribe(data => {
            this.projectList = data;
          }
        );
    // recuperer les noms des equipes
    this.teamService.getAllTeams()
        .subscribe(data => {
            this.projectList.forEach(project => {
              project.teamName = data.find(team => team.projectDev.idProject == project.idProject)?.name;
            }
          );
          }
        );
  }
}