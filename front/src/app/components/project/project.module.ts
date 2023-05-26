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
import { ProjectFilesComponent } from "./project-files/project-files.component";

@NgModule({
    declarations: [
      SubjectValidationComponent,
      MarksComponent,
      ProjectFilesComponent,
      StudentImportComponent,
      TeamCreationComponent,
      TeamListComponent
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
      ProjectFilesComponent,
      StudentImportComponent,
      TeamCreationComponent,
      TeamListComponent
    ]
  })
  export class TeamModule { }