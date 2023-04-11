import { Component } from '@angular/core';
import { UserDataService } from 'src/app/core/services/user-data.service';
import { ApiTeamService } from 'src/app/core/services/api-team.service';
import { Team } from 'src/app/core/data/models/team.model';



@Component({
  selector: 'app-all-teams-list',
  templateUrl: './all-teams-list.component.html',
  styleUrls: ['./all-teams-list.component.scss']
})

export class AllTeamsListComponent{
  teams!: Team[];
  constructor(private apiTeamService: ApiTeamService, public userDataService: UserDataService) { 
    this.getData();
  }

  getData(){
    this.apiTeamService.getAllTeams()
          .subscribe(data => {
            this.teams = data;
          }
        );
  }
}
