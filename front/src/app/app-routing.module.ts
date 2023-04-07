import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './login-page/components/login/login.component';
import { LeaderMarkComponent } from './leader-mark-page/components/leader-mark/leader-mark.component';


const websiteName = " - Taf"

const routes: Routes = [
  { path: '',title: 'Login' + websiteName, component: LoginComponent },
  { path: 'mark',title: 'Notes' + websiteName, component: LeaderMarkComponent },
  { path: 'mark/:idTeam',title: 'Notes' + websiteName, component: LeaderMarkComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
