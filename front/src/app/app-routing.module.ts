import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './login-page/components/login/login.component';
import { ViewValidateSubjectsComponent } from './page-validate-subject/components/view-validate-subjects/view-validate-subjects.component';

const websiteName = " - Taf"

const routes: Routes = [
  { path: '',title: 'Login' + websiteName, component: LoginComponent },

  // Subject page
  { path: 'subjects',title: 'Subjects' + websiteName, component: ViewValidateSubjectsComponent }
];

  @NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
  })
  
  export class AppRoutingModule { }
  
