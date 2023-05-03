import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Observable } from 'rxjs';
import { ApiTeamService } from 'src/app/core/services/api-team.service';
import { ApiUploadFileService } from 'src/app/core/services/api-upload-file.service';
import { UserDataService } from 'src/app/core/services/user-data.service';

@Component({
  selector: 'app-team-file',
  templateUrl: './team-file.component.html',
  styleUrls: ['./team-file.component.scss']
})
export class TeamFileComponent implements OnInit {

  form: FormGroup;
  fileSelected = false;

  fileFormControl = new FormControl([Validators.required]);

  constructor(private apiTeamService: ApiTeamService, private uploadFileService: ApiUploadFileService, public userDataService: UserDataService, private formBuilder: FormBuilder) {
    this.form = this.formBuilder.group({
      file: this.fileFormControl
    });
  }

  public ngOnInit():void {
    // TODO document why this method 'ngOnInit' is empty
  }



  onFileSelect(event: any) {
    this.fileSelected = event.target.files.length > 0;
  }

  onSubmit(fileType: string) {
    const file = this.form.value.file;
    console.log(file);
    console.log(fileType);
    this.uploadFileService.upload(file, 1, fileType);
  }
}
