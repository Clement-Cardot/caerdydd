import { Component, OnInit } from '@angular/core';
import { TeamList } from '../../models/team_list.model';
import { StudentsList } from '../../models/team_list.model';

const ELEMENT_DATA: StudentsList[] = [
  {id: 1, name: 'John', surname: 'Doe', speciality: 'LD'},
  {id: 2, name: 'Eric', surname: 'Smith', speciality: 'LD'},
  {id: 3, name: 'Patrik', surname: 'Smith', speciality: 'LD'},
  {id: 4, name: 'Isabelle', surname: 'Smith', speciality: 'CSS'},
];

const ELEMENT_DATA2: StudentsList[] = [
  {id: 11, name: 'Georges', surname: 'A', speciality: 'CSS'},
  {id: 23, name: 'Charles', surname: 'B', speciality: 'LD'},
  {id: 33, name: 'Henri', surname: 'C', speciality: 'LD'},
  {id: 43, name: 'Johny', surname: 'D', speciality: 'CSS'},
];

@Component({
  selector: 'app-all-teams-list',
  templateUrl: './all-teams-list.component.html',
  styleUrls: ['./all-teams-list.component.scss']
})

export class AllTeamsListComponent implements OnInit{
  team1!: TeamList;
  team2!: TeamList;

  
    ngOnInit() {
      this.team1 = {
        id: 1,
        teamName: 'Team 1',
        displayedColumns: ['id', 'name', 'surname', 'speciality'],
        dataSource: ELEMENT_DATA,
      }
      this.team2 = {
        id: 2,
        teamName: 'Team 2',
        displayedColumns: ['id', 'name', 'surname', 'speciality'],
        dataSource: ELEMENT_DATA2,
      }
    }
}
