import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import {MaterialModule} from '../material.module';
import { RouterModule } from '@angular/router';

import { DevProjectComponent } from './component/dev-project.component';

@NgModule({
  declarations: [
    DevProjectComponent
  ],
  imports: [
    CommonModule,
    RouterModule,
    ReactiveFormsModule,
    MaterialModule
    
  ],
  exports: [
    DevProjectComponent
  ]
})
export class DevProjectModule { }
