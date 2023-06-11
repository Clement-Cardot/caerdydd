import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { MaterialModule } from "src/app/material.module";
import { SubjectValidationComponent } from "./subject-validation/subject-validation.component";
import { MarksComponent } from "./marks/marks.component";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { TeamCreationComponent } from "./team-creation/team-creation.component";
import { TeamListComponent } from "./team-list/team-list.component";
import { StudentImportComponent } from "./student-import/student-import.component";
import { RouterModule } from "@angular/router";
import { MaterialFileInputModule } from "ngx-material-file-input";
import { ProjectFileComponent } from './project-file/project-file.component';
import { TestBookLinkComponent } from './test-book-link/test-book-link.component';

@NgModule({
    declarations: [
      SubjectValidationComponent,
      MarksComponent,
      StudentImportComponent,
      TeamCreationComponent,
      TeamListComponent,
      ProjectFileComponent,
      TestBookLinkComponent
    ],
    imports: [
      CommonModule,
      MaterialModule,
      FormsModule,
      ReactiveFormsModule,
      RouterModule,
      MaterialFileInputModule,
    ],
    exports: [
      SubjectValidationComponent,
      MarksComponent,
      StudentImportComponent,
      TeamCreationComponent,
      TeamListComponent,
      ProjectFileComponent,
      TestBookLinkComponent
    ]
  })
  export class ProjectModule { }