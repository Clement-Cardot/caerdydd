import { Component, Input, OnInit } from '@angular/core';
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

export class LeaderMarkComponent implements OnInit {
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

	teamMark!: number;
	validationMark!: number;
	idTeamString: any; 
	idTeam : number = 0;
	bonusPenaltySum: number = 0;

	panelOpenState = false;

	constructor(private apiTeamService: ApiTeamService, private apiTeamMemberService: ApiTeamMemberService) {
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
