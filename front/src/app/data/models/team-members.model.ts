import { User } from "./user.model";

export class TeamMembers {
    public idTeam!: number;
    public user!: User;
    public individualMark!: number;
    public bonusPenalty!: number;
}