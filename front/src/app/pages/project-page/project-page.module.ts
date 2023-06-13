import { NgModule } from '@angular/core';
import { MaterialModule } from 'src/app/material.module';
import { ProjectPageComponent } from './project-page.component';
import { RouterModule } from '@angular/router';
import { ConsultingModule } from 'src/app/components/consulting/consulting.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialFileInputModule } from 'ngx-material-file-input';
import { CommonModule } from '@angular/common';


@NgModule({
  declarations: [
    ProjectPageComponent
  ],
  imports: [
    CommonModule,
    MaterialModule,
    RouterModule,
    ConsultingModule,
    FormsModule,
    ReactiveFormsModule,
    MaterialFileInputModule,
  ]
})
export class ProjectPageModule { }
