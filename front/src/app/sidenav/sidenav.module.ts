import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MaterialModule } from '../material.module';
import { SidenavComponent } from './sidenav.component';
import { AppRoutingModule } from '../app-routing.module';
import { NotificationModule } from '../components/notification/notification.module';

@NgModule({
  declarations: [
    SidenavComponent
  ],
  imports: [
    CommonModule,
    MaterialModule,
    AppRoutingModule,
    NotificationModule
  ],
  exports: [
    SidenavComponent,
  ]
})
export class SidenavModule { }
