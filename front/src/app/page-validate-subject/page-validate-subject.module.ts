import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BoxValidateSubjectComponent } from './components/box-validate-subject/box-validate-subject.component';
import { MatCardModule } from '@angular/material/card';
import { BrowserModule } from '@angular/platform-browser';
import { ViewValidateSubjectsComponent } from './components/view-validate-subjects/view-validate-subjects.component';


@NgModule({
  declarations: [
    BoxValidateSubjectComponent,
    ViewValidateSubjectsComponent
  ],
  imports: [
    CommonModule,
    BrowserModule,
    MatCardModule
  ]
})
export class PageValidateSubjectModule { }
