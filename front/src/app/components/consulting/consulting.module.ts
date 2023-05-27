import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { MaterialModule } from "src/app/material.module";
import { MaterialFileInputModule } from "ngx-material-file-input";
import { ReactiveFormsModule } from "@angular/forms";
import { ConsultingImportComponent } from "./consulting-import/consulting-import.component";

@NgModule({
    declarations: [
        ConsultingImportComponent
    ],
    imports: [
      CommonModule,
      MaterialModule,
      MaterialFileInputModule,
      ReactiveFormsModule
    ],
    exports: [
      ConsultingImportComponent
    ]
  })
  export class ConsultingModule { }