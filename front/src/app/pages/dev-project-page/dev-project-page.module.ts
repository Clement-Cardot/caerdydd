import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { MaterialModule } from "src/app/material.module";
import { DevProjectPageComponent } from "./dev-project-page.component";
import { FileModule } from "src/app/components/files/file.module";

@NgModule({
    declarations: [
      DevProjectPageComponent
    ],
    imports: [
      CommonModule,
      MaterialModule,
      FileModule
    ]
  })
  export class DevProjectPageModule { }