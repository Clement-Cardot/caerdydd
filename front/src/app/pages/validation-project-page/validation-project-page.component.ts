import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { UserDataService } from 'src/app/core/services/user-data.service';
import { ApiTeamService } from 'src/app/core/services/api-team.service';
import { ApiTeamMemberService } from 'src/app/core/services/api-team-member.service';
import { TeamMember } from 'src/app/core/data/models/team-member.model';
import { Team } from 'src/app/core/data/models/team.model';
import { User } from 'src/app/core/data/models/user.model';

@Component({
  selector: 'app-validation-project',
  templateUrl: './validation-project-page.component.html',
  styleUrls: ['./validation-project-page.component.scss']
})
export class ValidationProjectPageComponent implements OnInit {
  currentUser!: User | null;
  currentTeam!: Team | null;
  testBookLinkValidation!: string | null;

  constructor(
    private userDataService: UserDataService,
    private apiTeamService: ApiTeamService,
    private apiTeamMemberService: ApiTeamMemberService,
    private _snackBar: MatSnackBar
  ) { }

  ngOnInit(): void {
    this.currentUser = this.userDataService.getCurrentUser().value;
    if (this.currentUser) {
      this.getTeamMember(this.currentUser.id);
    }
  }

  getTeamMember(userId: number) {
    this.apiTeamMemberService.getTeamMemberById(userId).subscribe(
      (teamMember: TeamMember) => {
        this.getTeam(teamMember.idTeam);
      },
      (error) => {
        console.error("Error getting team member:", error);
      }
    );
  }

  getTeam(teamId: number) {
    this.apiTeamService.getTeam(teamId).subscribe(
      (team: Team) => {
        this.currentTeam = team;
        if (this.currentTeam) {
          this.getTestBookLinkValidation(this.currentTeam.idTeam);
        }
      },
      (error) => {
        console.error("Error getting team:", error);
      }
    );
  }

  getTestBookLinkValidation(teamId: number) {
    this.apiTeamService.getTestBookLinkValidation(teamId).subscribe(
      (link: string) => {
        this.testBookLinkValidation = link;
      },
      (error) => {
        console.error("Error getting test book link validation:", error);
      }
    );
  }
}
