
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ValidationProjectPageComponent } from './validation-project-page.component';
import { MaterialModule } from 'src/app/material.module';




@NgModule({
  declarations: [
    ValidationProjectPageComponent
  ],
  imports: [
    CommonModule,
    MaterialModule
  ]
})
export class ValidationProjectPageModule { }