import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { MaterialModule } from "src/app/material.module";
import { ErrorPageComponent } from "./error-page.component";

@NgModule({
    declarations: [
      ErrorPageComponent
    ],
    imports: [
      CommonModule,
      MaterialModule
    ],
    exports: [
    ]
  })
  export class ErrorPageModule { }