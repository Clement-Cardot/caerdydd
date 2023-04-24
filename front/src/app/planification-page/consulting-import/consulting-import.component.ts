import { Component } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { FileInput } from 'ngx-material-file-input';
import { ApiConsultingService } from 'src/app/core/services/api-consulting.service';

@Component({
  selector: 'app-consulting-import',
  templateUrl: './consulting-import.component.html',
  styleUrls: ['./consulting-import.component.scss']
})
export class ConsultingImportComponent {
  
  consultingForm!: FormGroup;
  fileFormControl = new FormControl('', [Validators.required]);

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private apiConsultingService: ApiConsultingService
  ) { }

  ngOnInit() {
    this.consultingForm  =  this.formBuilder.group({
      file: this.fileFormControl,
    });
  }

  upload() {
    if(this.consultingForm.invalid){
      console.log("Invalid form");
    } else {
      const file_form: FileInput = this.consultingForm.get('file')?.value;
      const file = file_form.files[0];
      console.log(file);
      this.apiConsultingService.upload(file).subscribe();
      this.router.navigateByUrl("/consulting");
    }
  }

}
