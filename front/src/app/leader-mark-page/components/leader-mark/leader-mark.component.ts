import { Component, OnInit } from '@angular/core';
import { Student } from '../../models/student-leader-mark.model';
import { StudentService } from '../../services/leader-mark.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Team } from '../../models/team-leader-mark.model';
import { Observable } from 'rxjs';

@Component({
	selector: 'app-leader-mark',
	templateUrl: './leader-mark.component.html',
	styleUrls: ['./leader-mark.component.scss']
})

export class LeaderMarkComponent implements OnInit {
	idTeam : number = 0;
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
	
	// data local en attendant le back et l'accès à la DB
	dataSource: Student[] = [
	{ 
		id: 1,
		name: 'Dupont',
		firstname: 'Jean',
		individualMark: 10,
		teamMark: 5,
		validationMark: 3,
		bonusMalus: 0,
		finalMark: 0
	},
	{ 
		id: 2,
		name: 'Martin',
		firstname: 'Marie',
		individualMark: 5,
		teamMark: 5,
		validationMark: 5,
		bonusMalus: 2,
		finalMark: 0
	},
	{ 
		id: 3,
		name: 'Lefevre',
		firstname: 'Luc',
		individualMark: 7,
		teamMark: 3,
		validationMark: 3,
		bonusMalus: -2,
		finalMark: 0
	},
	{ 
		id: 4,
		name: 'Patrick',
		firstname: 'Chirac',
		individualMark: 2,
		teamMark: 1,
		validationMark: 0,
		bonusMalus: 0,
		finalMark: 0
	},
	{ 
		id: 5,
		name: 'Dubois',
		firstname: 'Juliette',
		individualMark: 2,
		teamMark: 5,
		validationMark: 0,
		bonusMalus: 0,
		finalMark: 0
	},
	];
	idTeamString: any;

	constructor(private route: ActivatedRoute, private router: Router) {
		this.idTeamString = this.route.snapshot.paramMap.get('idTeam');
		if(this.idTeamString == null ){
			this.router.navigate(['/erreur']); //Il faudra créer une page erreur pour gerer les trucs comme ça
		} else {
			this.idTeam = parseInt(this.idTeamString);
			//StudentService.getTeamById(this.idTeam);
		}

		// tester si la team existe, sinon rediriger vers erreur.
		// rediriger : this.router.navigate(['/ma-route', 'valeur1', 'valeur2']);
	  }

	ngOnInit(): void {
		//this.dataSource = StudentService.getStudents(StudentService.getTeamById(id));
		this.dataSource.forEach((student) => this.updateFinalMark(student));
	}

	onBonusMalusChange(student: Student) : void {
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
