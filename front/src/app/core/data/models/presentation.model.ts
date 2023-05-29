import { Injectable } from "@angular/core";
import { Adapter } from "../adapter";


export class Presentation {
    constructor(
        public idPresentation: number,
        public type: string,
        public datetimeBegin: Date,
        public datetimeEnd: Date,
        public room: string,
        public jury1Notes: string,
        public jury2Notes: string,
        public idJury: number,
        public idProject: number,

    ) {}
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
    
    adapt(item: any): Presentation {
        return new Presentation(
            item.idPresentation,
            item.type,
            new Date(item.datetimeBegin),
            new Date(item.datetimeEnd),
            item.room,
            item.jury1Notes,
            item.jury2Notes,
            item.idJury,
            item.idProject,
        );
    }
}
