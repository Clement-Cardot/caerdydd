import { Component, Input } from '@angular/core';
import { ApiProjectService } from '../../../core/services/api-project.service';
import { Team } from 'src/app/core/data/models/team.model';


@Component({
  selector: 'subject-validation',
  templateUrl: './subject-validation.component.html',
  styleUrls: ['./subject-validation.component.scss']
})
export class SubjectValidationComponent {
  @Input() team!: Team;
  constructor(private projectService: ApiProjectService) { }

  validateSubject() {
    this.team.projectDev.isValidated = true;
    this.projectService.updateProjectValidation(this.team.projectDev).subscribe(); // TODO call the write fonction
  }
}
