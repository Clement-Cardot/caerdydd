import { NgModule } from "@angular/core";
import { CreatePresentationComponent } from "./create-presentation/create-presentation.component";
import { CommonModule } from "@angular/common";
import { MaterialModule } from "src/app/material.module";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { TeachingStaffCommentaryComponent } from "./teaching-staff-commentary/teaching-staff-commentary.component";

@NgModule({
    declarations: [
        CreatePresentationComponent,
        TeachingStaffCommentaryComponent
    ],
    imports: [
      CommonModule,
      MaterialModule,
      FormsModule,
      ReactiveFormsModule
    ],
    exports: [
        CreatePresentationComponent,
        TeachingStaffCommentaryComponent
    ]
  })
  export class PresentationModule { }