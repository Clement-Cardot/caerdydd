import { Component, OnInit } from '@angular/core';
import { Team } from 'src/app/core/data/models/team.model';
import { ApiTeamService } from 'src/app/core/services/api-team.service';

@Component({
  selector: 'app-notation',
  templateUrl: './notation-page.component.html',
  styleUrls: ['./notation-page.component.scss']
})
export class NotationPageComponent implements OnInit {
  
  teams!: Team[];

  constructor(private apiTeamService: ApiTeamService) { }

  ngOnInit(): void {
    this.getAllData();
  }

  getAllData(){
    this.apiTeamService.getAllTeams()
        .subscribe(data => {
            this.teams = data;
          }
        );
  }

}
