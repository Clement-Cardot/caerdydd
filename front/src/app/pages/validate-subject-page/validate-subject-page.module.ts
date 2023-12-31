import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { BrowserModule } from '@angular/platform-browser';
import { ValidateSubjectPageComponent } from './validate-subject-page.component';
import { ProjectModule } from 'src/app/components/project/project.module';

@NgModule({
  declarations: [
    ValidateSubjectPageComponent
  ],
  imports: [
    CommonModule,
    BrowserModule,
    MatCardModule,
    ProjectModule
  ]
})
export class ValidateSubjectPageModule { }
