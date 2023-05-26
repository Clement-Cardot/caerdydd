import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { MaterialModule } from "src/app/material.module";
import { ProjectValidationComponent } from "./project-validation/project-validation.component";
import { MarksComponent } from "./marks/marks.component";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { TeamCreationComponent } from "./team-creation/team-creation.component";
import { TeamInfoComponent } from "./team-info/team-info.component";
import { TeamListComponent } from "./team-list/team-list.component";
import { StudentImportComponent } from "./student-import/student-import.component";
import { RouterModule } from "@angular/router";
import { MaterialFileInputModule } from "ngx-material-file-input";

@NgModule({
    declarations: [
      ProjectValidationComponent,
      MarksComponent,
      StudentImportComponent,
      TeamCreationComponent,
      TeamInfoComponent,
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
      ProjectValidationComponent,
      MarksComponent,
      StudentImportComponent,
      TeamCreationComponent,
      TeamInfoComponent,
      TeamListComponent
    ]
  })
  export class TeamModule { }