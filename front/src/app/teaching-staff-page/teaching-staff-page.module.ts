import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MaterialModule } from '../material.module';
import { AppRoutingModule } from '../app-routing.module';
import { AllTeachingStaffComponent } from './components/all-teaching-staff/all-teaching-staff/all-teaching-staff.component';
import { DefineSpecialtyComponent } from './components/define-specialty/define-specialty.component';
import { FormsModule } from '@angular/forms';

@NgModule({
  declarations: [DefineSpecialtyComponent, AllTeachingStaffComponent],
  imports: [CommonModule, MaterialModule, AppRoutingModule, FormsModule],
})
export class TeachingStaffPageModule {}
