import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpClient, HttpErrorResponse } from "@angular/common/http";
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

	teamMark!: number;
	validationMark!: number;
	team!: Team;
	idTeamString: any; 
	idTeam : number = 0;
	bonusPenaltySum: number = 0;

	constructor(private apiTeamService: ApiTeamService, private apiTeamMemberService: ApiTeamMemberService, private route: ActivatedRoute) {
		this.idTeamString = this.route.snapshot.paramMap.get('idTeam');
		this.idTeam = parseInt(this.idTeamString);
	}

	ngOnInit(): void {
		this.getAllData();
	}

	calculateFinalMark(student: TeamMember): number {
		return student.individualMark + this.teamMark +
			this.validationMark + student.bonusPenalty;
	}
	
	getMinValue(student: TeamMember): number {
		if (this.calculateFinalMark(student) - student.bonusPenalty >= 4) {
			return -4;
		} else {
			return -(this.calculateFinalMark(student) - student.bonusPenalty);
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
		this.bonusPenaltySum = 0;

		this.team.teamMembers.forEach((student) => {
			this.bonusPenaltySum += student.bonusPenalty;
		});

		return this.bonusPenaltySum;
	}

	getAllData(){
		this.apiTeamService. getTeam(this.idTeam)
			.subscribe(data => {
				this.team = data;
				this.teamMark = this.team.teamWorkMark;
				this.validationMark = this.team.teamValidationMark;
			  }
			);
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
