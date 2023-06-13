import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { MaterialModule } from "src/app/material.module";
import { DevProjectPageComponent } from "./dev-project-page.component";
import { ConsultingModule } from "src/app/components/consulting/consulting.module";
import { ProjectModule } from "src/app/components/project/project.module";

@NgModule({
    declarations: [
      DevProjectPageComponent
    ],
    imports: [
      CommonModule,
      MaterialModule,
      ConsultingModule,
      ProjectModule
    ]
  })
  export class DevProjectPageModule { }