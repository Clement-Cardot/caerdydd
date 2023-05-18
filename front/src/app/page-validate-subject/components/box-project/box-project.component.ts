import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Project } from '../../../core/data/models/project.model';
import { ApiProjectService } from '../../../core/services/api-project.service';


@Component({
  selector: 'box-project',
  templateUrl: './box-project.component.html',
  styleUrls: ['./box-project.component.scss']
})
export class BoxProjectComponent {
  @Input() project!: Project;
  constructor(private projectService: ApiProjectService) { }

  validateSubject() {
    this.project.isValidated = true;
    console.log(this.project);
    this.projectService.updateProjectValidation(this.project).subscribe(); // TODO call the write fonction
  }
}
