import { Component, Input } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Team } from 'src/app/core/data/models/team.model';
import { ApiTeamService } from 'src/app/core/services/api-team.service';

function linkValidator(control: AbstractControl): { [key: string]: any } | null {
  const value = control.value;
  const isValidUrl = /^(https?:\/\/)?([\da-z.-]+)\.([a-z.]{2,6})([/\w.-]*)*\/?/.test(value);

  return isValidUrl ? null : { invalidLink: true };
}

@Component({
  selector: 'app-test-book-link',
  templateUrl: './test-book-link.component.html',
  styleUrls: ['./test-book-link.component.scss']
})
export class TestBookLinkComponent {
  @Input() team!: Team;

  testBookLinkForm: FormGroup;

  constructor(private formBuilder: FormBuilder, private _snackBar: MatSnackBar, private apiTeamService: ApiTeamService) {
    this.testBookLinkForm = this.formBuilder.group({
      testBookLink: ['', [Validators.required, linkValidator]]
    });
  }

  onSubmit(): void {
    if (this.testBookLinkForm.valid && this.team) {
      this.team.testBookLink = this.testBookLinkForm.value.testBookLink;
      this.apiTeamService.addTestBookLink(this.team)
        .subscribe(team => {
          this.team = team;
          this.openSnackBar();
        });
    }
  }

  openSnackBar() {
    this._snackBar.open("Lien TestBook ajouté avec succès", "Fermer", { duration: 5000 });
  }
}
