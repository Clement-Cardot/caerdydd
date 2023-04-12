import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ProjectDescriptionComponent } from './components/project-description/project-description.component';
import { MaterialModule } from '../material.module';
import { ReactiveFormsModule } from '@angular/forms';


@NgModule({
  declarations: [
    ProjectDescriptionComponent
  ],
  imports: [
    CommonModule,
    MaterialModule,
    ReactiveFormsModule
  ], 
  exports:[
    ProjectDescriptionComponent
  ]
})
export class ProjectDescriptionPageModule { }
