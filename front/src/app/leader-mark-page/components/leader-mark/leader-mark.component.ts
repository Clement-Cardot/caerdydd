import { Component, OnInit } from '@angular/core';
import { Student } from '../../models/student-leader-mark.model';


interface Team {
	value: string;
	viewValue: string;
  }


@Component({
	selector: 'app-leader-mark',
	templateUrl: './leader-mark.component.html',
	styleUrls: ['./leader-mark.component.scss']
})

export class LeaderMarkComponent implements OnInit {
	bonusMalusSum: number = 0;

	displayedColumns = [
		'name',
		'firstname',
		'individualMark',
		'teamMark',
		'validationMark',
		'bonusMalus',
		'FinalMark'
	  ];
	
	dataSource: Student[] = [
	{ 
		name: 'Dupont',
		firstname: 'Jean',
		individualMark: 10,
		teamMark: 5,
		validationMark: 3,
		bonusMalus: 0,
		finalMark: 0
	},
	{ 
		name: 'Martin',
		firstname: 'Marie',
		individualMark: 5,
		teamMark: 5,
		validationMark: 5,
		bonusMalus: 2,
		finalMark: 0
	},
	{ 
		name: 'Lefevre',
		firstname: 'Luc',
		individualMark: 7,
		teamMark: 3,
		validationMark: 3,
		bonusMalus: -2,
		finalMark: 0
	},
	{ 
		name: 'Patrick',
		firstname: 'Chirac',
		individualMark: 2,
		teamMark: 1,
		validationMark: 0,
		bonusMalus: 0,
		finalMark: 0
	},
	{ 
		name: 'Dubois',
		firstname: 'Juliette',
		individualMark: 2,
		teamMark: 5,
		validationMark: 0,
		bonusMalus: 0,
		finalMark: 0
	},
	];

	
	foods: Team[] = [
		{value: 'steak-0', viewValue: 'Steak'},
		{value: 'pizza-1', viewValue: 'Pizza'},
		{value: 'tacos-2', viewValue: 'Tacos'},
	];

	ngOnInit(): void {
		this.dataSource.forEach((student) => this.updateFinalMark(student));
	}
	  
	onBonusMalusChange(student: Student) {
		this.updateFinalMark(student);
	}

	updateFinalMark(student: Student): void {
		student.finalMark = student.individualMark + student.teamMark +
			student.validationMark + student.bonusMalus;
	}
	
	getMinValue(student: Student): number {
		if (student.finalMark - student.bonusMalus >= 4) {
			return -4;
		} else {
			return -(student.finalMark - student.bonusMalus);
		}
	}
	
	getMaxValue(student: Student): number {
		if (student.finalMark - student.bonusMalus <= 16) {
			return 4;
		} else {
			return 20 - (student.finalMark - student.bonusMalus);
		}
	}
	
	getSumBonus(): number {
		this.bonusMalusSum = 0;

		this.dataSource.forEach((student) => {
			this.bonusMalusSum += student.bonusMalus;
		});

		return this.bonusMalusSum;
	}
}
