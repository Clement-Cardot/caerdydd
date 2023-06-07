import { Injectable } from "@angular/core";
import { Adapter } from "../adapter";
import { Jury, JuryAdapter } from "./jury.model";

export class Project {
    constructor(
        public idProject: number,
        public name: string,
        public description: string,
        public isValidated: boolean,
        public jury: Jury | null, 
        public teamName?: string,
    ) {}
}

@Injectable({
    providedIn: 'root'
})
export class ProjectAdapter implements Adapter<Project>{

    constructor(private juryAdapter: JuryAdapter) { }

    adapt(item: any): Project {
        return new Project(
            item.idProject,
            item.name,
            item.description,
            item.isValidated,
            this.juryAdapter.adapt(item.jury)
        );
    }
}