import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Project } from '../../../core/data/models/project.model';


@Component({
  selector: 'app-box-validate-subject',
  templateUrl: './box-project.component.html',
  styleUrls: ['./box-project.component.scss']
})
export class BoxProjectComponent {
  @Input() project!: Project;
  constructor() { }

  doValidateSubject() {
    this.project.is_validated = true;
  }
}
