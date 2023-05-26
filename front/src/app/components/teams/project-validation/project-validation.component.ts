import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Project } from '../../../core/data/models/project.model';
import { ApiProjectService } from '../../../core/services/api-project.service';


@Component({
  selector: 'project-validation',
  templateUrl: './project-validation.component.html',
  styleUrls: ['./project-validation.component.scss']
})
export class ProjectValidationComponent {
  @Input() project!: Project;
  constructor(private projectService: ApiProjectService) { }

  validateSubject() {
    this.project.isValidated = true;
    console.log(this.project);
    this.projectService.updateProjectValidation(this.project).subscribe(); // TODO call the write fonction
  }
}
