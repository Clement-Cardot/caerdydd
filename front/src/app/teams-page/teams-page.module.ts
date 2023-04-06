import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TeamListComponent } from './components/team-list/team-list.component';
import { AllTeamsListComponent } from './components/all-teams-list/all-teams-list.component';
import {MaterialModule} from '../material.module';


@NgModule({
  declarations: [
    TeamListComponent,
    AllTeamsListComponent
  ],
  imports: [
    CommonModule,
    MaterialModule,
  ]
})
export class TeamsPageModule { }
