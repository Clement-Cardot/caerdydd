import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Observable } from 'rxjs';
import { FileInput } from 'ngx-material-file-input';
import { ApiTeamService } from 'src/app/core/services/api-team.service';
import { ApiUploadFileService } from 'src/app/core/services/api-upload-file.service';
import { UserDataService } from 'src/app/core/services/user-data.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ApiTeamMemberService } from 'src/app/core/services/api-team-member.service';
import { User } from 'src/app/core/data/models/user.model';

@Component({
  selector: 'app-project-file',
  templateUrl: './project-file.component.html',
  styleUrls: ['./project-file.component.scss']
})
export class ProjectFileComponent implements OnInit {

  user!: User | null;

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
  }

  public ngOnInit():void {
    this.userDataService.getCurrentUser().subscribe((user: User | null) => {
      this.user = user;
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
