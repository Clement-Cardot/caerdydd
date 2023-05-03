import { Component, OnInit } from '@angular/core';
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

@Component({
  selector: 'app-project-description',
  templateUrl: './project-description.component.html',
  styleUrls: ['./project-description.component.scss']
})
export class ProjectDescriptionComponent implements OnInit {
  project!: Project;
  projectForm!: FormGroup;
  currentUser!: User | null;

  constructor(
    private formBuilder: FormBuilder,
    private apiProjectService: ApiProjectService,
    public userDataService: UserDataService,
    private apiTeamMemberService: ApiTeamMemberService,
    private apiTeamService: ApiTeamService,
    private location: Location 

  ) {
    
  }

  ngOnInit(): void {
    this.userDataService.getCurrentUser().subscribe((user: User | null) => {
      this.currentUser = user;
    });
    if (this.currentUser == null) {
      console.log("User is not connected");
    }
    else{
      this.getTeamMember(this.currentUser.id);
      this.initForm();
    }
    
  }

  initForm() {
    this.projectForm = this.formBuilder.group({
      name: ['', [Validators.required, Validators.maxLength(20)]],
      description: ['', [Validators.required, Validators.maxLength(250)]]
    });
  }
  
  loadProject(projectID : number) {
    this.apiProjectService.getProjectById(projectID).subscribe(
      (project) => {
        this.project = project;
        this.projectForm.patchValue({
          name: project.name,
          description: project.description
        });
      },
      (error) => {
        console.error("Error loading project:", error);
      }
    );
  }

  onSubmit() {
    if (this.projectForm.valid) {
      this.project.name =this.projectForm.get("name")!.value;
      this.project.description =this.projectForm.get("description")!.value;

      this.apiProjectService
        .updateProject(this.project)
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

  onSubmitAndGoBack() {
    this.onSubmit();
    this.location.back();
  }

  isCurrentUserAllowed(): boolean {
    let isAllowed = false;
    if (this.currentUser == null) {
      console.log("User is not connected");
      return false;
    }
    if(this.currentUser.getRoles().includes("STUDENT_ROLE" || "TEAM_MEMBER_ROLE")) {
        isAllowed = true;
    };
    return isAllowed; 
  }

  getTeamMember(userId: number) {
    console.log("getTeamMember");
    this.apiTeamMemberService.getTeamMemberById(userId).subscribe(
      (teamMember: TeamMember) => {
        console.log(teamMember);
        this.getTeam(teamMember.idTeam);
        
      },
      (error) => {
        console.error("Error getting team member:", error);
      }
    );
  }

  getTeam(teamId: number) {
    console.log("getTeam");
    this.apiTeamService.getTeam(teamId).subscribe(
      (team: Team) => {
        console.log(team);
        this.project=team.projectDev;
        this.loadProject(this.project.idProject);
           },
      (error) => {
        console.error("Error getting team:", error);
      }
    );
  }
}
