import {Injectable} from '@angular/core';
import {Adapter} from '../adapter';
import { Team, TeamAdapter } from './team.model';
import { PlannedTimingAvailability, PlannedTimingAvailabilityAdapter } from './planned-timing-availability.model';
import { PlannedTimingConsulting, PlannedTimingConsultingAdapter } from './planned-timing-consulting.model';

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
    providedIn: 'root'
})

export class ConsultingAdapter implements Adapter<Consulting>{

    constructor(private teamAdapter: TeamAdapter, private plannedTimingAvailabilityAdapter: PlannedTimingAvailabilityAdapter, private plannedTimingConsultingAdapter: PlannedTimingConsultingAdapter) { }

    adapt(item: any): Consulting {
        return new Consulting(
            item.idConsulting,
            item.speciality,
            item.notes,
            this.teamAdapter.adapt(item.team),
            this.plannedTimingAvailabilityAdapter.adapt(item.plannedTimingAvailability),
            this.plannedTimingConsultingAdapter.adapt(item.plannedTimingConsulting)
        );
    }
}