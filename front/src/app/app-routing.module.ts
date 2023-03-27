import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HelloWorldComponent } from './hello-world/hello-world.component';
import { LoginComponent } from './login-page/components/login/login.component';


const websiteName = " - Taf"

const routes: Routes = [
  {path: '', component: HelloWorldComponent},
  {path: 'hello-world', component: HelloWorldComponent},
  { path: 'login',title: 'Login' + websiteName, component: LoginComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
