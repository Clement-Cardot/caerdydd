import { Injectable } from '@angular/core';
import { PlannedTimingAvailability, PlannedTimingAvailabilityAdapter } from './planned-timing-availability.model';
import { Team, TeamAdapter } from './team.model';
import { Adapter } from '../adapter';

export class Consulting {
    constructor(
        public idConsulting: number,
        public specialty: string,
        public notes: string,
        public isValidated: boolean,
        public isReserved: boolean,
        public plannedTimingAvailability: PlannedTimingAvailability,
        public team: Team,
    ) {}
}

@Injectable({
    providedIn: 'root'
})
export class ConsultingAdapter implements Adapter<Consulting>{

    constructor(private plannedTimingAvailabilityAdapter: PlannedTimingAvailabilityAdapter, private teamAdapter: TeamAdapter) { }

    adapt(item: any): Consulting {
        return new Consulting(
            item.idConsulting,
            item.Specialty,
            item.Notes,
            item.IsValidated,
            item.IsReserved,
            this.plannedTimingAvailabilityAdapter.adapt(item.plannedTimingAvailability),
            this.teamAdapter.adapt(item.team),
        );
    }
}