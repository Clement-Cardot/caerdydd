import { NgModule } from "@angular/core";
import { DefineSpecialtyComponent } from "./define-specialty/define-specialty.component";
import { CommonModule } from "@angular/common";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { MaterialModule } from "src/app/material.module";
import { TeachingStaffEntityComponent } from './teaching-staff-entity/teaching-staff-entity.component';

@NgModule({
    declarations: [
        DefineSpecialtyComponent,
        TeachingStaffEntityComponent
    ],
    imports: [
      CommonModule,
      MaterialModule,
      FormsModule,
      ReactiveFormsModule,
    ],
    exports: [
      DefineSpecialtyComponent,
      TeachingStaffEntityComponent
    ]
  })
  export class TeachingStaffModule { }