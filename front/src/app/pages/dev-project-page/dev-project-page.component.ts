import { Component } from '@angular/core';
import { Consulting } from 'src/app/core/data/models/consulting.model';
import { TeamMember } from 'src/app/core/data/models/team-member.model';
import { Team } from 'src/app/core/data/models/team.model';
import { User } from 'src/app/core/data/models/user.model';
import { ApiConsultingService } from 'src/app/core/services/api-consulting.service';
import { ApiTeamMemberService } from 'src/app/core/services/api-team-member.service';
import { ApiTeamService } from 'src/app/core/services/api-team.service';
import { UserDataService } from 'src/app/core/services/user-data.service';


@Component({
  selector: 'app-dev-project-page',
  templateUrl: './dev-project-page.component.html',
  styleUrls: ['./dev-project-page.component.scss']
})
export class DevProjectPageComponent {
  consultingsList!: Consulting[];
  currentTeam!: Team;
  currentUser!: User | null;
  currentTeamMember!: TeamMember;

  constructor(private consultingService: ApiConsultingService, private userDataService: UserDataService,
    private apiTeamMemberService: ApiTeamMemberService, private apiTeamService: ApiTeamService) { }

  ngOnInit(): void {
    this.userDataService.getCurrentUser().subscribe((user: User | null) => {
      this.currentUser = user;
      if(this.currentUser != null) {
        this.getTeamMember(this.currentUser.id);
      }
    });
  }

  getAllConsultingsForCurrentTeam() {
    if(this.currentTeam) {
      this.consultingService.getConsultingForATeam(this.currentTeam.idTeam)
      .subscribe(data => {
          this.consultingsList = data;
          this.consultingsList.sort((a, b) => a.plannedTimingConsulting.datetimeEnd.getTime() - b.plannedTimingConsulting.datetimeEnd.getTime());

        }
      );
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
        this.getAllConsultingsForCurrentTeam();
      },
      (error) => {
        console.error("Error getting team:", error);
      }
    );
  }
}
