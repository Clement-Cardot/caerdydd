import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { DevProjectComponent } from './component/dev-project/dev-project.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialFileInputModule } from 'ngx-material-file-input';
import { MaterialModule } from '../material.module';
import { ProjectFileComponent } from './component/team-file/project-file.component';

@NgModule({
  declarations: [
    DevProjectComponent,
    ProjectFileComponent
  ],
  imports: [
    CommonModule,
    RouterModule,
    ReactiveFormsModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
    MaterialFileInputModule,
  ],
  exports: [
    DevProjectComponent,
    ProjectFileComponent
  ]
})
export class DevProjectModule { }
