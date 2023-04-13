import { Component, OnInit } from '@angular/core';
import { ValidateSubject } from '../../models/validate-subjects.model';
import { ValidateSubjectService } from '../../services/validate-subject.service';

@Component({
  selector: 'app-view-validate-subjects',
  templateUrl: './view-validate-subjects.component.html',
  styleUrls: ['./view-validate-subjects.component.scss']
})
export class ViewValidateSubjectsComponent implements OnInit {
  validateSubjectList!: ValidateSubject[];
  constructor(private validateSubjectService: ValidateSubjectService) {}

  ngOnInit() {
    this.validateSubjectList = this.validateSubjectService.getAllValidateSubject();
  }
}
