import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './login-page/components/login/login.component';


const websiteName = " - Taf"

const routes: Routes = [
  { path: '',title: 'Login' + websiteName, component: LoginComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
