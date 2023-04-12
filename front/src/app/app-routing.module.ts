import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './login-page/components/login/login.component';
import { AllTeamsListComponent } from './teams-page/components/all-teams-list/all-teams-list.component';
import { DefineSpecialtyComponent } from './teaching-staff-page/components/define-specialty/define-specialty.component';

const websiteName = ' - Taf';

const routes: Routes = [
  { path: '', title: 'Login' + websiteName, component: LoginComponent },
  {
    path: 'teams',
    title: 'Teams' + websiteName,
    component: AllTeamsListComponent,
  },
  {
    path: 'specialty',
    title: 'DefineSpecialty' + websiteName,
    component: DefineSpecialtyComponent,
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
