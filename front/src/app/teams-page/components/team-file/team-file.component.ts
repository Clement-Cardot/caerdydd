import { Component, OnInit } from '@angular/core';
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

  selectedFiles!: FileList;
  currentFile!: File;
  progress = 0;
  message = '';

  fileInfos!: Observable<any>;

  constructor(private apiTeamService: ApiTeamService, private uploadFileService: ApiUploadFileService, public userDataService: UserDataService) {}

  public ngOnInit():void {
    this.fileInfos = this.uploadFileService.getFiles();
  }

  // selectFile(event) {
  //   this.selectedFiles = event.target.files;
  // }
}
