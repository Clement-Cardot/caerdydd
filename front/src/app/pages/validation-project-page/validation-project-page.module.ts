import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { BrowserModule } from '@angular/platform-browser';
import { ValidationProjectPageComponent } from './validation-project-page.component';


@NgModule({
  declarations: [
    ValidationProjectPageComponent
  ],
  imports: [
    CommonModule,
    BrowserModule,
    MatCardModule
  ]
})
export class ValidationProjectPageModule { }
