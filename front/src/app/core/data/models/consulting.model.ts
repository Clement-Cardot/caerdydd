import { Injectable } from '@angular/core';
import { PlannedTimingAvailability, PlannedTimingAvailabilityAdapter } from './planned-timing-availability.model';
import { Team, TeamAdapter } from './team.model';
import { Adapter } from '../adapter';
import { PlannedTimingConsulting } from './planned-timing-consulting.model';

export class Consulting {
    constructor(
        public speciality: string,
        public plannedTimingConsulting: PlannedTimingConsulting,
        public team?: Team,
        public idConsulting?: number,
        public notes?: string,
        public plannedTimingAvailability?: PlannedTimingAvailability
    ) {}
}

@Injectable({
    providedIn: 'root'
})
export class ConsultingAdapter implements Adapter<Consulting>{

    constructor(private plannedTimingAvailabilityAdapter: PlannedTimingAvailabilityAdapter, private teamAdapter: TeamAdapter) { }

    adapt(item: any): Consulting {
        return new Consulting(
            item.speciality,
            item.plannedTimingConsulting
        );
    }
}