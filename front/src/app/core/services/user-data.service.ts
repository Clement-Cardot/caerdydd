import { Injectable } from "@angular/core";
import { User } from "../data/models/user.model";
import { Team } from "../data/models/team.model";
import { TeamMember } from "../data/models/team-member.model";

@Injectable()
export class UserDataService {
    private currentUser!: User;
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

    public getTeamMembersOfTeam(idTeam: number): TeamMember[] {
        return this.teams.find(team => team.idTeam === idTeam)!.teamMembers;
    }
}