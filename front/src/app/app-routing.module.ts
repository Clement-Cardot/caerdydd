import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './login-page/components/login/login.component';
import { ViewValidateSubjectsComponent } from './page-validate-subject/components/view-validate-subjects/view-validate-subjects.component';
import { ProjectDescriptionComponent } from './project-description-page/components/project-description/project-description.component';
import { AllTeamsListComponent } from './teams-page/components/all-teams-list/all-teams-list.component';

const websiteName = " - Taf"

const routes: Routes = [
  { path: '',title: 'Login' + websiteName, component: LoginComponent },
  { path: 'projectD',title: 'Project-description' + websiteName, component: ProjectDescriptionComponent },
@NgModule({
  exports: [RouterModule]
})
export class AppRoutingModule { }
