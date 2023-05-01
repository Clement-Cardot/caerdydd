import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import {MaterialModule} from '../material.module';

import { ProjectDescriptionComponent } from './components/project-description/project-description.component';

@NgModule({
  declarations: [
    ProjectDescriptionComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MaterialModule
  ],
  exports: [
    ProjectDescriptionComponent
  ]
})
export class ProjectDescriptionPageModule { }
