import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { RouterModule } from '@angular/router';
import { DevProjectComponent } from './component/dev-project/dev-project.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialFileInputModule } from 'ngx-material-file-input';
import { MaterialModule } from '../material.module';
import { TeamFileComponent } from './component/team-file/team-file.component';

@NgModule({
  declarations: [
    DevProjectComponent,
    TeamFileComponent
  ],
  imports: [
    CommonModule,
    RouterModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
    MaterialFileInputModule,
  ],
  exports: [
    DevProjectComponent,
    TeamFileComponent
  ]
})
export class DevProjectModule { }
