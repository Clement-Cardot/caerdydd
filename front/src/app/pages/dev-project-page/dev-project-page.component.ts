import { Component, OnInit, OnDestroy } from '@angular/core';
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
export class DevProjectPageComponent implements OnInit, OnDestroy {
  currentUser: User | undefined = undefined;
  idTeam!: number;
  team!: Team;

  refresh: any;

  constructor(public userDataService: UserDataService, private apiTeamMemberService: ApiTeamMemberService, private apiTeamService: ApiTeamService) { }

  public ngOnInit(): void {
    this.userDataService.getCurrentUser().subscribe((user: User | undefined) => {
      this.currentUser = user;
      this.getTeamMember();
    });
    this.refresh = setInterval(() => { this.getData(); }, 10000);
  }

  ngOnDestroy(): void {
    clearInterval(this.refresh);
  }

  getData() {
    this.getTeam();
  }

  getTeamMember() {
    let id = this.currentUser?.id;
    if (id == null || id == undefined) return;
    this.apiTeamMemberService.getTeamMemberById(id).subscribe(
      (teamMember: TeamMember) => {
        this.idTeam = teamMember.idTeam; 
        this.getTeam();
      },
      (error) => { 
        console.error("Error getting team member:", error)
      }
    );
  }

  getTeam() {
    this.apiTeamService.getTeam(this.idTeam).subscribe(
      (team: Team) => {
        this.team = team;
      },
      (error) => {
        console.error("Error getting team:", error);
      }
    );
  }

}
