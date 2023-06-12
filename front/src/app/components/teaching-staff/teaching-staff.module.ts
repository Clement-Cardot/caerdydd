import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { MaterialModule } from "src/app/material.module";
import { TeachingStaffEntityComponent } from './teaching-staff-entity/teaching-staff-entity.component';
import { DefineSpecialityComponent } from "./define-speciality/define-speciality.component";

@NgModule({
    declarations: [
        DefineSpecialityComponent,
        TeachingStaffEntityComponent
    ],
    imports: [
      CommonModule,
      MaterialModule,
      FormsModule,
      ReactiveFormsModule,
    ],
    exports: [
      DefineSpecialityComponent,
      TeachingStaffEntityComponent
    ]
  })
  export class TeachingStaffModule { }