import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserDataService } from 'src/app/core/services/user-data.service';
import { ApiProjectService } from 'src/app/core/services/api-project.service';
import { Project } from 'src/app/core/data/models/project.models';
import { ApiTeamMemberService } from 'src/app/core/services/api-team-member.service';
import { ApiTeamService } from 'src/app/core/services/api-team.service';
import { TeamMember } from 'src/app/core/data/models/team-member.model';
import { Team } from 'src/app/core/data/models/team.model';

@Component({
  selector: 'app-project-description',
  templateUrl: './project-description.component.html',
  styleUrls: ['./project-description.component.scss']
})
export class ProjectDescriptionComponent implements OnInit {
  project!: Project;
  projectForm!: FormGroup;
  projectId!: number;


  constructor(
    private formBuilder: FormBuilder,
    private apiProjectService: ApiProjectService,
    public userDataService: UserDataService,
    private apiTeamMemberService: ApiTeamMemberService,
    private apiTeamService: ApiTeamService
  ) {
    const currentUser = userDataService.getCurrentUser();
    this.getTeamMember(currentUser.id);
  }

  ngOnInit(): void {
    this.initForm();
    this.loadProject();
  }

  initForm() {
    this.projectForm = this.formBuilder.group({
      name: [this.project.name, [Validators.required]],
      description: [this.project.description, [Validators.required]]
    });
  }

  loadProject() {
    this.apiProjectService.getProjectById(this.projectId).subscribe(
      (project) => {
        this.project = project;
        this.initForm();
      },
      (error) => {
        console.error("Error loading project:", error);
      }
    );
  }

  onSubmit() {
    if (this.projectForm.valid) {
      const name = this.projectForm.get("name")!.value;
      const description = this.projectForm.get("description")!.value;
      const updatedProject = new Project(this.project.idProject, name, description);

      this.apiProjectService
        .updateProject(this.projectId, updatedProject)
        .subscribe(
          (response) => {
            console.log(response);
          },
          (error) => {
            console.error("Error updating project:", error);
          }
        );
    }
  }

  isCurrentUserAllowed(): boolean {
    let isAllowed = false;
    this.userDataService.getCurrentUser().roles.forEach(role => {
      if (role.role === 'STUDENT_ROLE' || role.role === 'TEAM_MEMBER_ROLE') {
        isAllowed = true;
      }
    });
    return isAllowed; 
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
        this.projectId = team.idProjectDev;
        this.loadProject();
      },
      (error) => {
        console.error("Error getting team:", error);
      }
    );
  }
}
