import { NgModule } from "@angular/core";
import { CreatePresentationComponent } from "./create-presentation/create-presentation.component";
import { CommonModule } from "@angular/common";
import { MaterialModule } from "src/app/material.module";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";

@NgModule({
    declarations: [
        CreatePresentationComponent
    ],
    imports: [
      CommonModule,
      MaterialModule,
      FormsModule,
      ReactiveFormsModule
    ],
    exports: [
        CreatePresentationComponent
    ]
  })
  export class PresentationModule { }