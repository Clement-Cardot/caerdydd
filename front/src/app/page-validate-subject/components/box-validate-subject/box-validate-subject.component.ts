import { Component, Input, OnInit } from '@angular/core';
import { ValidateSubject } from '../../models/validate-subjects.model';


@Component({
  selector: 'app-box-validate-subject',
  templateUrl: './box-validate-subject.component.html',
  styleUrls: ['./box-validate-subject.component.scss']
})
export class BoxValidateSubjectComponent implements OnInit {
  @Input() validateSubject!: ValidateSubject;
  constructor() { }

  ngOnInit() {
  }

}
