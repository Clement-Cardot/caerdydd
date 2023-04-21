export class Project {
    team!: string;
    subject!: string;
    description!: string;
    is_validated!: boolean;
}

export class ProjectDev {

}

export class ProjectAdapter implements Adapter<Project> {

    adapt(item: any): Project {
        return new Project();
    }
}