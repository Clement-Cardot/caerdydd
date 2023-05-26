import { NgModule } from "@angular/core";
import { DefineSpecialtyComponent } from "./define-specialty/define-specialty.component";
import { CommonModule } from "@angular/common";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { MaterialModule } from "src/app/material.module";

@NgModule({
    declarations: [
        DefineSpecialtyComponent
    ],
    imports: [
      CommonModule,
      MaterialModule,
      FormsModule,
      ReactiveFormsModule,
    ],
    exports: [
      DefineSpecialtyComponent
    ]
  })
  export class TeachingStaffModule { }