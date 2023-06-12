import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { FileInput } from 'ngx-material-file-input';
import { Team } from 'src/app/core/data/models/team.model';
import { ApiUploadFileService } from 'src/app/core/services/api-upload-file.service';

@Component({
  selector: 'app-project-file',
  templateUrl: './project-file.component.html',
  styleUrls: ['./project-file.component.scss']
})
export class ProjectFileComponent implements OnInit {
  @Input() team!: Team;
  @Input() fileName!: string;
  fullFileName!: string;

  importFileForm!: FormGroup;
  fileFormControl = new FormControl('', [Validators.required]);

  errorMessage!: string;

  constructor(private formBuilder: FormBuilder, private uploadFileService: ApiUploadFileService, private _snackBar: MatSnackBar) {
    this.importFileForm = this.formBuilder.group({
      file: this.fileFormControl
    });
  }

  ngOnInit(): void {
    this.fullFileName = this.getFullFileName();
  }

  getFullFileName(): string {
    switch (this.fileName) {
      case 'teamScopeStatement':
        return 'Cahier des charges';
      case 'analysis':
        return 'Analyse du cahier des charges';
      case 'finalTeamScopeStatement':
        return 'Cahier des charges final';
      case 'report':
        return 'Rapport';
      default:
        return 'ERROR';
    }
  }

  isThereFile(fileName: string): boolean {
    switch (fileName) {
      case 'teamScopeStatement':
        return (this.team.filePathScopeStatement != null);
      case 'analysis':
        return (this.team.filePathScopeStatementAnalysis != null);
      case 'finalTeamScopeStatement':
        return (this.team.filePathFinalScopeStatement != null);
      case 'report':
        return (this.team.filePathReport != null);
      default:
        return false;
    }
  }

  isUploadDisabled(): boolean {
    if(this.importFileForm.invalid) return true;
    if((this.fileName === 'teamScopeStatement') && (this.isThereFile('finalTeamScopeStatement'))) {
      return true;
    }
    return false;
  }

  upload(): void {
    this.fileFormControl.setErrors({'apiError': null});
    this.fileFormControl.updateValueAndValidity();
    if (this.importFileForm.invalid) {
      console.error('invalid form');
    } else {
      const file_form: FileInput = this.importFileForm.get('file')?.value;
      const file = file_form.files[0];
      this.uploadFileService.upload(file, this.team.idTeam, this.fileName).subscribe(
        data => {
          this.showSuccess();
        },
        error => {
          this.showError(error)
        },
      );
    }
  }

  download(): void {
    this.uploadFileService.download(this.team.idTeam, this.fileName).subscribe(response=>
      {
        let fileName = response.headers.get('content-disposition').split(';')[2].split('=')[1];
        fileName = fileName.slice(1, fileName.length - 1);
        let blob : Blob = response.body as Blob;
        let a = document.createElement('a');
        a.download = fileName;
        a.href = window.URL.createObjectURL(blob);
        a.click();
      },
      error => {
        {this.showErrorDownload()}
      });
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

  showErrorDownload() {
    this._snackBar.open("Une erreur est survenue lors du téléchargement", "Fermer", {
      duration: 5000,
    });
  }
}
