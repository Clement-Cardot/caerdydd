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

    constructor(private juryAdapter: JuryAdapter) { }

    adapt(item: any): Project {
        let jury: Jury | null = null; 
        if (item.jury) {
            jury = this.juryAdapter.adapt(item.jury);
        }
        return new Project(
            item.idProject,
            item.name,
            item.description,
            item.isValidated,
            item.jury
        );
    }
}