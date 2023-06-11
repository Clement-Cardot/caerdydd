import { Component, OnInit } from '@angular/core';
import { TeamMember } from 'src/app/core/data/models/team-member.model';
import { Team } from 'src/app/core/data/models/team.model';
import { User } from 'src/app/core/data/models/user.model';
import { ApiTeamMemberService } from 'src/app/core/services/api-team-member.service';
import { ApiTeamService } from 'src/app/core/services/api-team.service';
import { UserDataService } from 'src/app/core/services/user-data.service';


@Component({
  selector: 'app-dev-project-page',
  templateUrl: './dev-project-page.component.html',
  styleUrls: ['./dev-project-page.component.scss']
})
export class DevProjectPageComponent implements OnInit {
  currentUser: User | undefined = undefined;
  team!: Team;

  constructor(public userDataService: UserDataService, private apiTeamMemberService: ApiTeamMemberService, private apiTeamService: ApiTeamService) { }

  public ngOnInit(): void {
    this.userDataService.getCurrentUser().subscribe((user: User | undefined) => {
      this.currentUser = user;
      this.getTeamMember();
    });
  }

  getTeamMember() {
    let id = this.currentUser?.id;
    if (id == null || id == undefined) return;
    this.apiTeamMemberService.getTeamMemberById(id).subscribe(
      (teamMember: TeamMember) => { this.getTeam(teamMember.idTeam)},
      (error) => { console.error("Error getting team member:", error)}
    );
  }

  getTeam(teamId: number) {
    this.apiTeamService.getTeam(teamId).subscribe(
      (team: Team) => {
        this.team = team;
      },
      (error) => {
        console.error("Error getting team:", error);
      }
    );
  }

}
