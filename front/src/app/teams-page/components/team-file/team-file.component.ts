import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { FileInput } from 'ngx-material-file-input';
import { ApiTeamService } from 'src/app/core/services/api-team.service';
import { ApiUploadFileService } from 'src/app/core/services/api-upload-file.service';
import { UserDataService } from 'src/app/core/services/user-data.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-team-file',
  templateUrl: './team-file.component.html',
  styleUrls: ['./team-file.component.scss']
})
export class TeamFileComponent implements OnInit {

  importTSSform: FormGroup;
  fileSelected = false;

  fileFormControl = new FormControl([Validators.required]);

  errorMessage!: string;

  constructor(private apiTeamService: ApiTeamService, private uploadFileService: ApiUploadFileService, public userDataService: UserDataService, private formBuilder: FormBuilder, private _snackBar: MatSnackBar) {
    this.importTSSform = this.formBuilder.group({
      file: this.fileFormControl
    });
  }

  public ngOnInit():void {
  }

  upload() {
    this.fileFormControl.setErrors({'apiError': null});
    this.fileFormControl.updateValueAndValidity();
    if(this.importTSSform.invalid){
      console.log("Invalid form");
    } else {
      const file_form: FileInput = this.importTSSform.get('file')?.value;
      const file = file_form.files[0];
      console.log(file);
      this.uploadFileService.upload(file, 1, "teamScopeStatement").subscribe(
        data => {this.showSuccess()},
        error => {this.showError(error)},
      );
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
