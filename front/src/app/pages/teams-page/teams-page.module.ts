import { NgModule } from '@angular/core';
import { TeamsPageComponent } from './teams-page.component';
import { CommonModule } from '@angular/common';
import { MaterialModule } from 'src/app/material.module';
import { TeamModule } from 'src/app/components/project/project.module';


@NgModule({
  declarations: [
    TeamsPageComponent,
  ],
  imports: [
    CommonModule,
    MaterialModule,
    TeamModule
  ]
})
export class TeamsPageModule { }
