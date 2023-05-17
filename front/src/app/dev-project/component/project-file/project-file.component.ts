import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Observable } from 'rxjs';
import { FileInput } from 'ngx-material-file-input';
import { ApiTeamService } from 'src/app/core/services/api-team.service';
import { ApiUploadFileService } from 'src/app/core/services/api-upload-file.service';
import { UserDataService } from 'src/app/core/services/user-data.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ApiTeamMemberService } from 'src/app/core/services/api-team-member.service';
import { User } from 'src/app/core/data/models/user.model';
import { Team } from 'src/app/core/data/models/team.model';


function linkValidator(control: AbstractControl): { [key: string]: any } | null {
  const value = control.value;
  const isValidUrl = /^(https?:\/\/)?([\da-z.-]+)\.([a-z.]{2,6})([/\w.-]*)*\/?/.test(value);

  return isValidUrl ? null : { invalidLink: true };
}

@Component({
  selector: 'app-project-file',
  templateUrl: './project-file.component.html',
  styleUrls: ['./project-file.component.scss']
})
export class ProjectFileComponent implements OnInit {

  user!: User | null;
  testBookLinkForm: FormGroup;
  currentTeam!: Team | null;

  isNotFinalScope!: boolean;

  importTSSform: FormGroup;
  importAnalysisform: FormGroup;
  importFTSSform: FormGroup;

  fileFormControl = new FormControl([Validators.required]);

  errorMessage!: string;

  constructor(private apiTeamMemberService: ApiTeamMemberService, private apiTeamService: ApiTeamService, private uploadFileService: ApiUploadFileService, public userDataService: UserDataService, private formBuilder: FormBuilder, private _snackBar: MatSnackBar) {
    this.isNotFinalScope = true
    this.importTSSform = this.formBuilder.group({
      file: this.fileFormControl
    });
    this.importAnalysisform = this.formBuilder.group({
      file: this.fileFormControl
    });
    this.importFTSSform = this.formBuilder.group({
      file: this.fileFormControl
    });
    this.testBookLinkForm = this.formBuilder.group({
      testBookLink: ['', [Validators.required, linkValidator]]
    });
  }

  public ngOnInit():void {
    this.userDataService.getCurrentUser().subscribe((user: User | null) => {
      this.user = user;
      if (user?.teamId) {
        this.getTeam(user.teamId);
      }
      this.isFinalStateScope();
    });
  }

  upload(fileName: string) {
    if (this.user != null) {
      this.fileFormControl.setErrors({'apiError': null});
      this.fileFormControl.updateValueAndValidity();
      if(this.importTSSform.invalid){
        console.log("Invalid form");
      } else {
        const file_form: FileInput = this.importTSSform.get('file')?.value;
        const file = file_form.files[0];
        this.apiTeamMemberService.getTeamMemberById(this.user.id).subscribe(value => {
          this.uploadFileService.upload(file, value.idTeam, fileName).subscribe(
            data => {
              this.showSuccess();
              this.isNotFinalScope = false;
            },
            error => {this.showError(error)},
          );
        });
      }
    }

  }

  isFinalStateScope() {
    if (this.user != null) {
      this.apiTeamMemberService.getTeamMemberById(this.user.id).subscribe(value => {
        this.apiTeamService.getTeam(value.idTeam).subscribe(value => {
          if(value.filePathFinalScopeStatement != null) {
            this.isNotFinalScope = false;
          }
        })
      });
    }
  }

  isCurrentUserInTeam(): boolean {
    console.log(this.currentTeam);
    return !!(this.user && this.currentTeam);
  }

  isProjectDev(): boolean {
    return !!(this.currentTeam && this.currentTeam.idProjectDev !== null);
  }

  onSubmit(): void {
    if (this.testBookLinkForm.valid && this.user && this.currentTeam) {
      this.apiTeamService
        .addTestBookLink(this.currentTeam.idTeam, this.testBookLinkForm.value.testBookLink)
        .subscribe(team => {
          this.currentTeam = team;
          console.log('Lien TestBook ajouté avec succès');
          this.openSnackBar();
        });
    }
  }

  getTeam(teamId: number) {
    this.apiTeamService.getTeam(teamId).subscribe(
      (team: Team) => {
        this.currentTeam = team;
      },
      (error) => {
        console.error("Error getting team:", error);
      }
    );
  }

  openSnackBar() {
    this._snackBar.open("Lien TestBook ajouté avec succès", "Fermer", { duration: 5000 });
  }

  showSuccess() {
    this._snackBar.open("Import du fichier avec succès", "Fermer", {
      duration: 5000,
    });
  }

  showError(error: { status: number; }) {
    this.fileFormControl.setErrors({apiError: true});
    switch (error.status) {
      case 415:
        this.errorMessage = "Le fichier n'est pas au bon format";
        break;
      case 500:
        this.errorMessage = "Une erreur est survenue, veuillez contacter l'administrateur";
        break;
      default:
        this.errorMessage = "Une erreur est survenue, veuillez contacter l'administrateur";
    }
  }
}
