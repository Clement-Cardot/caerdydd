import { Component } from '@angular/core';
import { FormGroup, FormControl, Validators, FormBuilder } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { FileInput } from 'ngx-material-file-input';
import { User } from 'src/app/core/data/models/user.model';
import { ApiUserService } from 'src/app/core/services/api-user.service';

@Component({
  selector: 'app-student-import',
  templateUrl: './student-import.component.html',
  styleUrls: ['./student-import.component.scss']
})
export class StudentImportComponent {

  studentsForm!: FormGroup;
  fileFormControl = new FormControl('', [Validators.required]);

  errorMessage!: string;

  constructor(
    private formBuilder: FormBuilder,
    private apiUserService: ApiUserService,
    private _snackBar: MatSnackBar
  ) { }

  ngOnInit() {
    this.studentsForm  =  this.formBuilder.group({
      file: this.fileFormControl,
    });
  }

  upload() {
    this.fileFormControl.setErrors({'apiError': null});
    this.fileFormControl.updateValueAndValidity();
    if(this.studentsForm.invalid){
      console.log("Invalid form");
    } else {
      const file_form: FileInput = this.studentsForm.get('file')?.value;
      const file = file_form.files[0];
      this.apiUserService.uploadStudents(file).subscribe(
        data => {this.showSuccess(data)},
        error => {this.showError(error)},
      );
    }
  }
  
  showSuccess(data : User[]) {
    let nbStudents = data.length;
    this._snackBar.open(nbStudents + " étudiants ont bien été importés", "Fermer", {
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
