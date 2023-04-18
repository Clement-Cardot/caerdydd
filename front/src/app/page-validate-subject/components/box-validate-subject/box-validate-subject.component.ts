import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ValidateSubject } from '../../models/validate-subjects.model';


@Component({
  selector: 'app-box-validate-subject',
  templateUrl: './box-validate-subject.component.html',
  styleUrls: ['./box-validate-subject.component.scss']
})
export class BoxValidateSubjectComponent {
  @Input() validateSubject!: ValidateSubject;
  constructor() { }

  doValidateSubject() {
    this.validateSubject.is_validated = true;
  }
}
