import { Injectable } from "@angular/core";
import { Adapter } from "../adapter";

export class Project {
    constructor(
        public idProject: number,
        public name: string,
        public description: string,
    ) {}
}


@Injectable({
    providedIn: 'root'
})
export class ProjectAdapter implements Adapter<Project> {
    adapt(item: any): Project {
        return new Project(
            item.idProject,
            item.name,
            item.description,
        );
    }
}
