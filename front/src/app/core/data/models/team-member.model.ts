import { User, UserAdapter } from "./user.model";
import { Injectable } from "@angular/core";
import { Adapter } from "../adapter";

export class TeamMember{
    constructor(
        public user: User,
        public idTeam: number,
        public individualMark: number,
        public bonusPenalty: number
    ) {}
}

@Injectable({
    providedIn: 'root'
})
export class TeamMemberAdapter implements Adapter<TeamMember>{

    constructor(private userAdapter: UserAdapter) { }

    adapt(item: any): TeamMember {
        return new TeamMember(
            this.userAdapter.adapt(item.user),
            item.idTeam,
            item.individualMark,
            item.bonusPenalty
        );
    }
}