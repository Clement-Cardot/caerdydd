import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './login-page/components/login/login.component';
import { TeamListComponent } from './teams-page/components/team-list/team-list.component';
import { AllTeamsListComponent } from './teams-page/components/all-teams-list/all-teams-list.component';

const websiteName = " - Taf"

const routes: Routes = [
  { path: '',title: 'Login' + websiteName, component: LoginComponent },
  { path: 'teams',title: 'Teams' + websiteName, component: AllTeamsListComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
