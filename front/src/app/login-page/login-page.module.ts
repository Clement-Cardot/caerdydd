import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { LoginComponent } from './components/login/login.component';
import { MaterialModule } from '../material.module';
import { ReactiveFormsModule } from '@angular/forms';
import { CoreServiceService } from '../core/services/core-service.service';

@NgModule({
  declarations: [
    LoginComponent,
  ],
  imports: [
    CommonModule,
    MaterialModule,
    ReactiveFormsModule,
  ],
  exports: [
    LoginComponent,
  ],
  providers: [
    CoreServiceService
  ],
})
export class LoginPageModule { }
