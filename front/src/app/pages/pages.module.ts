import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MaterialModule } from '../material.module';
import { CalendarPageModule } from './calendar-page/calendar-page.module';
import { ConsultingPageModule } from './consulting-page/consulting-page.module';
import { DashboardPageModule } from './dashboard-page/dashboard-page.module';
import { ErrorPageModule } from './error-page/error-page.module';
import { LoginPageModule } from './login-page/login-page.module';
import { NotationPageModule } from './notation-page/notation-page.module';
import { DevProjectPageModule } from './dev-project-page/dev-project-page.module';
import { PlanificationPageModule } from './planification-page/planification-page.module';
import { ProjectDescriptionPageModule } from './project-description-page/project-description-page.module';
import { TeachingStaffPageModule } from './teaching-staff-page/teaching-staff-page.module';
import { TeamsPageModule } from './teams-page/teams-page.module';
import { ValidateSubjectPageModule } from './validate-subject-page/validate-subject-page.module';
import { ValidationProjectPageModule } from './validation-project-page/validation-project-page.module';
import { ProjectPageModule } from './project-page/project-page.module';


@NgModule({
  declarations: [
  ],
  imports: [
    CommonModule,
    MaterialModule
  ],
  exports: [
    CalendarPageModule,
    ConsultingPageModule,
    DashboardPageModule,
    DevProjectPageModule,
    ErrorPageModule,
    LoginPageModule,
    NotationPageModule,
    PlanificationPageModule,
    ProjectDescriptionPageModule,
    ProjectPageModule,
    TeachingStaffPageModule,
    TeamsPageModule,
    ValidateSubjectPageModule,
    ValidationProjectPageModule
  ]
})
export class PagesModule { }
