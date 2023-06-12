import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { MaterialModule } from "src/app/material.module";
import { DevProjectPageComponent } from "./dev-project-page.component";
import { ProjectModule } from "src/app/components/project/project.module";

@NgModule({
    declarations: [
      DevProjectPageComponent
    ],
    imports: [
      CommonModule,
      MaterialModule,
      ProjectModule
    ]
  })
  export class DevProjectPageModule { }