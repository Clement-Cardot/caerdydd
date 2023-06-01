import { NgModule } from '@angular/core';
import { MaterialModule } from 'src/app/material.module';
import { ProjectPageComponent } from './project-page.component';
import { RouterModule } from '@angular/router';


@NgModule({
  declarations: [
    ProjectPageComponent
  ],
  imports: [
    MaterialModule,
    RouterModule,
  ]
})
export class ProjectPageModule { }
