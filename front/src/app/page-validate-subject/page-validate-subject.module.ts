import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { BrowserModule } from '@angular/platform-browser';
import { BoxProjectComponent } from './components/box-project/box-project.component';
import { ProjectListComponent } from './components/projects-list/projects-list.component';


@NgModule({
  declarations: [
    BoxProjectComponent,
    ProjectListComponent
  ],
  imports: [
    CommonModule,
    BrowserModule,
    MatCardModule
  ]
})
export class ProjectsPageModule { }
