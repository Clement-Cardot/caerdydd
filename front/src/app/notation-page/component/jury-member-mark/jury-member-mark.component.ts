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
		'FinalMark',
	  ];
	
	@Input() team!: Team;

	panelOpenState = false;

	individualMarkMax = 10; 
	individualMarkMin = 0;

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

	saveMarks(): void{
		this.team.teamMembers.forEach(teamMember => {
			this.apiTeamMemberService.setBonusTeamMember(teamMember.user.id, teamMember.individualMark).subscribe((response) => {
				console.log(response);
			  });
			})
		console.log("individualMark");
	}
}