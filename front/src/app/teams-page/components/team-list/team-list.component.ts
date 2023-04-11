import { Component, Input } from '@angular/core';
import { UserDataService } from 'src/app/core/services/user-data.service';
import { Team } from 'src/app/core/data/models/team.model';
import { ApiTeamService } from 'src/app/core/services/api-team.service';

@Component({
  selector: 'app-team-list',
  templateUrl: './team-list.component.html',
  styleUrls: ['./team-list.component.scss']
})

export class TeamListComponent {
  @Input() team!: Team;
  displayedColumns: string[] = ['id', 'name', 'surname', 'speciality'];

  constructor(private apiTeamService: ApiTeamService, public userDataService: UserDataService) {  }

  applyInTeam(idTeam: number) {
    this.apiTeamService.applyForTeam(idTeam, this.userDataService.getCurrentUser().id).subscribe((res) => {
      console.log(res);
    });
  }

  isCurrentUserAStudent() {
    let isStudent = false;
    this.userDataService.getCurrentUser().roles.forEach(role => {
      if(role.role == "STUDENT_ROLE"){
        console.log("User is Student");
        isStudent = true;
      }
    });
    return isStudent;
  }
}