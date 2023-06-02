import { NgModule } from '@angular/core';
import { PlanificationPageComponent } from './planification-page.component';
import { ConsultingModule } from 'src/app/components/consulting/consulting.module';
import { JuryModule } from 'src/app/components/jury/jury.module';
import { TeamModule } from 'src/app/components/project/project.module';

@NgModule({
  declarations: [
    PlanificationPageComponent
  ],
  imports: [
    ConsultingModule,
    JuryModule,
    TeamModule
  ]
})
export class PlanificationPageModule { }
