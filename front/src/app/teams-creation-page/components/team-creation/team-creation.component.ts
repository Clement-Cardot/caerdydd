import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Team } from 'src/app/core/data/models/team.model';
import { ApiTeamService } from 'src/app/core/services/api-team.service';
import { MyErrorStateMatcher } from 'src/app/login-page/components/login/login.component';

@Component({
  selector: 'app-team-creation',
  templateUrl: './team-creation.component.html',
  styleUrls: ['./team-creation.component.scss']
})
export class TeamCreationComponent implements OnInit {
  teamCreationForm!: FormGroup;
  matcher = new MyErrorStateMatcher();
  nbTeamsFormControl = new FormControl('', Validators.required);
  teams!: Team[];

  constructor(
    private formBuilder: FormBuilder,
    private apiTeamService: ApiTeamService
  ) { }

  ngOnInit(): void {
    this.teamCreationForm = this.formBuilder.group({
      nbTeams: this.nbTeamsFormControl
    });
  }

  createTeams() {
    if(this.teamCreationForm.invalid){
      return;
    } else {
      this.apiTeamService.createTeams(this.teamCreationForm.value.nbTeams).subscribe(response => {
        console.log("Team Creation Response : " + JSON.stringify(response));
        this.teams = response;
      });
    }
  }
}
