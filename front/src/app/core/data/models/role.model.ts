import { Injectable } from "@angular/core";
import { Adapter } from "../adapter";

export class Role {
    constructor(
        public idRole: number,
        public role: string
    ) {}
}

@Injectable({
    providedIn: 'root'
})
export class RoleAdapter implements Adapter<Role>{
    adapt(item: any): Role {
        return new Role(
            item.idRole,
            item.role
        );
    }
}