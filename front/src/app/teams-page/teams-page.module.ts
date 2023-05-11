import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TeamListComponent } from './components/team-list/team-list.component';
import { AllTeamsListComponent } from './components/all-teams-list/all-teams-list.component';
import { TeamCreationComponent } from './components/team-creation/team-creation.component';
import {MaterialModule} from '../material.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialFileInputModule } from 'ngx-material-file-input';


@NgModule({
  declarations: [
    TeamListComponent,
    AllTeamsListComponent,
    TeamCreationComponent
  ],
  imports: [
    CommonModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
    MaterialFileInputModule,
  ]
})
export class TeamsPageModule { }
