import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { MaterialModule } from "src/app/material.module";
import { MaterialFileInputModule } from "ngx-material-file-input";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { ConsultingImportComponent } from "./consulting-import/consulting-import.component";
import { ConsultingInfoComponent } from './consulting-info/consulting-info.component';
import { DialogAnnotationsComponent } from './consulting-info/dialog-annotations/dialog-annotations.component';

@NgModule({
    declarations: [
        ConsultingImportComponent,
        ConsultingInfoComponent,
        DialogAnnotationsComponent
    ],
    imports: [
      CommonModule,
      MaterialModule,
      MaterialFileInputModule,
      ReactiveFormsModule,
      FormsModule
    ],
    exports: [
      ConsultingImportComponent,
      ConsultingInfoComponent
    ]
  })
  export class ConsultingModule { }