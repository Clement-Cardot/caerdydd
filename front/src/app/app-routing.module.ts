import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DashboardComponent } from './dashboard/component/dashboard/dashboard.component';
import { LoginComponent } from './login-page/components/login/login.component';
import { ProjectDescriptionComponent } from './project-description-page/components/project-description/project-description.component';
import { AllTeamsListComponent } from './teams-page/components/all-teams-list/all-teams-list.component';
import { TeamCreationComponent } from './teams-creation-page/components/team-creation/team-creation.component';
import { NotationComponent } from './notation-page/component/notation/notation.component';
import { AuthGuard } from './core/guards/auth.guard';
import { RoleGuard } from './core/guards/role.guard';

const websiteName = " - Taf"

const routes: Routes = [
  { path: '',title: 'Login' + websiteName, component: LoginComponent },

  // Main Pagee
  { path: 'dashboard',title: 'Dashboard' + websiteName, component: DashboardComponent, canActivate: [AuthGuard] },

  // Teams pages
  { path: 'teams',title: 'Teams' + websiteName, component: AllTeamsListComponent, canActivate: [AuthGuard] },
  { path: 'teams-creation',title: 'Création d\'équipes' + websiteName, component: TeamCreationComponent, canActivate: [AuthGuard, RoleGuard], data: {roles: ['OPTION_LEADER_ROLE']}},
  
  // Projects pages
  { path: 'projectD',title: 'Project-description' + websiteName, component: ProjectDescriptionComponent, canActivate: [AuthGuard] },

  // Notation pages
  { path: 'marks',title: 'Notation' + websiteName, component: NotationComponent, canActivate: [AuthGuard] },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
