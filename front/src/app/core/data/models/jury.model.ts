import { User, UserAdapter } from "./user.model";
import { Injectable } from "@angular/core";
import { Adapter } from "../adapter";

export class Jury{
    constructor(
        public idJury: number,
        public idTs1: User,
        public idTs2: User
    ) {}
}

@Injectable({
    providedIn: 'root'
})
export class JuryAdapter implements Adapter<Jury>{

    constructor(private userAdapter: UserAdapter) { }

    adapt(item: any): Jury {
        return new Jury(
            item.idJury,
            this.userAdapter.adapt(item.idTs1),
            this.userAdapter.adapt(item.idTs2)
        );
    }
}