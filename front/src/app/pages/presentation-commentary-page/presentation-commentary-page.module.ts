import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { MaterialModule } from "src/app/material.module";
import { JuryModule } from "src/app/components/jury/jury.module";
import { PresentationCommentaryPageComponent } from "./presentation-commentary-page.component";

@NgModule({
  declarations: [
    PresentationCommentaryPageComponent
  ],
  imports: [
    CommonModule,
    MaterialModule,
    JuryModule
  ]
})
export class PresentationCommentaryPageModule { }
