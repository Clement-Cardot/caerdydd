import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ConsultingCalendarComponent } from './consulting-calendar/consulting-calendar.component';
import { CalendarModule, DateAdapter } from 'angular-calendar';
import { adapterFactory } from 'angular-calendar/date-adapters/moment';
import * as moment from 'moment';
import { NgxMatFileInputModule } from '@angular-material-components/file-input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
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
    MatFormFieldModule,
    ReactiveFormsModule,
    NgxMatFileInputModule,
    MatIconModule,
    CalendarModule.forRoot({ provide: DateAdapter, useFactory: momentAdapterFactory })
  ]
})
export class ConsultingPageModule { }
