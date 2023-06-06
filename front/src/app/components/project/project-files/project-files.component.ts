import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';

import { FileInput } from 'ngx-material-file-input';
import { ApiTeamService } from 'src/app/core/services/api-team.service';
import { ApiUploadFileService } from 'src/app/core/services/api-upload-file.service';
import { UserDataService } from 'src/app/core/services/user-data.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ApiTeamMemberService } from 'src/app/core/services/api-team-member.service';
import { User } from 'src/app/core/data/models/user.model';
import { Team } from 'src/app/core/data/models/team.model';
import { TeamMember } from 'src/app/core/data/models/team-member.model';


function linkValidator(control: AbstractControl): { [key: string]: any } | null {
  const value = control.value;
  const isValidUrl = /^(https?:\/\/)?([\da-z.-]+)\.([a-z.]{2,6})([/\w.-]*)*\/?/.test(value);

  return isValidUrl ? null : { invalidLink: true };
}

@Component({
  selector: 'app-project-file',
  templateUrl: './project-files.component.html',
  styleUrls: ['./project-files.component.scss']
})
export class ProjectFilesComponent implements OnInit {

  testBookLinkForm: FormGroup;
  currentUser!: User | null;
  currentTeam!: Team | null;

  importTSSform: FormGroup;
  importAnalysisform: FormGroup;
  importFTSSform: FormGroup;
  importReportform: FormGroup;

  teamScopeFormControl = new FormControl('', [Validators.required]);
  analysisFormControl = new FormControl('', [Validators.required]);
  finalTeamScopeFormControl = new FormControl('', [Validators.required]);
  reportFormControl = new FormControl('', [Validators.required]);

  errorMessage!: string;

  constructor(private apiTeamMemberService: ApiTeamMemberService, private apiTeamService: ApiTeamService, private uploadFileService: ApiUploadFileService, public userDataService: UserDataService, private formBuilder: FormBuilder, private _snackBar: MatSnackBar) {
    this.importTSSform = this.formBuilder.group({
      teamScopeStatement: this.teamScopeFormControl
    });
    this.importAnalysisform = this.formBuilder.group({
      analysis: this.analysisFormControl
    });
    this.importFTSSform = this.formBuilder.group({
      finalTeamScopeStatement: this.finalTeamScopeFormControl
    });
    this.importReportform = this.formBuilder.group({
      report: this.reportFormControl
    });
    this.testBookLinkForm = this.formBuilder.group({
      testBookLink: ['', [Validators.required, linkValidator]]
    });
  }

  public ngOnInit():void {
    this.userDataService.getCurrentUser().subscribe((user: User | null) => {
      this.currentUser = user;
      this.getTeamMember();
    });
  }

  upload(fileName: string, type: number) {
    if (this.currentUser != null && this.currentTeam != null) {
      let id = this.currentTeam.idTeam;
      let importForm: FormGroup;
      let formControl: FormControl;
      if (type === 1) {
        importForm = this.importTSSform;
        formControl = this.teamScopeFormControl;
        this.teamScopeFormControl.setErrors({'apiError': null});
        this.teamScopeFormControl.updateValueAndValidity();
      } else if (type === 2) {
        importForm = this.importAnalysisform;
        formControl = this.analysisFormControl;
        this.analysisFormControl.setErrors({'apiError': null});
        this.analysisFormControl.updateValueAndValidity();
      } else if (type === 3) {
        importForm = this.importFTSSform;
        formControl = this.finalTeamScopeFormControl;
        this.analysisFormControl.setErrors({'apiError': null});
        this.analysisFormControl.updateValueAndValidity();
      } else {
        importForm = this.importReportform;
        formControl = this.reportFormControl;
        this.analysisFormControl.setErrors({'apiError': null});
        this.analysisFormControl.updateValueAndValidity();
      }
      if(importForm.invalid) {
        console.log("Invalid form");
      } else {
        const file_form: FileInput = importForm.get(fileName)?.value;
        const file = file_form.files[0];
        this.uploadFileService.upload(file, id, fileName).subscribe(
          data => {
            this.getTeam(id);
            this.showSuccess();
          },
          error => {
            this.showError(error, formControl)
          },
        );
      }
    }

  }

  isThereStateScope() {
    return (this.currentTeam?.filePathScopeStatement != null);
  }

  isThereAnalysis() {
    return (this.currentTeam?.filePathScopeStatementAnalysis != null);
  }

  isThereFinalStateScope() {
    return (this.currentTeam?.filePathFinalScopeStatement != null);
  }

  isThereReport() {
    return (this.currentTeam?.filePathReport != null);
  }

  isCurrentUserInTeam(): boolean {
    return !!(this.currentUser && this.currentTeam);
  }

  isProjectDev(): boolean {
    return !!(this.currentTeam && this.currentTeam.idProjectDev !== null);
  }

  onSubmit(): void {
    if (this.testBookLinkForm.valid && this.currentUser && this.currentTeam) {
      this.currentTeam.testBookLink = this.testBookLinkForm.value.testBookLink;
      this.apiTeamService.addTestBookLink(this.currentTeam)
        .subscribe(team => {
          this.currentTeam = team;
          console.log('Lien TestBook ajouté avec succès');
          this.openSnackBar();
        });
    }
  }

  getTeamMember() {
    let id = this.currentUser?.id;
    if (id == null || id == undefined) return;
    this.apiTeamMemberService.getTeamMemberById(id).subscribe(
      (teamMember: TeamMember) => { this.getTeam(teamMember.idTeam)},
      (error) => { console.error("Error getting team member:", error)}
    );
  }

  getTeam(teamId: number) {
    this.apiTeamService.getTeam(teamId).subscribe(
      (team: Team) => {
        this.currentTeam = team;
      },
      (error) => {
        console.error("Error getting team:", error);
      }
    );
  }

  getFile(file: string) {
    if (this.currentTeam) {
      this.uploadFileService.download(this.currentTeam?.idTeam, file).subscribe(response=>
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

  showErrorDownload() {
    this._snackBar.open("Une erreur est survenue lors du téléchargement", "Fermer", {
      duration: 5000,
    });
  }

  openSnackBar() {
    this._snackBar.open("Lien TestBook ajouté avec succès", "Fermer", { duration: 5000 });
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
}
