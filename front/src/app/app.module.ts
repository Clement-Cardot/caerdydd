import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginPageModule } from './login-page/login-page.module';
import { CoreModule } from './core/core.module';
import { TeamsPageModule } from './teams-page/teams-page.module';
import { TeachingStaffPageModule } from './teaching-staff-page/teaching-staff-page.module';
@NgModule({
  declarations: [AppComponent],
  imports: [
    CoreModule,
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    BrowserAnimationsModule,
    LoginPageModule,
    TeamsPageModule,
    TeachingStaffPageModule,
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
