import { NgModule } from '@angular/core';
import { MaterialModule } from 'src/app/material.module';
import { ProjectPageComponent } from './project-page.component';
import { RouterModule } from '@angular/router';
import { ConsultingModule } from 'src/app/components/consulting/consulting.module';
import { CommonModule } from '@angular/common';


@NgModule({
  declarations: [
    ProjectPageComponent
  ],
  imports: [
    CommonModule,
    MaterialModule,
    RouterModule,
    ConsultingModule
  ]
})
export class ProjectPageModule { }
