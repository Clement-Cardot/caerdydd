import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginPageModule } from './login-page/login-page.module';
import { LeaderMarkPageModule } from './leader-mark-page/leader-mark-page.module';
import { CoreModule } from './core/core.module';
import {TeamsPageModule } from './teams-page/teams-page.module';
@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    CoreModule,
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    BrowserAnimationsModule,
    LoginPageModule,
    LeaderMarkPageModule,
    TeamsPageModule
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
