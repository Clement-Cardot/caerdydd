import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { MaterialModule } from "src/app/material.module";
import { ConsultingPageComponent } from "./consulting-page.component";

@NgModule({
    declarations: [
        ConsultingPageComponent
    ],
    imports: [
      CommonModule,
      MaterialModule
    ],
    exports: [
    ]
  })
  export class ConsultingPageModule { }