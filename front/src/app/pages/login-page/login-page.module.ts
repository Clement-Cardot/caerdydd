import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { MaterialModule } from "src/app/material.module";
import { LoginPageComponent } from "./login-page.component";
import { ReactiveFormsModule } from "@angular/forms";

@NgModule({
    declarations: [
      LoginPageComponent
    ],
    imports: [
      CommonModule,
      MaterialModule,
      ReactiveFormsModule
    ],
    exports: [
      LoginPageComponent
    ]
  })
  export class LoginPageModule { }