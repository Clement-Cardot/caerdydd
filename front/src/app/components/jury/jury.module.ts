import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { MaterialModule } from "src/app/material.module";
import { JuryCreationComponent } from "./jury-creation/jury-creation.component";
import { FormsModule } from "@angular/forms";
import { JuryEntityComponent } from './jury-entity/jury-entity.component';

@NgModule({
    declarations: [
      JuryCreationComponent,
      JuryEntityComponent
    ],
    imports: [
      CommonModule,
      MaterialModule,
      FormsModule
    ],
    exports: [
      JuryCreationComponent,
      JuryEntityComponent
    ]
  })
  export class JuryModule { }