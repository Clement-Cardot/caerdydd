import { Component, Input } from '@angular/core';
import { TeamMember } from 'src/app/core/data/models/team-member.model';
import { Team } from 'src/app/core/data/models/team.model';
import { ApiTeamService } from 'src/app/core/services/api-team.service';
import { ApiTeamMemberService } from 'src/app/core/services/api-team-member.service';

@Component({
	selector: 'app-leader-mark',
	templateUrl: './leader-mark.component.html',
	styleUrls: ['./leader-mark.component.scss']
})

export class LeaderMarkComponent {
	displayedColumns = [
		'name',
		'firstname',
		'individualMark',
		'teamMark',
		'validationMark',
		'bonusMalus',
		'FinalMark'
	  ];
	
	@Input() team!: Team;

	panelOpenState = false;

	constructor(private apiTeamService: ApiTeamService, private apiTeamMemberService: ApiTeamMemberService) {
	}

	calculateFinalMark(teamMember: TeamMember): number {
		return teamMember.individualMark 
				+ this.team.teamWorkMark 
				+ this.team.teamValidationMark 
				+ teamMember.bonusPenalty;
	}
	
	getMinValue(teamMember: TeamMember): number {
		if (this.calculateFinalMark(teamMember) - teamMember.bonusPenalty >= 4) {
			return -4;
		} else {
			return -(this.calculateFinalMark(teamMember) - teamMember.bonusPenalty);
		}
	}
	
	getMaxValue(student: TeamMember): number {
		if (this.calculateFinalMark(student) - student.bonusPenalty <= 16) {
			return 4;
		} else {
			return 20 - (this.calculateFinalMark(student) - student.bonusPenalty);
		}
	}
	
	getSumBonus(): number {
		let bonusPenaltySum = 0;

		this.team.teamMembers.forEach((student) => {
			bonusPenaltySum += student.bonusPenalty;
		});

		return bonusPenaltySum;
	}

	saveBonus(){
		this.team.teamMembers.forEach(teamMember => {
			this.apiTeamMemberService.setBonusTeamMember(teamMember.user.id, teamMember.bonusPenalty).subscribe((response) => {
				console.log(response);
			  });
			})
		console.log("Bonus/Penalty saved");
	}
}
