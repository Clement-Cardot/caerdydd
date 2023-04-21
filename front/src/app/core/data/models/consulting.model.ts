import { Injectable } from "@angular/core";
import { Adapter } from "../adapter";
import { Team, TeamAdapter } from "./team.model";
import * as moment from 'moment';
import { CalendarEvent } from "angular-calendar/modules/common/calendar-common.module";
import { EventColor, EventAction } from "calendar-utils";

export class Consulting implements CalendarEvent {

    id?: string | number | undefined;
    start: Date;
    end?: Date | undefined;
    title: string;
    color?: EventColor | undefined;
    actions?: EventAction[] | undefined;
    allDay?: boolean | undefined;
    cssClass?: string | undefined;
    resizable?: { beforeStart?: boolean | undefined; afterEnd?: boolean | undefined; } | undefined;
    draggable?: boolean | undefined;
    meta?: any;

    constructor(
        public idConsulting: number,
        public datetimeBegin: Date,
        public datetimeEnd: Date,
        public speciality: string,
        public notes: string,
        public isValidated: boolean,
        public isReserved: boolean,
        //public team: Team
    ) {
        this.id = idConsulting;
        this.start = datetimeBegin;
        this.end = datetimeEnd;
        this.title = "Consulting";
    }
    
}

@Injectable({
    providedIn: 'root'
})
export class ConsultingAdapter implements Adapter<Consulting>{

    constructor(private teamAdapter: TeamAdapter) { }


    adapt(item: any): Consulting {
        return new Consulting(
            item.idConsulting,
            moment(item.datetimeBegin).toDate(),
            moment(item.datetimeEnd).toDate(),
            item.speciality,
            item.notes,
            item.isValidated,
            item.isReserved,
            //this.teamAdapter.adapt(item.team)
        );
    }
}