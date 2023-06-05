import { Component, Input, OnInit } from '@angular/core';
import { TeamMember } from 'src/app/core/data/models/team-member.model';
import { Team } from 'src/app/core/data/models/team.model';
import { ApiTeamService } from 'src/app/core/services/api-team.service';
import { ApiTeamMemberService } from 'src/app/core/services/api-team-member.service';
import { UserDataService } from 'src/app/core/services/user-data.service';
import { User } from 'src/app/core/data/models/user.model';
@Component({
	selector: 'app-marks',
	templateUrl: './marks.component.html',
	styleUrls: ['./marks.component.scss']
})

export class MarksComponent implements OnInit {

	currentUser!: User | null;

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

	public ngOnInit():void {
		this.userService.getCurrentUser().subscribe((user: User | null) => {
		  this.currentUser = user;
		});
	  }

	panelOpenState = false;

	constructor(private userService: UserDataService, private apiTeamService: ApiTeamService, private apiTeamMemberService: ApiTeamMemberService) {
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
			if(teamMember.individualMark != null) {
				await this.apiTeamMemberService.setIndividualMarkTeamMember(teamMember.user.id, teamMember.individualMark).toPromise();
				console.log("Individual mark saved for user:", teamMember.user.id);
			}
		} 
		if (this.team.teamWorkMark !== null) {
			await this.apiTeamService.setTeamMarks(this.team.idTeam, this.team.teamWorkMark, this.team.teamValidationMark).toPromise();
			console.log("Team work mark saved!");
		  }
		  
		  if (this.team.teamValidationMark !== null) {
			await this.apiTeamService.setTeamMarks(this.team.idTeam, this.team.teamWorkMark, this.team.teamValidationMark).toPromise();
			console.log("Validation mark saved!");
		  }

		  console.log("Marks saved!");
		} catch (error) {
		  console.error("Error while saving marks:", error);
		}
	}

	isJuryOfTeam(team: Team): boolean {
		if(this.currentUser && team.projectDev.jury) {
			if(team.projectDev.jury.ts1.idUser == this.currentUser.id || team.projectDev.jury.ts2.idUser == this.currentUser.id) {
				return true;
			}
			else {
				return false;
			}
		}
		else {
 			return false;
		}
	}
}
