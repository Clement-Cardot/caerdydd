import { Component } from "@angular/core";
import { FormBuilder, FormControl, FormGroup, Validators } from "@angular/forms";
import { MatSnackBar } from "@angular/material/snack-bar";
import { ActivatedRoute } from "@angular/router";
import { FileInput } from "ngx-material-file-input";
import { Team } from "src/app/core/data/models/team.model";
import { User } from "src/app/core/data/models/user.model";
import { ApiTeamService } from "src/app/core/services/api-team.service";
import { ApiUploadFileService } from "src/app/core/services/api-upload-file.service";
import { UserDataService } from "src/app/core/services/user-data.service";

@Component({
    selector: 'app-project-page',
    templateUrl: './project-page.component.html',
    styleUrls: ['./project-page.component.scss']
  })
  export class ProjectPageComponent {
    
    id!: string;
    team!: Team;
    currentUser!: User | null;

    importReportAnnotform: FormGroup;
    reportAnnotFormControl = new FormControl('', [Validators.required]);

    errorMessage!: string;

    constructor(public userDataService: UserDataService, private route: ActivatedRoute, private apiTeamService: ApiTeamService, private uploadFileService: ApiUploadFileService, private _snackBar: MatSnackBar, private formBuilder: FormBuilder) { 
      this.importReportAnnotform = this.formBuilder.group({
        report: this.reportAnnotFormControl
      });
    }

    ngOnInit(): void {
      this.id = this.route.snapshot.params['id'];
      this.userDataService.getCurrentUser().subscribe((user: User | null) => {
        this.currentUser = user;
      });
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

    uploadFile(fileName: string, type: number) {
      if (this.currentUser != null && this.team != null) {
        let id = this.team.idTeam;
        this.reportAnnotFormControl.setErrors({'apiError': null});
        this.reportAnnotFormControl.updateValueAndValidity();
        if(this.importReportAnnotform.invalid) {
          console.log("Invalid form");
        } else {
          const file_form: FileInput = this.importReportAnnotform.get(fileName)?.value;
          const file = file_form.files[0];
          this.uploadFileService.upload(file, id, fileName).subscribe(
            data => {
              // this.getTeam(id);
              this.showSuccess();
            },
            error => {
              this.showError(error, this.reportAnnotFormControl)
            },
          );
        }
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

    showSuccess() {
      this._snackBar.open("Import du fichier avec succès", "Fermer", {
        duration: 5000,
      });
    }
  
    showError(error: { status: number; }, formControl : FormControl) {
      formControl.setErrors({apiError: true});
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