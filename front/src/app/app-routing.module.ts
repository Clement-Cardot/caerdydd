import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './login-page/components/login/login.component';
import { ProjectDescriptionComponent } from './project-description-page/components/project-description/project-description.component';
import { AllTeamsListComponent } from './teams-page/components/all-teams-list/all-teams-list.component';
import { NotationComponent } from './notation-page/component/notation/notation.component';
import { AuthGuard } from './core/guards/auth.guard';
import { RoleGuard } from './core/guards/role.guard';
import { ConsultingCalendarComponent } from './consulting-page/consulting-calendar/consulting-calendar.component';
import { PlanificationPageComponent } from './planification-page/planification-page/planification-page.component';
import { DashboardComponent } from './dashboard/component/dashboard/dashboard.component';
import { ErrorComponent } from './error/component/error/error.component';
import { ProjectFileComponent } from './dev-project/component/project-file/project-file.component';
import { ProjectListComponent } from './page-validate-subject/components/projects-list/projects-list.component';
import { DevProjectComponent } from './dev-project/component/dev-project/dev-project.component';

const websiteName = ' - Taf';
const routes: Routes = [
  { path: '', title: 'Login' + websiteName, component: LoginComponent },

  // Main Page
  {
    path: 'dashboard',
    title: 'Tableau de bord' + websiteName,
    component: DashboardComponent,
    canActivate: [AuthGuard],
  },

  // Administration page
  /* TODO */ {
    path: 'administration',
    title: 'Administration' + websiteName,
    component: DashboardComponent,
    canActivate: [AuthGuard, RoleGuard],
    data: { roles: ['OPTION_LEADER_ROLE'] },
  },

  // Planification pages
  {
    path: 'planning',
    title: 'Planification' + websiteName,
    component: PlanificationPageComponent,
    canActivate: [AuthGuard, RoleGuard],
    data: { roles: ['PLANNING_ROLE'] },
  },
  {
    path: 'consultings',
    title: 'Consultings' + websiteName,
    component: ConsultingCalendarComponent,
    canActivate: [AuthGuard],
  },

  // Notation pages
  {
    path: 'marks',
    title: 'Notation' + websiteName,
    component: NotationComponent,
    canActivate: [AuthGuard, RoleGuard],
    data: { roles: ['OPTION_LEADER_ROLE', 'TEACHING_STAFF_ROLE'] },
  },

  // Subject page
  {
    path: 'subjects',
    title: 'Sujets' + websiteName,
    component: ProjectListComponent,
    canActivate: [AuthGuard, RoleGuard],
    data: { roles: ['OPTION_LEADER_ROLE'] },
  },

  // Teams pages
  {
    path: 'teams',
    title: 'Teams' + websiteName,
    component: AllTeamsListComponent,
    canActivate: [AuthGuard],
  },
  /* TODO */ {
    path: 'my-team',
    title: 'Teams' + websiteName,
    component: DashboardComponent,
    canActivate: [AuthGuard, RoleGuard],
    data: { roles: ['TEAM_MEMBER_ROLE'] },
  },
  {
    path: 'file',
    title: 'Teams' + websiteName,
    component: ProjectFileComponent,
    canActivate: [AuthGuard],
  },

  // Projects pages
  /* TODO */ {
    path: 'dev-project',
    title: 'Projet de Développement' + websiteName,
    component: DevProjectComponent,
    canActivate: [AuthGuard, RoleGuard],
    data: { roles: ['TEAM_MEMBER_ROLE'] },
  },
  /* TODO */ {
    path: 'validation-project',
    title: 'Projet de Validation' + websiteName,
    component: DashboardComponent,
    canActivate: [AuthGuard, RoleGuard],
    data: { roles: ['TEAM_MEMBER_ROLE'] },
  },

  // Temporaire pour tester
  {
    path: 'projectD',
    title: 'Project-description' + websiteName,
    component: ProjectDescriptionComponent,
    canActivate: [AuthGuard],
  },

  { path: 'error', title: 'Erreur' + websiteName, component: ErrorComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
