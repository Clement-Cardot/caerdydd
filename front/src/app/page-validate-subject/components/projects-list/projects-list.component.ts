import { Component, OnInit } from '@angular/core';
import { Project } from '../../../core/data/models/project.model';
import { ApiProjectService } from '../../../core/services/api-project.service';
import { ApiTeamService } from 'src/app/core/services/api-team.service';

@Component({
  selector: 'app-project-list',
  templateUrl: './projects-list.component.html',
  styleUrls: ['./projects-list.component.scss']
})
export class ProjectListComponent implements OnInit {
  projectList!: Project[];

  refresh: any;

  constructor(private projectService: ApiProjectService, private teamService: ApiTeamService) {}

  ngOnInit(): void {
    this.getAllData();
  }

  getAllData(){
    // recuperer les projets
    this.projectService.getAllSubjects()
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
