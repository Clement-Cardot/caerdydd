import { Component } from "@angular/core";
import { MatSnackBar } from "@angular/material/snack-bar";
import { ActivatedRoute } from "@angular/router";
import { Team } from "src/app/core/data/models/team.model";
import { ApiTeamService } from "src/app/core/services/api-team.service";
import { ApiUploadFileService } from "src/app/core/services/api-upload-file.service";

@Component({
    selector: 'app-project-page',
    templateUrl: './project-page.component.html',
    styleUrls: ['./project-page.component.scss']
  })
  export class ProjectPageComponent {
    
    id!: string;
    team!: Team;

    constructor(private route: ActivatedRoute, private apiTeamService: ApiTeamService, private uploadFileService: ApiUploadFileService, private _snackBar: MatSnackBar) {  }

    ngOnInit(): void {
      this.id = this.route.snapshot.params['id'];
      this.apiTeamService.getTeam(+this.id).subscribe(data => {
        this.team = data;
      });
    }

    getFile(file: string) {
      if (this.team) {
        this.uploadFileService.download(this.team?.idTeam, file).subscribe(response=>
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
    }

    isThereStateScope() {
      return (this.team?.filePathScopeStatement != null);
    }
  
    isThereAnalysis() {
      return (this.team?.filePathScopeStatementAnalysis != null);
    }
  
    isThereFinalStateScope() {
      return (this.team?.filePathFinalScopeStatement != null);
    }

    isThereReport() {
      return (this.team?.filePathReport != null);
    }
  
    showErrorDownload() {
      this._snackBar.open("Une erreur est survenue lors du téléchargement", "Fermer", {
        duration: 5000,
      });
    }
  
  }