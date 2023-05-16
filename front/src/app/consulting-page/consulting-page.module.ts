import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ConsultingCalendarComponent } from './consulting-calendar/consulting-calendar.component';
import { CalendarDateFormatter, CalendarModule, CalendarNativeDateFormatter, DateAdapter } from 'angular-calendar';
import { adapterFactory } from 'angular-calendar/date-adapters/moment';
import * as moment from 'moment';
import { MaterialModule } from '../material.module';
import { ClickedEventDialogComponent } from './clicked-event-dialog/clicked-event-dialog.component';
import { FormsModule } from '@angular/forms';

export function momentAdapterFactory() {
  return adapterFactory(moment);
};

@NgModule({
  declarations: [
    ConsultingCalendarComponent,
    ClickedEventDialogComponent
  ],
  imports: [
    CommonModule,
    MaterialModule,
    FormsModule,
    CalendarModule.
    forRoot({ 
        provide: DateAdapter, useFactory: momentAdapterFactory
    })
  ],
  providers: [
    {provide: CalendarDateFormatter, useClass: CalendarNativeDateFormatter}
  ]
})
export class ConsultingPageModule { }
