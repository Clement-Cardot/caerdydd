import { Component, Input } from '@angular/core';
import { Project } from '../../../core/data/models/project.model';
import { ApiProjectService } from '../../../core/services/api-project.service';


@Component({
  selector: 'subject-validation',
  templateUrl: './subject-validation.component.html',
  styleUrls: ['./subject-validation.component.scss']
})
export class SubjectValidationComponent {
  @Input() project!: Project;
  constructor(private projectService: ApiProjectService) { }

  validateSubject() {
    this.project.isValidated = true;
    console.log(this.project);
    this.projectService.updateProjectValidation(this.project).subscribe(); // TODO call the write fonction
  }
}
