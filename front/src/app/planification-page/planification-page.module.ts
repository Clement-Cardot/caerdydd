import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PlanificationPageComponent } from './planification-page/planification-page.component';
import { ConsultingImportComponent } from './consulting-import/consulting-import.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialFileInputModule } from 'ngx-material-file-input';
import { MaterialModule } from '../material.module';

@NgModule({
  declarations: [
    PlanificationPageComponent,
    ConsultingImportComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    MaterialFileInputModule,
    MaterialModule
  ]
})
export class PlanificationPageModule { }
