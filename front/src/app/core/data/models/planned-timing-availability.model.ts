import { Injectable } from '@angular/core';
import { TeachingStaff, TeachingStaffAdapter } from './teaching-staff.model';
import { Adapter } from '../adapter';
import {
  PlannedTimingConsulting,
  PlannedTimingConsultingAdapter,
} from './planned-timing-consulting.model';
import { Consulting, ConsultingAdapter } from './consulting.model';

export class PlannedTimingAvailability {
  constructor(
    public idPlannedTimingAvailability: number,
    public teachingStaff: TeachingStaff,
    public isAvailable: boolean
  ) {}
}
@Injectable({
  providedIn: 'root',
})
export class PlannedTimingAvailabilityAdapter
  implements Adapter<PlannedTimingAvailability>
{
  constructor(private teachingStaffAdapter: TeachingStaffAdapter) {}

  adapt(item: any): PlannedTimingAvailability {
    return new PlannedTimingAvailability(
      item.idPlannedTimingAvailability,
      this.teachingStaffAdapter.adapt(item.teachingStaff),
      item.isAvailable
    );
  }
}
