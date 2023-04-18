import { Component, OnInit, Input } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserDataService } from 'src/app/core/services/user-data.service';
import { ApiProjectService } from 'src/app/core/services/api-project.service';
import { Project } from 'src/app/core/data/models/project.models';

@Component({
  selector: 'app-project-description',
  templateUrl: './project-description.component.html',
  styleUrls: ['./project-description.component.scss']
})
export class ProjectDescriptionComponent implements OnInit {
  @Input() project!: Project;
  projectForm!: FormGroup;
  
  constructor(
    private formBuilder: FormBuilder,
    private apiProjectService: ApiProjectService,
    public userDataService: UserDataService
  ) {}

  ngOnInit(): void {
    this.initForm();
  }

  initForm() {
    this.projectForm = this.formBuilder.group({
      name: [this.project.name, [Validators.required]],
      description: [this.project.description, [Validators.required]]
    });
  }

  onSubmit() {
    if (this.projectForm.valid) {
      const name = this.projectForm.get('name')!.value;
      const description = this.projectForm.get('description')!.value;
      const updatedProject = new Project(this.project.idProject, name, description);

      this.apiProjectService.updateProject(updatedProject).subscribe((response) => {
        console.log(response);
      });
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
}
