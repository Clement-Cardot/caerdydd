import { Component, OnInit } from '@angular/core';
import { Project } from '../../../core/data/models/project.model';
import { ProjectService } from '../../../core/services/project.service';

@Component({
  selector: 'app-view-validate-subjects',
  templateUrl: './projects-list.component.html',
  styleUrls: ['./projects-list.component.scss']
})
export class ProjectListComponent implements OnInit {
  projectList!: Project[];
  constructor(private projectService: ProjectService) {}

  ngOnInit() {
    this.projectList = this.projectService.getAllValidateSubject();
  }
}
