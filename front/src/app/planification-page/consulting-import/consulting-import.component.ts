import { Component } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { FileInput } from 'ngx-material-file-input';
import { Consulting } from 'src/app/core/data/models/consulting.model';
import { ApiConsultingService } from 'src/app/core/services/api-consulting.service';

@Component({
  selector: 'app-consulting-import',
  templateUrl: './consulting-import.component.html',
  styleUrls: ['./consulting-import.component.scss']
})
export class ConsultingImportComponent {
  
  consultingForm!: FormGroup;
  fileFormControl = new FormControl('', [Validators.required]);

  errorMessage!: string;

  constructor(
    private formBuilder: FormBuilder,
    private apiConsultingService: ApiConsultingService,
    private _snackBar: MatSnackBar
  ) { }

  ngOnInit() {
    this.consultingForm  =  this.formBuilder.group({
      file: this.fileFormControl,
    });
  }

  upload() {
    this.fileFormControl.setErrors({'apiError': null});
    this.fileFormControl.updateValueAndValidity();
    if(this.consultingForm.invalid){
      console.log("Invalid form");
    } else {
      const file_form: FileInput = this.consultingForm.get('file')?.value;
      const file = file_form.files[0];
      console.log(file);
      this.apiConsultingService.upload(file).subscribe(
        data => {this.showSuccess(data)},
        error => {this.showError(error)},
      );
    }
  }

  showSuccess(data : Consulting[]) {
    let nbConsulting = data.length;
    this._snackBar.open(nbConsulting + " sessions de consulting ont bien été importés", "Fermer", {
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
