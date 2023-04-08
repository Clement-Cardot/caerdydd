import { Component, Input } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { UserDataService } from 'src/app/data/user-data.service';
import { TeamMembers } from 'src/app/data/models/team-members.model';
import { Team } from 'src/app/data/models/team.model';

@Component({
  selector: 'app-team-list',
  templateUrl: './team-list.component.html',
  styleUrls: ['./team-list.component.scss']
})

export class TeamListComponent {
  @Input() team!: Team;
  displayedColumns: string[] = ['id', 'name', 'surname', 'speciality'];

  constructor(private http: HttpClient, public userDataService: UserDataService) {  }

  applyInTeam(idTeam: number) {
    let idUser = this.userDataService.getCurrentUser().id;
    const url = "http://localhost:4200/api/teams/"+ idTeam +"/" + idUser
    this.http.put(url, null).subscribe((res) => {

      this.http.get("http://localhost:4200/api/teams/" + idTeam + "/teamMembers").subscribe((res) => {
        console.log(this.team.teamMembers);
        this.team.teamMembers = res as TeamMembers[];
        console.log(this.team.teamMembers);
      });

    });
  }

  isCurrentUserAStudent() {
    return this.userDataService.getCurrentUser().roles.includes("STUDENT_ROLE");
  }


}