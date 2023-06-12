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
    currentUser!: User | undefined;

    isJury: boolean = false;

    importReportAnnotform: FormGroup;
    reportAnnotFormControl = new FormControl('', [Validators.required]);

    errorMessage!: string;

    constructor(public userDataService: UserDataService, private route: ActivatedRoute, private apiTeamService: ApiTeamService, private uploadFileService: ApiUploadFileService, private _snackBar: MatSnackBar, private formBuilder: FormBuilder) {
      this.importReportAnnotform = this.formBuilder.group({
        reportAnnot: this.reportAnnotFormControl
      });
    }

    ngOnInit(): void {
      this.id = this.route.snapshot.params['id'];
      this.userDataService.getCurrentUser().subscribe(user => {
        this.currentUser = user;
      });

      this.apiTeamService.getTeam(+this.id).subscribe(data => {
        this.team = data;
      });
    }

    isUserJury() {
      let userID = this.currentUser?.id
      let ts1 = this.team.projectDev.jury?.ts1.idUser;
      let ts2 = this.team.projectDev.jury?.ts2.idUser;
      let statement = this.currentUser?.getRoles().includes("JURY_MEMBER_ROLE") && (ts1 == userID || ts2 == userID)
      return statement;
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

    uploadFile() {
      if (this.team != null) {
        this.reportAnnotFormControl.setErrors({'apiError': null});
        this.reportAnnotFormControl.updateValueAndValidity();
        if(this.importReportAnnotform.invalid) {
          console.log("Invalid form");
        } else {
          const file_form: FileInput = this.importReportAnnotform.get('reportAnnot')?.value;
          const file = file_form.files[0];
          this.uploadFileService.upload(file, this.team.idTeam, "annotedReport").subscribe(
          data => {
            this.showSuccess();
          },
          error => {
            this.showError(error)
          },
        );
        }
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
        case 'reportAnnotation':
          return this.team.isReportAnnotation;
        default:
          return false;
      }
    }

    showSuccess() {
      this._snackBar.open("Import du fichier avec succès", "Fermer", {
        duration: 5000,
      });
    }

    showError(error: { status: number; }) {
      this.reportAnnotFormControl.setErrors({apiError: true});
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
