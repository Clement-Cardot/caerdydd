import { NgModule } from "@angular/core";
import { NotationPageComponent } from "./notation-page.component";
import { CommonModule } from "@angular/common";
import { MaterialModule } from "src/app/material.module";
import { ProjectModule } from "src/app/components/project/project.module";

@NgModule({
    declarations: [
        NotationPageComponent
    ],
    imports: [
      CommonModule,
      MaterialModule,
      ProjectModule
    ]
  })
  export class NotationPageModule { }