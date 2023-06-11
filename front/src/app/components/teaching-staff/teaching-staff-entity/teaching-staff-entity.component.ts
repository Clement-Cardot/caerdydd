import { Component, Input } from '@angular/core';
import { TeachingStaff } from 'src/app/core/data/models/teaching-staff.model';

@Component({
  selector: 'app-teaching-staff-entity',
  templateUrl: './teaching-staff-entity.component.html',
  styleUrls: ['./teaching-staff-entity.component.scss']
})
export class TeachingStaffEntityComponent {
  @Input() teachingStaff!: TeachingStaff;
}
