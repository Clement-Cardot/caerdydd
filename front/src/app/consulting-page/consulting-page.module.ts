import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ConsultingCalendarComponent } from './consulting-calendar/consulting-calendar.component';
import { CalendarModule, DateAdapter } from 'angular-calendar';
import { adapterFactory } from 'angular-calendar/date-adapters/moment';
import * as moment from 'moment';
import { ReactiveFormsModule } from '@angular/forms';

export function momentAdapterFactory() {
  return adapterFactory(moment);
}; 

@NgModule({
  declarations: [
    ConsultingCalendarComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    CalendarModule.forRoot({ provide: DateAdapter, useFactory: momentAdapterFactory })
  ]
})
export class ConsultingPageModule { }
