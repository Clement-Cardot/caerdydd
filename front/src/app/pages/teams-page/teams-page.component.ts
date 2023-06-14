import { Component, OnDestroy, OnInit } from '@angular/core';
import { UserDataService } from 'src/app/core/services/user-data.service';
import { ApiTeamService } from 'src/app/core/services/api-team.service';
import { Team } from 'src/app/core/data/models/team.model';
import { User } from 'src/app/core/data/models/user.model';

@Component({
  selector: 'app-teams-page',
  templateUrl: './teams-page.component.html',
  styleUrls: ['./teams-page.component.scss']
})

export class TeamsPageComponent implements OnInit, OnDestroy {
  teams: Team[] = [];

  refresh: any;
  currentUserSubscription: any;

  openTeamsCreation: boolean = false;

  currentUser: User | undefined = undefined;

  constructor(private apiTeamService: ApiTeamService, public userDataService: UserDataService) {
  }

  ngOnInit(): void {
    this.getAllData();
    this.refresh = setInterval(() => { this.getAllData() },  5000 );
    this.currentUserSubscription = this.userDataService.getCurrentUser().subscribe((user: User | undefined) => {
      this.currentUser = user;
    });
  }

  ngOnDestroy(): void {
    clearInterval(this.refresh);
    this.currentUserSubscription.unsubscribe();
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

  closeTeamsCreation(componentDisplayed: Team[]) {
    this.openTeamsCreation = false;
    this.teams = [...this.teams, ...componentDisplayed];
  }

  isCurrentUserAnOptionLeader() {
    if (this.currentUser == null) {
      console.error("User is not connected");
      return false;
    }
    if (this.currentUser.getRoles().includes("OPTION_LEADER_ROLE")){
      return true;
    }
    return false; 
  }

}
