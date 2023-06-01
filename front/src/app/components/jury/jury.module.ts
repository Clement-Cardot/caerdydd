import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { MaterialModule } from "src/app/material.module";
import { JuryCreationComponent } from "./jury-creation/jury-creation.component";
import { FormsModule } from "@angular/forms";

@NgModule({
    declarations: [
      JuryCreationComponent
    ],
    imports: [
      CommonModule,
      MaterialModule,
      FormsModule
    ],
    exports: [
      JuryCreationComponent
    ]
  })
  export class JuryModule { }