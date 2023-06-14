import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { MaterialModule } from "src/app/material.module";

import { FormsModule } from "@angular/forms";
import { NotificationComponent } from "./notification/notification.component";

@NgModule({
    declarations: [
      NotificationComponent
    ],
    imports: [
      CommonModule,
      MaterialModule,
      FormsModule
    ],
    exports: [
        NotificationComponent
    ]
  })
  
  export class NotificationModule { }