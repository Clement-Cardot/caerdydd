import { Injectable } from "@angular/core";
import { Role, RoleAdapter } from "./role.model";
import { Adapter } from "../adapter";

export class User {
    constructor(
        public id: number,
        public firstname: string,
        public lastname: string,
        public login: string,
        public password: string,
        public email: string,
        public roles: Role[]
    ) { }
}

@Injectable({
    providedIn: 'root'
})
export class UserAdapter implements Adapter<User> {

    constructor(private roleAdapter: RoleAdapter) { }

    adapt(item: any): User {
        let roleList: Role[] = [];
        item.roles.forEach((role: any) => {
            roleList.push(this.roleAdapter.adapt(role));
        });
        
        return new User(
            item.id,
            item.firstname,
            item.lastname,
            item.login,
            item.password,
            item.email,
            roleList
        );
    }
}