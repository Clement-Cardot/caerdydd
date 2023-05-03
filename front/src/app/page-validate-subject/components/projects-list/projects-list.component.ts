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
  constructor(private projectService: ApiProjectService) {}

  ngOnInit() {
    this.projectList = this.projectService.getAllValidateSubject();
  }
}
