import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { MaterialModule } from "src/app/material.module";
import { ProjectFileComponent } from "./project-file/project-file.component";
import { MaterialFileInputModule } from "ngx-material-file-input";
import { ReactiveFormsModule } from "@angular/forms";

@NgModule({
    declarations: [
        ProjectFileComponent
    ],
    imports: [
      CommonModule,
      MaterialModule,
      MaterialFileInputModule,
      ReactiveFormsModule
    ],
    exports: [
      ProjectFileComponent
    ]
  })
  export class FileModule { }