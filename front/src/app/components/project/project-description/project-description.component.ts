import { Component, Input, Output, OnInit, EventEmitter } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ApiProjectService } from 'src/app/core/services/api-project.service';
import { Team } from 'src/app/core/data/models/team.model';
import { User } from 'src/app/core/data/models/user.model';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-project-description',
  templateUrl: './project-description.component.html',
  styleUrls: ['./project-description.component.scss']
})
export class ProjectDescriptionComponent implements OnInit {
  @Input() team!: Team;
  @Output() updatableEvent = new EventEmitter();
  projectForm!: FormGroup;

  teamNameInput: string = "";
  teamDescriptionInput: string = "";

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
    this.teamNameInput = this.team.projectDev.name;
    this.teamDescriptionInput = this.team.projectDev.description;
  }

  onSubmit() {
    if (this.projectForm.valid) {
      this.team.projectDev.name = this.projectForm.get('name')?.value;
      this.team.projectDev.description = this.projectForm.get('description')?.value;
      this.apiProjectService
        .updateProjectDescription(this.team.projectDev)
        .subscribe(
          (response) => {
            this.showSnackbar("Les infomations de votre projet ont été mises à jour !");
            this.updatableEvent.emit();
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
