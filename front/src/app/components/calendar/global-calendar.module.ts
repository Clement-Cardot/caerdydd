import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { MaterialModule } from "src/app/material.module";
import { ClickedConsultingDialogComponent } from "./clicked-consulting-dialog/clicked-consulting-dialog.component";
import { CalendarComponent } from "./calendar/calendar.component";
import { CalendarDateFormatter, CalendarModule, CalendarNativeDateFormatter, DateAdapter } from 'angular-calendar';
import { adapterFactory } from 'angular-calendar/date-adapters/moment';
import * as moment from "moment";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";

export function momentAdapterFactory() {
    return adapterFactory(moment);
  };

@NgModule({
    declarations: [
        CalendarComponent,
        ClickedConsultingDialogComponent
    ],
    imports: [
      CommonModule,
      MaterialModule,
      FormsModule,
      ReactiveFormsModule,
      CalendarModule.forRoot({ 
            provide: DateAdapter, useFactory: momentAdapterFactory
        })
    ],
    providers: [
        {provide: CalendarDateFormatter, useClass: CalendarNativeDateFormatter}
    ],
    exports: [
      CalendarComponent,
      ClickedConsultingDialogComponent
    ]
  })
  export class GlobalCalendarModule { }