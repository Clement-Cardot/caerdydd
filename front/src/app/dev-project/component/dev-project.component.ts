import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserDataService } from 'src/app/core/services/user-data.service';
import { ApiTeamService } from 'src/app/core/services/api-team.service';
import { ApiTeamMemberService } from 'src/app/core/services/api-team-member.service';
import { TeamMember } from 'src/app/core/data/models/team-member.model';
import { Team } from 'src/app/core/data/models/team.model';
import { User } from 'src/app/core/data/models/user.model';
import { MatSnackBar } from '@angular/material/snack-bar';



function linkValidator(control: AbstractControl): { [key: string]: any } | null {
  const value = control.value;
  const isValidUrl = /^(https?:\/\/)?([\da-z.-]+)\.([a-z.]{2,6})([/\w.-]*)*\/?/.test(value);

  return isValidUrl ? null : { invalidLink: true };
}

@Component({
  selector: 'app-dev-project',
  templateUrl: './dev-project.component.html',
  styleUrls: ['./dev-project.component.scss']
})
export class DevProjectComponent implements OnInit {
  testBookLinkForm: FormGroup;
  currentUser!: User | null;
  currentTeam!: Team | null;
  testBookLink!: string | null;

  constructor(
    private fb: FormBuilder,
    private userDataService: UserDataService,
    private apiTeamService: ApiTeamService,
    private apiTeamMemberService: ApiTeamMemberService,
    private _snackBar: MatSnackBar
  ) {
    this.testBookLinkForm = this.fb.group({
      testBookLink: ['', [Validators.required, linkValidator]] 
    });
  }

  
  openSnackBar() {
    this._snackBar.open("Lien TestBook ajouté avec succès", "Fermer", { duration: 5000 });
  }

  ngOnInit(): void {
    this.currentUser = this.userDataService.getCurrentUser().value;
    if (this.currentUser) {
      this.getTeamMember(this.currentUser.id);
    }
  }

  isCurrentUserInTeam(): boolean {
    return !!(this.currentUser && this.currentTeam);
  }

  isProjectDev(): boolean {
    return !!(this.currentTeam && this.currentTeam.idProjectDev !== null);
  }

  onSubmit(): void {
    if (this.testBookLinkForm.valid && this.currentUser && this.currentTeam) {
      this.apiTeamService
        .addTestBookLink(this.currentTeam.idTeam, this.testBookLinkForm.value.testBookLink)
        .subscribe(team => {
          this.currentTeam = team;
          this.testBookLink = team.testBookLink;
          console.log('Lien TestBook ajouté avec succès');
          this.openSnackBar();
        });
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
        this.getTestBookLink(team.idTeam);
      },
      (error) => {
        console.error("Error getting team:", error);
      }
    );
  }

  getTestBookLink(teamId: number) {
    this.apiTeamService.getTestBookLinkDev(teamId).subscribe(
      (link: string) => {
        this.testBookLink = link;
      },
      (error) => {
        console.error("Error getting test book link:", error);
      }
    );
  }

  
}
