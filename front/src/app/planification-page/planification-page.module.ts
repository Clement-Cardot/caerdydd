import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PlanificationPageComponent } from './planification-page/planification-page.component';
import { ConsultingImportComponent } from './consulting-import/consulting-import.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialFileInputModule } from 'ngx-material-file-input';
import { MaterialModule } from '../material.module';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { MatButtonModule } from '@angular/material/button';
import { StudentImportComponent } from './student-import/student-import.component';
import { JuryCreationComponent } from './jury-creation/jury-creation.component';

@NgModule({
  declarations: [
    PlanificationPageComponent,
    ConsultingImportComponent,
    StudentImportComponent,
    JuryCreationComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    MaterialFileInputModule,
    MaterialModule,
    CommonModule,
    FormsModule,
    MatFormFieldModule,
    MatSelectModule,
    MatButtonModule
  ]
})
export class PlanificationPageModule { }
