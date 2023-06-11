import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserDataService } from 'src/app/core/services/user-data.service';
import { ApiProjectService } from 'src/app/core/services/api-project.service';
import { Project } from 'src/app/core/data/models/project.model';
import { ApiTeamMemberService } from 'src/app/core/services/api-team-member.service';
import { ApiTeamService } from 'src/app/core/services/api-team.service';
import { TeamMember } from 'src/app/core/data/models/team-member.model';
import { Team } from 'src/app/core/data/models/team.model';
import { User } from 'src/app/core/data/models/user.model';
import { Location } from '@angular/common';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-project-description',
  templateUrl: './project-description.component.html',
  styleUrls: ['./project-description.component.scss']
})
export class ProjectDescriptionComponent implements OnInit {
  @Input() team!: Team;
  projectForm!: FormGroup;

  currentUser: User | undefined = undefined;

  constructor(
    private formBuilder: FormBuilder,
    private apiProjectService: ApiProjectService,
    private _snackBar: MatSnackBar
  ) { }

  ngOnInit(): void {
    this.projectForm = this.formBuilder.group({
      name: ['', [Validators.required, Validators.maxLength(20)]],
      description: ['', [Validators.required, Validators.maxLength(1000)]]
    });
  }

  onSubmit() {
    if (this.projectForm.valid) {
      this.apiProjectService
        .updateProjectDescription(this.team.projectDev)
        .subscribe(
          (response) => {
            this.showSnackbar("Les infomations de votre projet ont été mises à jour !");
          },
          (error) => {
            this.showSnackbar("Une erreur est survenue lors de la mise à jour de votre projet.");
          }
        );
    }
  }

  showSnackbar(message: string) {
		this._snackBar.open(message, "Fermer", {
		  duration: 5000,
		});
	}
}
