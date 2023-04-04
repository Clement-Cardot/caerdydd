import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BoxValidateSubjectComponent } from './components/box-validate-subject/box-validate-subject.component';
import { MatCardModule } from '@angular/material/card';
import { BrowserModule } from '@angular/platform-browser';


@NgModule({
  declarations: [
    BoxValidateSubjectComponent
  ],
  imports: [
    CommonModule,
    BrowserModule,
    MatCardModule
  ]
})
export class PageValidateSubjectModule { }
