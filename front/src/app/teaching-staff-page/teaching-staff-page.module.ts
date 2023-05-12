import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DefineSpecialtyComponent } from './components/define-specialty/define-specialty.component';
import { FormsModule } from '@angular/forms';
import { MaterialModule } from '../material.module';
import { AllTeachingStaffComponent } from './components/all-teaching-staff/all-teaching-staff.component';
import { AppRoutingModule } from '../app-routing.module';

@NgModule({
  declarations: [DefineSpecialtyComponent, AllTeachingStaffComponent],
  imports: [CommonModule, FormsModule, MaterialModule, AppRoutingModule],
})
export class TeachingStaffPageModule {}
