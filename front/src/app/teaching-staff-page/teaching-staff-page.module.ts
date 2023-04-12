import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DefineSpecialtyComponent } from './components/define-specialty/define-specialty.component';
import { FormsModule } from '@angular/forms';
import { MaterialModule } from '../material.module';

@NgModule({
  declarations: [DefineSpecialtyComponent],
  imports: [CommonModule, FormsModule, MaterialModule],
})
export class TeachingStaffPageModule {}
