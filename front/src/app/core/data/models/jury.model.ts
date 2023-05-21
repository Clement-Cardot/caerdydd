import { User, UserAdapter } from "./user.model";
import { Injectable } from "@angular/core";
import { Adapter } from "../adapter";
import { TeachingStaff, TeachingStaffAdapter } from "./teaching-staff.model";

export class Jury{
    constructor(
        public idJury: number,
        public ts1: TeachingStaff,
        public ts2: TeachingStaff
    ) {}
}

@Injectable({
    providedIn: 'root'
})
export class JuryAdapter implements Adapter<Jury>{

    constructor(private teachingStaffAdapter: TeachingStaffAdapter) { }

    adapt(item: any): Jury {
        return new Jury(
            item.idJury,
            this.teachingStaffAdapter.adapt(item.ts1),
            this.teachingStaffAdapter.adapt(item.ts2)
        );
    }
}