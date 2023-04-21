import { Component, OnInit } from '@angular/core';
import { UserDataService } from 'src/app/core/services/user-data.service';
import { ApiTeamService } from 'src/app/core/services/api-team.service';
import { Team } from 'src/app/core/data/models/team.model';
import { User } from 'src/app/core/data/models/user.model';

@Component({
  selector: 'app-all-teams-list',
  templateUrl: './all-teams-list.component.html',
  styleUrls: ['./all-teams-list.component.scss']
})

export class AllTeamsListComponent implements OnInit {
  teams!: Team[];

  refresh: any;

  openTeamsCreation: boolean = false;

  currentUser!: User | null;

  constructor(private apiTeamService: ApiTeamService, public userDataService: UserDataService) {
  }

  ngOnInit(): void {
    this.getAllData();
    this.refresh = setInterval(() => { this.getAllData() },  5000 );
    this.userDataService.getCurrentUser().subscribe((user: User | null) => {
      this.currentUser = user;
    });
  }

  ngOnDestroy(): void {
    if (this.refresh) {
      clearInterval(this.refresh);
    }
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

  generateTeams() {
    this.openTeamsCreation = true;
  }

  closeTeamsCreation(componentDisplayed: boolean) {
    this.openTeamsCreation = componentDisplayed;
  }

  isCurrentUserAnOptionLeader() {
    if (this.currentUser == null) {
      console.log("User is not connected");
      return false;
    }
    if (this.currentUser.getRoles().includes("OPTION_LEADER_ROLE")){
      return true;
    }
    return false; 
  }

}
