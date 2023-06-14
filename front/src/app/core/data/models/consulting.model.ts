import { Injectable } from '@angular/core';
import { Team, TeamAdapter } from './team.model';
import { Adapter } from '../adapter';
import {
  PlannedTimingAvailability,
  PlannedTimingAvailabilityAdapter,
} from './planned-timing-availability.model';
import {
  PlannedTimingConsulting,
  PlannedTimingConsultingAdapter,
} from './planned-timing-consulting.model';

export class Consulting {

  constructor(
    public idConsulting: number | null,
    public speciality: string,
    public notes: string | null,
    public team: Team | null,
    public plannedTimingAvailability: PlannedTimingAvailability | null,
    public plannedTimingConsulting: PlannedTimingConsulting
) {}

}

@Injectable({
  providedIn: 'root',
})
export class ConsultingAdapter implements Adapter<Consulting> {
  constructor(
    private teamAdapter: TeamAdapter,
    private plannedTimingConsultingAdapter: PlannedTimingConsultingAdapter,
    private plannedTimingAvailabilityAdapter: PlannedTimingAvailabilityAdapter
  ) {} 

  adapt(item: any): Consulting {
    let plannedTimingAvailability;
    if (item.plannedTimingAvailability == null) {
      plannedTimingAvailability = null;
    } else {
      plannedTimingAvailability = this.plannedTimingAvailabilityAdapter.adapt(
        item.plannedTimingAvailability
      );
    }
    return new Consulting(
      item.idConsulting,
      item.speciality,
      item.notes,
      this.teamAdapter.adapt(item.team),
      plannedTimingAvailability,
      this.plannedTimingConsultingAdapter.adapt(item.plannedTimingConsulting)
    );
  }
}