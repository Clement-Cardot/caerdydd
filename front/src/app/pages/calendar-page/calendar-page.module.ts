import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { MaterialModule } from "src/app/material.module";
import { CalendarPageComponent } from "./calendar-page.component";
import { GlobalCalendarModule } from "src/app/components/calendar/global-calendar.module";

@NgModule({
    declarations: [
        CalendarPageComponent
    ],
    imports: [
      CommonModule,
      MaterialModule,
      GlobalCalendarModule
    ],
    exports: [
    ]
  })
  export class CalendarPageModule { }