import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { UserDataService } from 'src/app/core/services/user-data.service';
import { ApiTeamService } from 'src/app/core/services/api-team.service';
import { ApiTeamMemberService } from 'src/app/core/services/api-team-member.service';
import { TeamMember } from 'src/app/core/data/models/team-member.model';
import { Team } from 'src/app/core/data/models/team.model';
import { User } from 'src/app/core/data/models/user.model';
import { ApiUploadFileService } from 'src/app/core/services/api-upload-file.service';

@Component({
  selector: 'app-validation-project',
  templateUrl: './validation-project-page.component.html',
  styleUrls: ['./validation-project-page.component.scss']
})
export class ValidationProjectPageComponent implements OnInit {
  currentUser!: User | null;
  currentTeam!: Team | null;
  validationTeam!: Team;
  testBookLinkValidation!: string | null;

  constructor(
    private userDataService: UserDataService,
    private apiTeamService: ApiTeamService,
    private apiTeamMemberService: ApiTeamMemberService,
    private _snackBar: MatSnackBar,
    private uploadFileService: ApiUploadFileService
  ) { }

  ngOnInit(): void {
    this.currentUser = this.userDataService.getCurrentUser().value;
    if (this.currentUser) {
      this.getTeamMember(this.currentUser.id);
    }
  }

  getTeamMember(userId: number) {
    this.apiTeamMemberService.getTeamMemberById(userId).subscribe(
      (teamMember: TeamMember) => {
        this.getTeam(teamMember.idTeam);
      },
      (error) => {
        console.error("Error getting team member:", error);
      }
    );
  }

  getTeam(teamId: number) {
    this.apiTeamService.getTeam(teamId).subscribe(
      (team: Team) => {
        this.currentTeam = team;
        if (this.currentTeam) {
          this.getTestBookLinkValidation(this.currentTeam.idTeam);
        };
        this.getValidationTeam();
      },
      (error) => {
        console.error("Error getting team:", error);
      }
    );
  }

  getValidationTeam() {
    if (this.currentTeam?.projectValidation.idProject) {
      this.apiTeamService.getTeam(this.currentTeam?.projectValidation.idProject).subscribe(
        (team: Team) => {
          this.validationTeam = team;
        },
        (error) => {
          console.error("Error getting validation team:", error);
        }
      );
    }
  }

  getTestBookLinkValidation(teamId: number) {
    this.apiTeamService.getTestBookLinkValidation(teamId).subscribe(
      (link: string) => {
        this.testBookLinkValidation = link;
      },
      (error) => {
        console.error("Error getting test book link validation:", error);
      }
    );
  }

  getFile(file: string) {
    if (this.currentTeam) {
      this.uploadFileService.download(this.currentTeam?.projectValidation.idProject, file).subscribe(response=>
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

  isThereFinalStateScope() {
    return (this.validationTeam?.filePathFinalScopeStatement != null);
  }

  showErrorDownload() {
    this._snackBar.open("Une erreur est survenue lors du téléchargement", "Fermer", {
      duration: 5000,
    });
  }
}
