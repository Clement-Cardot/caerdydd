import { NgModule } from '@angular/core';
import { MaterialModule } from 'src/app/material.module';
import { ProjectPageComponent } from './project-page.component';
import { RouterModule } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialFileInputModule } from 'ngx-material-file-input';


@NgModule({
  declarations: [
    ProjectPageComponent
  ],
  imports: [
    MaterialModule,
    RouterModule,
    FormsModule,
    ReactiveFormsModule,
    MaterialFileInputModule,
  ]
})
export class ProjectPageModule { }
