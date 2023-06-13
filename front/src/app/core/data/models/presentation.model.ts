import { CalendarEvent} from "angular-calendar/modules/common/calendar-common.module";
import { EventColor, EventAction } from "calendar-utils";
import { Adapter } from "../adapter";
import { Injectable } from "@angular/core";
import { Jury, JuryAdapter } from "./jury.model";
import { Project, ProjectAdapter } from "./project.model";

export class Presentation implements CalendarEvent {
    id: string | number;
    start: Date;
    end: Date;
    title: string;
    color: EventColor;
    actions: EventAction[] = [];
    allDay: boolean = false;
    cssClass: string = '';
    resizable!: {
        beforeStart: boolean,
        afterEnd: boolean,
    };
    draggable: boolean = false;
    meta: any = {};
    
    constructor(
        public idPresentation: number,
        public type: string,
        public datetimeBegin: Date,
        public datetimeEnd: Date,
        public room: string,
        public jury1Notes: string,
        public jury2Notes: string,
        public validationTeamNotes: string,
        public teachingStaffNotes: string,
        public jury: Jury,
        public project: Project | undefined
    ) {
        this.id = idPresentation;
        this.start = datetimeBegin;
        this.end = datetimeEnd;
        this.title = `${type} en Salle ${room} de ${datetimeBegin.toLocaleTimeString()} à ${datetimeEnd.toLocaleTimeString()}`;
        this.color = {primary: '#40A798', secondary: '#D1F2EB'};
    }
}



@Injectable({
    providedIn: 'root'
})
export class PresentationAdapter implements Adapter<Presentation> {

    constructor(private juryAdapter: JuryAdapter,
                private projectAdapter: ProjectAdapter) {}

    adapt(item: any): Presentation {
        let project: Project | undefined;
        if (item.project) {
            project = this.projectAdapter.adapt(item.project);
        } else {
            project = undefined;
        }

        return new Presentation(
            item.idPresentation,
            item.type,
            new Date(item.datetimeBegin),
            new Date(item.datetimeEnd),
            item.room,
            item.jury1Notes,
            item.jury2Notes,
            item.validationTeamNotes,
            item.teachingStaffNotes,
            this.juryAdapter.adapt(item.jury),
            project
        );
    }
}
