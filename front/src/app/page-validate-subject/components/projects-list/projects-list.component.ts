import { Component, OnInit } from '@angular/core';
import { Project } from '../../../core/data/models/project.model';
import { ApiProjectService } from '../../../core/services/api-project.service';

@Component({
  selector: 'app-project-list',
  templateUrl: './projects-list.component.html',
  styleUrls: ['./projects-list.component.scss']
})
export class ProjectListComponent implements OnInit {
  projectList!: Project[];

  refresh: any;

  constructor(private projectService: ApiProjectService) {}

  ngOnInit(): void {
    this.getAllData();
  }

  getAllData(){
    this.projectService.getAllSubjects()
        .subscribe(data => {
            this.projectList = data;
          }
        );
  }
}
