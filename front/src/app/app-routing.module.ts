import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AuthGuard } from './core/guards/auth.guard';
import { RoleGuard } from './core/guards/role.guard';
import { LoginPageComponent } from './pages/login-page/login-page.component';
import { DashboardPageComponent } from './pages/dashboard-page/dashboard-page.component';
import { PlanificationPageComponent } from './pages/planification-page/planification-page.component';
import { CalendarPageComponent } from './pages/calendar-page/calendar-page.component';
import { NotationPageComponent } from './pages/notation-page/notation-page.component';
import { ValidateSubjectPageComponent } from './pages/validate-subject-page/validate-subject-page.component';
import { TeamsPageComponent } from './pages/teams-page/teams-page.component';
import { DevProjectPageComponent } from './pages/dev-project-page/dev-project-page.component';
import { ValidationProjectPageComponent } from './pages/validation-project-page/validation-project-page.component';
import { ProjectDescriptionPageComponent } from './pages/project-description-page/project-description-page.component';
import { ErrorPageComponent } from './pages/error-page/error-page.component';
import { TeachingStaffPageComponent } from './pages/teaching-staff-page/teaching-staff-page.component';
import { ProjectPageComponent } from './pages/project-page/project-page.component';

const websiteName = " - Taf"

const routes: Routes = [
  { path: '', title: 'Login' + websiteName, component: LoginPageComponent },

  // Main Page
  { path: 'dashboard', title: 'Tableau de bord' + websiteName, component: DashboardPageComponent, canActivate: [AuthGuard] },

  // Administration page
  /* TODO */ { path: 'administration', title: 'Administration' + websiteName, component: DashboardPageComponent, canActivate: [AuthGuard, RoleGuard], data: {roles: ['OPTION_LEADER_ROLE']}},

  // Planification pages
  { path: 'planning', title: 'Planification' + websiteName, component: PlanificationPageComponent, canActivate: [AuthGuard, RoleGuard], data: {roles: ['PLANNING_ROLE']}},
  { path: 'calendar', title: 'Calendrier' + websiteName, component: CalendarPageComponent, canActivate: [AuthGuard] },

  // Notation pages
  { path: 'marks', title: 'Notes' + websiteName, component: NotationPageComponent, canActivate: [AuthGuard, RoleGuard], data: {roles: ['OPTION_LEADER_ROLE', 'TEACHING_STAFF_ROLE']}},

  // Subject page
  { path: 'subjects',title: 'Sujets' + websiteName, component: ValidateSubjectPageComponent, canActivate: [AuthGuard, RoleGuard], data: {roles: ['OPTION_LEADER_ROLE']}},

  // Teams pages
  { path: 'teams', title: 'Equipes' + websiteName, component: TeamsPageComponent, canActivate: [AuthGuard] },

  // Projects pages
  { path: 'project/:id', title: 'Projet' + websiteName, component: ProjectPageComponent, canActivate: [AuthGuard]},
  { path: 'dev-project', title: 'Projet de Développement' + websiteName, component: DevProjectPageComponent, canActivate: [AuthGuard, RoleGuard], data: {roles: ['TEAM_MEMBER_ROLE']}},
  { path: 'validation-project', title: 'Projet de Validation' + websiteName, component: ValidationProjectPageComponent, canActivate: [AuthGuard, RoleGuard], data: {roles: ['TEAM_MEMBER_ROLE']}},

  // Teaching Staff Pages
  { path: 'teachingStaff', title: 'Corps Enseignant' + websiteName, component: TeachingStaffPageComponent, canActivate: [AuthGuard, RoleGuard], data: { roles: ['TEACHING_STAFF_ROLE'] }},

  // Temporaire pour tester
  { path: 'projectD', title: 'Project-description' + websiteName, component: ProjectDescriptionPageComponent, canActivate: [AuthGuard] },
  
  { path: 'error', title: 'Erreur' + websiteName, component: ErrorPageComponent },

  ];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
