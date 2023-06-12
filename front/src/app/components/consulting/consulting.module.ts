import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MaterialModule } from 'src/app/material.module';
import { MaterialFileInputModule } from 'ngx-material-file-input';
import { ReactiveFormsModule } from '@angular/forms';
import { ConsultingImportComponent } from './consulting-import/consulting-import.component';
import { ConsultingTeachingSAcceptComponent } from './consulting-teaching-s-accept/consulting-teaching-s-accept.component';

@NgModule({
  declarations: [ConsultingImportComponent, ConsultingTeachingSAcceptComponent],
  imports: [
    CommonModule,
    MaterialModule,
    MaterialFileInputModule,
    ReactiveFormsModule,
  ],
  exports: [ConsultingImportComponent,ConsultingTeachingSAcceptComponent],
})
export class ConsultingModule {}
