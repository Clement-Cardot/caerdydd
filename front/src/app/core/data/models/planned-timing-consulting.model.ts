import { Injectable } from "@angular/core";
import { Adapter } from "../adapter";
import * as moment from 'moment';
import { CalendarEvent } from "angular-calendar/modules/common/calendar-common.module";
import { EventColor, EventAction } from "calendar-utils";
import { PlannedTimingAvailability, PlannedTimingAvailabilityAdapter } from "./planned-timing-availability.model";

export class PlannedTimingConsulting implements CalendarEvent {

    id: string | number | undefined;
    start: Date;
    end: Date | undefined;
    title: string;
    color: EventColor | undefined;
    actions: EventAction[] | undefined;
    allDay: boolean | undefined;
    cssClass: string | undefined;
    resizable: { beforeStart: boolean | undefined; afterEnd: boolean | undefined; } | undefined;
    draggable: boolean | undefined;
    meta?: any;

    constructor(
        public idPlannedTimingConsulting: number,
        public datetimeBegin: Date,
        public datetimeEnd: Date,
        public availabilities: PlannedTimingAvailability[]
    ) {
        this.id = idPlannedTimingConsulting;
        this.start = datetimeBegin;
        this.end = datetimeEnd;
        this.title = "Consulting";
    }
    
}

@Injectable({
    providedIn: 'root'
})
export class PlannedTimingConsultingAdapter implements Adapter<PlannedTimingConsulting>{

    constructor(private plannedTimingAvailabilityAdapter: PlannedTimingAvailabilityAdapter) { }


    adapt(item: any): PlannedTimingConsulting {
        let availabilities: PlannedTimingAvailability[] = [];
        if (item.teachingStaffAvailabilities != null){
            item.teachingStaffAvailabilities.forEach((availability: any) => {
                availabilities.push(this.plannedTimingAvailabilityAdapter.adapt(availability));
            });
        }

        return new PlannedTimingConsulting(
            item.idPlannedTimingConsulting,
            moment(item.datetimeBegin).toDate(),
            moment(item.datetimeEnd).toDate(),
            availabilities
        );
    }
}