import { Component, Input } from '@angular/core';
import { ApiProjectService } from '../../../core/services/api-project.service';
import { Team } from 'src/app/core/data/models/team.model';
import { MatSnackBar } from '@angular/material/snack-bar';


@Component({
  selector: 'subject-validation',
  templateUrl: './subject-validation.component.html',
  styleUrls: ['./subject-validation.component.scss']
})
export class SubjectValidationComponent {
  @Input() team!: Team;
  constructor(
    private projectService: ApiProjectService,
    private _snackbar: MatSnackBar
    ) { }

  validateSubject() {
    this.team.projectDev.isValidated = true;
    try {
      this.projectService.updateProjectValidation(this.team.projectDev).subscribe();
      this._snackbar.open("Le sujet a bien été validé", "Fermer", {
        duration: 5000,
      });
    } catch (error) {
      this.team.projectDev.isValidated = false;
    }
  }
}
