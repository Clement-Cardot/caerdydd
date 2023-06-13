import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { MaterialModule } from "src/app/material.module";
import { JuryCreationComponent } from "./jury-creation/jury-creation.component";
import { JuryEntityComponent } from './jury-entity/jury-entity.component';
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { JuryCommentaryComponent } from './jury-commentary/jury-commentary.component';
import { RouterModule } from "@angular/router";
import { MaterialFileInputModule } from "ngx-material-file-input";

@NgModule({
    declarations: [
      JuryCreationComponent,
      JuryEntityComponent,
      JuryCommentaryComponent
    ],
    imports: [
      CommonModule,
      MaterialModule,
      FormsModule,
      ReactiveFormsModule,
      RouterModule,
      MaterialFileInputModule,
    ],
    exports: [
      JuryCreationComponent,
      JuryEntityComponent,
      JuryCommentaryComponent
    ]
  })
  export class JuryModule { }