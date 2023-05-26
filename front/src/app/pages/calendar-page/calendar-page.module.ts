import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { MaterialModule } from "src/app/material.module";
import { CalendarPageComponent } from "./calendar-page.component";

@NgModule({
    declarations: [
        CalendarPageComponent
    ],
    imports: [
      CommonModule,
      MaterialModule
    ],
    exports: [
    ]
  })
  export class CalendarPageModule { }