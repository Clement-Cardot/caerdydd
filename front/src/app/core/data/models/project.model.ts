import { Injectable } from "@angular/core";
import { Adapter } from "../adapter";
import { Jury } from "./jury.model";

export class Project {
    constructor(
        public idProject: number,
        public name: string,
        public description: string,
        public isValidated: boolean,
        public jury: Jury,
        public teamName?: string,
    ) {}
}

@Injectable({
    providedIn: 'root'
})
export class ProjectAdapter implements Adapter<Project>{
    adapt(item: any): Project {
        return new Project(
            item.idProject,
            item.name,
            item.description,
            item.isValidated,
            item.jury
        );
    }
}