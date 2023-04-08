import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { UserDataService } from 'src/app/data/user-data.service';
import { Team } from 'src/app/data/models/team.model';



@Component({
  selector: 'app-all-teams-list',
  templateUrl: './all-teams-list.component.html',
  styleUrls: ['./all-teams-list.component.scss']
})

export class AllTeamsListComponent{
  teams!: Team[];
  constructor(private http: HttpClient, public userDataService: UserDataService) { 
    this.getData();
  }

  getData(){
    const url = "http://localhost:4200/api/teams";
    this.http.get(url).subscribe((res) => {
      this.teams = res as Team[];
      this.userDataService.setTeams(this.teams);
    });
  }
}
