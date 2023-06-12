import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MaterialModule } from 'src/app/material.module';
import { ConsultingPageComponent } from './consulting-page.component';
import { ConsultingModule } from 'src/app/components/consulting/consulting.module';

@NgModule({
  declarations: [ConsultingPageComponent],
  imports: [CommonModule, MaterialModule, ConsultingModule],
  exports: [],
})
export class ConsultingPageModule {}
