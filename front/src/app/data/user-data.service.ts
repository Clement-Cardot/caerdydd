import { Injectable } from "@angular/core";
import { User } from "./models/user.model";
import { Team } from "./models/team.model";

@Injectable()
export class UserDataService {
    private currentUser: User = new User(1, "jean", "dupont", "jdupont", "mdp", "jdupont@reseau.eseo.fr", "LD", ["STUDENT_ROLE"]);
    private users!: User[];
    private teams!: Team[];

    // Getters and setters
    public getCurrentUser(): User {
        return this.currentUser;
    }

    public setCurrentUser(user: User): void {
        this.currentUser = user;
    }

    public getUsers(): User[] {
        return this.users;
    }

    public setUsers(users: User[]): void {
        this.users = users;
    }

    public getTeams(): Team[] {
        return this.teams;
    }

    public setTeams(teams: Team[]): void {
        this.teams = teams;
    }
}