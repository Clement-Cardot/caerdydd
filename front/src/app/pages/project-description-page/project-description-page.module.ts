import { NgModule } from '@angular/core';
import { ProjectDescriptionPageComponent } from './project-description-page.component';
import { MaterialModule } from 'src/app/material.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';


@NgModule({
  declarations: [
    ProjectDescriptionPageComponent
  ],
  imports: [
    MaterialModule,
    ReactiveFormsModule
  ]
})
export class ProjectDescriptionPageModule { }
