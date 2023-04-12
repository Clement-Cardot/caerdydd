import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DashboardComponent } from './dashboard/component/dashboard/dashboard.component';
import { LoginComponent } from './login-page/components/login/login.component';
import { ViewValidateSubjectsComponent } from './page-validate-subject/components/view-validate-subjects/view-validate-subjects.component';
import { ProjectDescriptionComponent } from './project-description-page/components/project-description/project-description.component';
import { AllTeamsListComponent } from './teams-page/components/all-teams-list/all-teams-list.component';
import { TeamCreationComponent } from './teams-creation-page/components/team-creation/team-creation.component';

const websiteName = " - Taf"

const routes: Routes = [
  { path: '',title: 'Login' + websiteName, component: LoginComponent },
  { path: 'teams_creation',title: 'Création d\'équipes' + websiteName, component: TeamCreationComponent },
  { path: 'teams',title: 'Teams' + websiteName, component: AllTeamsListComponent },
  { path: 'projectD',title: 'Project-description' + websiteName, component: ProjectDescriptionComponent },
  { path: 'dashboard',title: 'Dashboard' + websiteName, component: DashboardComponent },
  { path: 'teams',title: 'Teams' + websiteName, component: AllTeamsListComponent },
  { path: 'dashboard',title: 'Dashboard' + websiteName, component: DashboardComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
