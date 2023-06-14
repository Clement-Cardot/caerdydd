import { Component, Input } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Presentation } from 'src/app/core/data/models/presentation.model';
import { Project } from 'src/app/core/data/models/project.model';
import { TeamMember } from 'src/app/core/data/models/team-member.model';
import { Team } from 'src/app/core/data/models/team.model';
import { User } from 'src/app/core/data/models/user.model';
import { ApiPresentationService } from 'src/app/core/services/api-presentation.service';
import { ApiProjectService } from 'src/app/core/services/api-project.service';
import { ApiTeamMemberService } from 'src/app/core/services/api-team-member.service';
import { ApiTeamService } from 'src/app/core/services/api-team.service';
import { UserDataService } from 'src/app/core/services/user-data.service';

@Component({
  selector: 'app-teaching-staff-commentary',
  templateUrl: './teaching-staff-commentary.component.html',
  styleUrls: ['./teaching-staff-commentary.component.scss']
})
export class TeachingStaffCommentaryComponent {
  panelOpenState = false;
  commentary !: string;
  errorMessage !: string;
  project !: Project;
  currentDate !: Date;
  presentationDate !: Date;
  user !: User | undefined;
  writable : Boolean = false;
  isAudit : Boolean = true;
  isDisplayable : Boolean = false;

  constructor(private apiPresentationService : ApiPresentationService,
              private userService : UserDataService,
              private apiTeamMemberService : ApiTeamMemberService,
              private apiTeamService : ApiTeamService,
              private _snackBar: MatSnackBar){}

  @Input() presentation!: Presentation;

  ngOnInit(){
    this.commentary = this.presentation.validationTeamNotes;
    this.presentationDate = new Date(this.presentation.datetimeBegin);
    this.currentDate = new Date();
    this.user = this.userService.getCurrentUser().getValue();
    if (this.user) {
      this.apiTeamMemberService.getTeamMemberById(this.user.id).subscribe(
        (teamMember: TeamMember) => {
          this.apiTeamService.getTeam(teamMember.idTeam).subscribe(
            (team: Team) => {
              if(team.projectValidation.idProject == this.presentation.project?.idProject){
                this.writable = true;
              }
              if(this.presentation.type == "Présentation intermédiaire" || this.presentation.type == "Présentation finale"){
                this.isAudit = false;
              }
              if(this.isAudit || (!this.isAudit && team.projectDev.idProject == this.presentation.project?.idProject)){
                this.isDisplayable = true;
              }
            },
            (error) => { 
              console.error("Error getting team member:", error);
            }
          );
        },
        (error) => { 
          console.error("Error getting team member:", error);
        }
      );
    }
  }

  onSubmit() {
    if (this.presentation.id !== undefined) {
      const idPresentation = parseInt(this.presentation.id.toString(), 10);
      this.apiPresentationService.updateTeamNotes(idPresentation, this.commentary)
        .subscribe(
          data => {
            console.log('Mise à jour des notes du jury effectuée avec succès :', data);
            this.showSuccess();
          },
          error => { this.showError(error);}
        );
    }
  }

  showSuccess() {
    this._snackBar.open("Le commentaire a été mis à jour.", "Fermer", {
      duration: 5000,
    });
  }

  showError(error: { status: number; }) {
    this.errorMessage = "Une erreur inconnue est survenue, veuillez contacter l'administrateur. (" + error.status + ")";
  }
  
}
