import { Component } from '@angular/core';
import { FormGroup, FormControl, Validators, FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { FileInput } from 'ngx-material-file-input';
import { ApiConsultingService } from 'src/app/core/services/api-consulting.service';
import { ApiUserService } from 'src/app/core/services/api-user.service';

@Component({
  selector: 'app-student-import',
  templateUrl: './student-import.component.html',
  styleUrls: ['./student-import.component.scss']
})
export class StudentImportComponent {

  studentsForm!: FormGroup;
  fileFormControl = new FormControl('', [Validators.required]);
  error: string = "test";

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private apiUserService: ApiUserService
  ) { }

  ngOnInit() {
    this.studentsForm  =  this.formBuilder.group({
      file: this.fileFormControl,
    });
  }

  upload() {
    if(this.studentsForm.invalid){
      console.log("Invalid form");
    } else {
      const file_form: FileInput = this.studentsForm.get('file')?.value;
      const file = file_form.files[0];
      console.log(file);
      this.apiUserService.uploadStudents(file).subscribe(
        data => {console.log(data)},
        err => {this.error = err},
      );
      this.router.navigateByUrl("/consulting");
    }
  }

}
