import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Team } from 'src/app/core/data/models/team.model';
import { ApiTeamService } from 'src/app/core/services/api-team.service';
import { MyErrorStateMatcher } from 'src/app/pages/login-page/login-page.component';

@Component({
  selector: 'app-team-creation',
  templateUrl: './team-creation.component.html',
  styleUrls: ['./team-creation.component.scss']
})
export class TeamCreationComponent implements OnInit {
  teamCreationForm!: FormGroup;
  matcher = new MyErrorStateMatcher();
  nbTeamsFormControl = new FormControl('', Validators.required);
  @Output() componentDisplayed = new EventEmitter<Team[]>();

  constructor(
    private formBuilder: FormBuilder,
    private apiTeamService: ApiTeamService,
    private _snackBar: MatSnackBar
  ) { }

  ngOnInit(): void {
    this.teamCreationForm = this.formBuilder.group({
      nbTeams: this.nbTeamsFormControl
    });
  }

  createTeams() {
    if(this.teamCreationForm.invalid){
      return;
    } else {
      this.apiTeamService.createTeams(this.teamCreationForm.value.nbTeams).subscribe(
        response => {
          this.showSnackbar(response.length + " Equipes créées avec succès !");
          this.componentDisplayed.emit(response);
        },
        error => {
          this.showSnackbar("Une erreur est survenue lors de la création des équipes.");
        }
      );
    }
  }

  showSnackbar(message : string) {
    this._snackBar.open(message, "Fermer", {duration: 5000});
  }
}
