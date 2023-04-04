import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TeamCreationComponent } from './components/team-creation/team-creation.component';
import { MaterialModule } from '../material.module';



@NgModule({
  declarations: [
    TeamCreationComponent
  ],
  imports: [
    CommonModule,
    MaterialModule
  ]
})
export class TeamsCreationPageModule { }
