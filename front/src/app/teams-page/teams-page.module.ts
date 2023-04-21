import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TeamListComponent } from './components/team-list/team-list.component';
import { AllTeamsListComponent } from './components/all-teams-list/all-teams-list.component';
import { TeamCreationComponent } from './components/team-creation/team-creation.component';
import {MaterialModule} from '../material.module';
import { TeamFileComponent } from './components/team-file/team-file.component';
import { ReactiveFormsModule } from '@angular/forms';


@NgModule({
  declarations: [
    TeamListComponent,
    AllTeamsListComponent,
    TeamCreationComponent,
    TeamFileComponent
  ],
  imports: [
    CommonModule,
    MaterialModule,
    ReactiveFormsModule
  ]
})
export class TeamsPageModule { }
