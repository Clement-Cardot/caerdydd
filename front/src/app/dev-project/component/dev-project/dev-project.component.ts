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

  ngOnInit(): void {
    this.currentUser = this.userDataService.getCurrentUser().value;
  }
}
