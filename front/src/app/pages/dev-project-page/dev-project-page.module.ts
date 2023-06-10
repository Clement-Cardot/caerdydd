import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { MaterialModule } from "src/app/material.module";
import { DevProjectPageComponent } from "./dev-project-page.component";
import { TeamModule } from "src/app/components/project/project.module";
import { ConsultingModule } from "src/app/components/consulting/consulting.module";

@NgModule({
    declarations: [
      DevProjectPageComponent
    ],
    imports: [
      CommonModule,
      MaterialModule,
      TeamModule,
      ConsultingModule,
    ]
  })
  export class DevProjectPageModule { }