import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { BrowserModule } from '@angular/platform-browser';
import { ValidateSubjectPageComponent } from './validate-subject-page.component';
import { TeamModule } from 'src/app/components/teams/team.module';

@NgModule({
  declarations: [
    ValidateSubjectPageComponent
  ],
  imports: [
    CommonModule,
    BrowserModule,
    MatCardModule,
    TeamModule
  ]
})
export class ValidateSubjectPageModule { }
