import { Component, Input } from '@angular/core';
import { TeamMember } from 'src/app/core/data/models/team-member.model';
import { Team } from 'src/app/core/data/models/team.model';
import { ApiTeamService } from 'src/app/core/services/api-team.service';
import { ApiTeamMemberService } from 'src/app/core/services/api-team-member.service';

@Component({
  selector: 'app-jury-member-mark',
  templateUrl: './jury-member-mark.component.html',
  styleUrls: ['./jury-member-mark.component.scss']
})

export class JuryMemberMarkComponent {
  displayedColumns = [
		'name',
		'firstname',
		'individualMark',
		'teamMark',
		'validationMark',
		'bonusMalus',
		'FinalMark',
	  ];
	
	@Input() team!: Team;

	panelOpenState = false;

	individualMarkMax = 10; 
	individualMarkMin = 0;
	teamMarkMax = 5;
	teamMarkMin = 0; 

	constructor(private apiTeamService: ApiTeamService, private apiTeamMemberService: ApiTeamMemberService) {
	}

  	calculateFinalMark(teamMember: TeamMember): number {
		return teamMember.individualMark 
				+ this.team.teamWorkMark 
				+ this.team.teamValidationMark 
				+ teamMember.bonusPenalty;
	} 

	checkFinalMarks(team: Team): boolean{
		for(let teamMember of team.teamMembers){
			if(this.calculateFinalMark(teamMember) > 20 || this.calculateFinalMark(teamMember) < 0){
				return true;
			}
		}
		return false;
	}

	async saveMarks(): Promise<void> {
		try {
		  for (const teamMember of this.team.teamMembers) {
			await this.apiTeamMemberService.setIndividualMarkTeamMember(teamMember.user.id, teamMember.individualMark).toPromise();
			console.log("Individual mark saved for user:", teamMember.user.id);
		  }
	  
		  await this.apiTeamService.setTeamWorkMarkTeam(this.team.idTeam, this.team.teamWorkMark).toPromise();
		  console.log("Team work mark saved!");
	  
		  await this.apiTeamService.setTeamValidationMarkTeam(this.team.idTeam, this.team.teamValidationMark).toPromise();
		  console.log("Validation mark saved!");
	  
		  console.log("Marks saved!");
		} catch (error) {
		  console.error("Error while saving marks:", error);
		}
	  }
}