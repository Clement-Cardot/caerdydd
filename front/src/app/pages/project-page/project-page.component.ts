import { Component } from "@angular/core";
import { MatSnackBar } from "@angular/material/snack-bar";
import { ActivatedRoute } from "@angular/router";
import { Consulting } from "src/app/core/data/models/consulting.model";
import { Team } from "src/app/core/data/models/team.model";
import { ApiConsultingService } from "src/app/core/services/api-consulting.service";
import { ApiTeamService } from "src/app/core/services/api-team.service";
import { ApiUploadFileService } from "src/app/core/services/api-upload-file.service";
import { FormBuilder, FormControl, FormGroup, Validators } from "@angular/forms";
import { FileInput } from "ngx-material-file-input";
import { User } from "src/app/core/data/models/user.model";
import { UserDataService } from "src/app/core/services/user-data.service";
import { ApiJuryService } from "src/app/core/services/api-jury.service";

@Component({
    selector: 'app-project-page',
    templateUrl: './project-page.component.html',
    styleUrls: ['./project-page.component.scss']
  })
  export class ProjectPageComponent {
    
    id!: string;
    team!: Team;
    currentUser!: User | undefined;
    consultingsList: Consulting[] = [];

    isJury: boolean = false;

    importReportAnnotform: FormGroup;
    reportAnnotFormControl = new FormControl('', [Validators.required]);

    addReportCommentform: FormGroup;
    reportCommentFormControl = new FormControl('', [Validators.required]);

    errorMessage!: string;

    refresh: any;

    constructor(public userDataService: UserDataService, private route: ActivatedRoute, private apiTeamService: ApiTeamService, private uploadFileService: ApiUploadFileService, private juryService: ApiJuryService,private _snackBar: MatSnackBar, private formBuilder: FormBuilder, private consultingService: ApiConsultingService) {
      this.importReportAnnotform = this.formBuilder.group({
        reportAnnot: this.reportAnnotFormControl
      });
      this.addReportCommentform = this.formBuilder.group({
        reportComment: this.reportCommentFormControl
      });
    }

    ngOnInit(): void {
      this.id = this.route.snapshot.params['id'];
      this.getData()
      this.refresh = setInterval(() => { this.getData() },  5000 );
    }

    ngOnDestroy(): void {
      clearInterval(this.refresh);
    }

    getData() {
      this.getCurrentUser();
      this.getTeam();
    }

    getCurrentUser() {
      this.userDataService.getCurrentUser().subscribe(user => {
        this.currentUser = user;
      });
    }

    getTeam() {
      this.apiTeamService.getTeam(+this.id).subscribe(data => {
        this.team = data;
        this.getAllConsultingsForCurrentTeam();
      });
    }

    isUserJury() {
      let userID = this.currentUser?.id
      let ts1 = this.team.projectDev.jury?.ts1.idUser;
      let ts2 = this.team.projectDev.jury?.ts2.idUser;
      let statement = this.currentUser?.getRoles().includes("JURY_MEMBER_ROLE") && (ts1 == userID || ts2 == userID)
      return statement;
    }

    isUserOptionLeader() {
      return this.currentUser?.getRoles().includes("OPTION_LEADER_ROLE");
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
            this.showSuccess("Import du fichier avec succès");
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

    uploadComment() {
      if (this.team != null) {
        if(this.addReportCommentform.invalid) {
          console.log("The form is invalid, form was :");
          console.log(this.addReportCommentform);
        } else {
          const comment: string = this.addReportCommentform.get('reportComment')?.value;
          this.juryService.setCommentOnReport(this.team.idTeam, comment).subscribe(
            data => {
              this.showSuccess("Le commentaire à été modifier avec succès");
            },
            error => {
              this.showError(error)
            },
          );
        }
        // this.reportAnnotFormControl.setErrors({'apiError': null});
        // this.reportAnnotFormControl.updateValueAndValidity();
      }
    }

    showSuccess(message: string) {
      this._snackBar.open(message, "Fermer", {
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

    getAllConsultingsForCurrentTeam(){
      if(this.team) {
        this.consultingService.getConsultingForATeam(this.team.idTeam)
        .subscribe(data => {
            this.consultingsList = data;
            this.consultingsList.sort((a, b) => a.plannedTimingConsulting.datetimeEnd.getTime() - b.plannedTimingConsulting.datetimeEnd.getTime());

          }
        );
      }
    }
  
  }
