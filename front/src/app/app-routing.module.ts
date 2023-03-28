import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DashboardComponent } from './dashboard/component/dashboard/dashboard.component';
import { LoginComponent } from './login-page/components/login/login.component';
import { AllTeamsListComponent } from './teams-page/components/all-teams-list/all-teams-list.component';

const websiteName = " - Taf"

const routes: Routes = [
  { path: '',title: 'Login' + websiteName, component: LoginComponent },
  { path: 'teams',title: 'Teams' + websiteName, component: AllTeamsListComponent },
  { path: 'dashboard',title: 'Dashboard' + websiteName, component: DashboardComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
