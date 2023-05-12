import { User, UserAdapter } from "./user.model";
import { Injectable } from "@angular/core";
import { Adapter } from "../adapter";

export class Jury{
    constructor(
        public idJury: number,
        public ts1: User,
        public ts2: User
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
            this.userAdapter.adapt(item.ts1),
            this.userAdapter.adapt(item.ts2)
        );
    }
}