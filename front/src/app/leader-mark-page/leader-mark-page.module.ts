import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { LeaderMarkComponent } from './components/leader-mark/leader-mark.component';
import { MaterialModule } from '../material.module';
import { FormsModule  } from '@angular/forms';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';

@NgModule({
  declarations: [
    LeaderMarkComponent,
  ],
  imports: [
    CommonModule,
    MaterialModule,
    FormsModule,
    MatFormFieldModule,
    MatInputModule,
  ],
  exports: [
    LeaderMarkComponent,
  ]
})
export class LeaderMarkPageModule { }
