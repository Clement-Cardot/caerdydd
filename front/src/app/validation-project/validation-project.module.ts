import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import {MaterialModule} from '../material.module';
import { RouterModule } from '@angular/router';
import { ValidationProjectComponent } from './component/validation-project/validation-project.component';



@NgModule({
  declarations: [
    ValidationProjectComponent
  ],
  imports: [
    CommonModule,
    RouterModule,
    ReactiveFormsModule,
    MaterialModule
    
  ],
  exports: [
    ValidationProjectComponent
  ]
})
export class ValidationProjectModule { }
