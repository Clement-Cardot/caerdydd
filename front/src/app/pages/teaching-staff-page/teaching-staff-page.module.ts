import { NgModule } from '@angular/core';
import { TeachingStaffPageComponent } from './teaching-staff-page.component';
import { MaterialModule } from 'src/app/material.module';
import { CommonModule } from '@angular/common';
import { TeachingStaffModule } from "../../components/teaching-staff/teaching-staff.module";

@NgModule({
    declarations: [
        TeachingStaffPageComponent
    ],
    imports: [
        CommonModule,
        MaterialModule,
        TeachingStaffModule
    ]
})
export class TeachingStaffPageModule {}
