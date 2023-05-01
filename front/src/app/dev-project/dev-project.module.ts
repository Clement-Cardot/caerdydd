import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { RouterModule } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { DevProjectComponent } from './component/dev-project.component';

@NgModule({
  declarations: [
    DevProjectComponent
  ],
  imports: [
    CommonModule,
    RouterModule,
    MatButtonModule
  ],
  exports: [
    DevProjectComponent
  ]
})
export class DevProjectModule { }
