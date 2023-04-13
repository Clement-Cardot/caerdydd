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
    // setInterval(() => { this.getData() },  5000 );
  }

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

  getTeamData(idTeam: number){
    this.apiTeamService.getTeam(idTeam)
          .subscribe(data => {
            let index = this.teams.indexOf(this.teams.find(team => team.idTeam == idTeam) as Team);
            this.teams[index] = data;
          }
        );
  }

  update(idTeam: number){
    this.getTeamData(idTeam);
  }
}
