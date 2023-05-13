import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginPageModule } from './login-page/login-page.module';
import { CoreModule } from './core/core.module';
import { ProjectDescriptionPageModule } from './project-description-page/project-description-page.module';

import { ProjectsPageModule } from './page-validate-subject/page-validate-subject.module';
import { TeamsPageModule } from './teams-page/teams-page.module';
import { UserDataService } from './core/services/user-data.service';
import { ApiAuthService } from './core/services/api-auth.service';
import { ApiTeamService } from './core/services/api-team.service';
import { ApiUserService } from './core/services/api-user.service';
import { SidenavModule } from './sidenav/sidenav.module';
import { NotationModule } from './notation-page/notation.module';
import { HashLocationStrategy, LocationStrategy } from '@angular/common';
import { ConsultingPageModule } from './consulting-page/consulting-page.module';
import { PlanificationPageModule } from './planification-page/planification-page.module';
import { ApiProjectService } from './core/services/api-project.service';
import { DevProjectModule } from './dev-project/dev-project.module';
import { ErrorInterceptor } from './core/services/error.interceptor';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { TeachingStaffPageModule } from './teaching-staff-page/teaching-staff-page.module';
import { ApiTeachingStaffService } from './core/services/api-teaching-staff.service';

@NgModule({
  declarations: [AppComponent],
  providers: [
    UserDataService,
    ApiAuthService,
    ApiTeamService,
    ApiUserService,
    ApiProjectService,
    ApiTeachingStaffService,
    { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true },
    { provide: LocationStrategy, useClass: HashLocationStrategy },
  ],
  imports: [
    CoreModule,
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    BrowserAnimationsModule,
    LoginPageModule,
    ProjectDescriptionPageModule,
    SidenavModule,
    TeamsPageModule,
    NotationModule,
    ConsultingPageModule,
    PlanificationPageModule,
    DevProjectModule,
    ProjectsPageModule,
    TeachingStaffPageModule,
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
