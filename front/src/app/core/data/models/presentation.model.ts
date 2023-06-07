import { CalendarEvent} from "angular-calendar/modules/common/calendar-common.module";
import { EventColor, EventAction } from "calendar-utils";
import { Adapter } from "../adapter";
import { Injectable } from "@angular/core";
import { Jury, JuryAdapter } from "./jury.model";
import { Project, ProjectAdapter } from "./project.model";

export class Presentation implements CalendarEvent {
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
        public idPresentation: number,
        public type: string,
        public datetimeBegin: Date,
        public datetimeEnd: Date,
        public room: string,
        public jury1Notes: string,
        public jury2Notes: string,
        public jury: Jury,
        public project: Project
    ) {
        this.id = idPresentation;
        this.start = datetimeBegin;
        this.end = datetimeEnd;
        this.title = `Présentation ${type} en ${room} de ${datetimeBegin.toLocaleTimeString()} à ${datetimeEnd.toLocaleTimeString()}`;
        this.color = {primary: '#40A798', secondary: '#D1F2EB'};
    }
}

export interface PresentationPayload {
    type: string,
    datetimeBegin: Date,
    datetimeEnd: Date,
    room: string,
    jury: {
        idJury: number
    },
    project: {
        idProject: number
    }
}

@Injectable({
    providedIn: 'root'
})
export class PresentationAdapter implements Adapter<Presentation> {

    constructor(private juryAdapter: JuryAdapter,
                private projectAdapter: ProjectAdapter) {}

    adapt(item: any): Presentation {
        return new Presentation(
            item.idPresentation,
            item.type,
            new Date(item.datetimeBegin),
            new Date(item.datetimeEnd),
            item.room,
            item.jury1Notes,
            item.jury2Notes,
            this.juryAdapter.adapt(item.jury),
            this.projectAdapter.adapt(item.project)
        );
    }
}
