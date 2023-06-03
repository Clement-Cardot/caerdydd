import { Injectable } from "@angular/core";
import { TeachingStaff, TeachingStaffAdapter } from "./teaching-staff.model";
import { Adapter } from "../adapter";

export class PlannedTimingAvailability {
    constructor(
        public idPlannedTimingAvailability: number,
        public idPlannedTimingConsulting: number,
        public isAvailable: boolean,
        public teachingStaff: TeachingStaff,
    ) {}
}

@Injectable({
    providedIn: 'root'
})
export class PlannedTimingAvailabilityAdapter implements Adapter<PlannedTimingAvailability>{

    constructor(private teachingStaffAdapter: TeachingStaffAdapter) { }

    adapt(item: any): PlannedTimingAvailability {
        return new PlannedTimingAvailability(
            item.idPlannedTimingAvailability,
            item.idPlannedTimingConsulting,
            item.isAvailable,
            this.teachingStaffAdapter.adapt(item.teachingStaff),
        );
    }
}
