import { NgModule } from '@angular/core';
import { PlanificationPageComponent } from './planification-page.component';
import { ConsultingModule } from 'src/app/components/consulting/consulting.module';
import { JuryModule } from 'src/app/components/jury/jury.module';
import { TeamModule } from 'src/app/components/project/project.module';
import { PresentationModule } from 'src/app/components/presentation/presentation.module';
import { CommonModule } from '@angular/common';

@NgModule({
  declarations: [
    PlanificationPageComponent
  ],
  imports: [
    CommonModule,
    ConsultingModule,
    JuryModule,
    TeamModule,
    PresentationModule
  ]
})
export class PlanificationPageModule { }
