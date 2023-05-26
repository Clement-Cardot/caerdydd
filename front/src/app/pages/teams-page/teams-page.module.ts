import { NgModule } from '@angular/core';
import { TeamsPageComponent } from './teams-page.component';
import { TeamModule } from 'src/app/components/teams/team.module';


@NgModule({
  declarations: [
    TeamsPageComponent,
  ],
  imports: [
    TeamModule
  ]
})
export class TeamsPageModule { }
