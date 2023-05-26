import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { MaterialModule } from "src/app/material.module";
import { DashboardPageComponent } from "./dashboard-page.component";

@NgModule({
    declarations: [
        DashboardPageComponent
    ],
    imports: [
      CommonModule,
      MaterialModule
    ],
    exports: [
    ]
  })
  export class DashboardPageModule { }