import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginPageModule } from './login-page/login-page.module';
import { CoreModule } from './core/core.module';
import { TeamsCreationPageModule } from './teams-creation-page/teams-creation-page.module';
import { ProjectDescriptionPageModule } from './project-description-page/project-description-page.module';
import { PageValidateSubjectModule } from './page-validate-subject/page-validate-subject.module';
import {TeamsPageModule } from './teams-page/teams-page.module';
import { UserDataService } from './core/services/user-data.service';
import { ApiAuthService } from './core/services/api-auth.service';
import { ApiTeamService } from './core/services/api-team.service';
import { ApiUserService } from './core/services/api-user.service';
import { SidenavModule } from './sidenav/sidenav.module';

@NgModule({
  declarations: [
    AppComponent
  ],
  providers: [
    UserDataService,
    ApiAuthService,
    ApiTeamService,
    ApiUserService
  ],
  imports: [
    CoreModule,
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    BrowserAnimationsModule,
    LoginPageModule,
    TeamsCreationPageModule,
    ProjectDescriptionPageModule,
    PageValidateSubjectModule,
    TeamsPageModule,
    SidenavModule,
    TeamsPageModule
  ],
  bootstrap: [AppComponent],
})
export class AppModule { }
