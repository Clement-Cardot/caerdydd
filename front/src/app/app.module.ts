import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule, HttpClientXsrfModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CoreModule } from './core/core.module';
import { UserDataService } from './core/services/user-data.service';
import { ApiAuthService } from './core/services/api-auth.service';
import { ApiTeamService } from './core/services/api-team.service';
import { ApiUserService } from './core/services/api-user.service';
import { SidenavModule } from './sidenav/sidenav.module';
import { HashLocationStrategy, LocationStrategy } from '@angular/common';
import { ApiProjectService } from './core/services/api-project.service';
import { ErrorInterceptor } from './core/services/error.interceptor';
import { ApiTeachingStaffService } from './core/services/api-teaching-staff.service';
import { PagesModule } from './pages/pages.module';
import { LoginPageModule } from './pages/login-page/login-page.module';

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
    HttpClientXsrfModule.withOptions({cookieName: 'XSRF-TOKEN'}),
    BrowserAnimationsModule,
    SidenavModule,
    PagesModule,
    LoginPageModule
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
