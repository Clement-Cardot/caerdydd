import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TeamListComponent } from './components/team-list/team-list.component';
import { AllTeamsListComponent } from './components/all-teams-list/all-teams-list.component';
import { TeamCreationComponent } from './components/team-creation/team-creation.component';
import {MaterialModule} from '../material.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialFileInputModule } from 'ngx-material-file-input';
import { TeamInfoComponent } from './components/team-info/team-info.component';
import { RouterModule } from '@angular/router';


@NgModule({
  declarations: [
    TeamListComponent,
    AllTeamsListComponent,
    TeamCreationComponent,
    TeamInfoComponent
  ],
  imports: [
    CommonModule,
    RouterModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
    MaterialFileInputModule,
  ]
})
export class TeamsPageModule { }
