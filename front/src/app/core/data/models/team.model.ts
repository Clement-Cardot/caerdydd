import { Injectable } from "@angular/core";
import { TeamMember, TeamMemberAdapter } from "./team-member.model";
import { Adapter } from "../adapter";


export class Team {
    constructor(
        public idTeam: number,
        public name: string,
        public teamWorkMark: number,
        public teamValidationMark: number,
        public testBookLink: string,
        public filePathScopeStatement: string,
        public filePathFinalScopeStatement: string,
        public filePathScopeStatementAnalysis: string,
        public filePathReport: string,
        public idProjectDev: number,
        public idProjectValidation: number,

        public teamMembers: TeamMember[]
    ) {}
}

@Injectable({
    providedIn: 'root'
})
export class TeamAdapter implements Adapter<Team> {

    constructor(private teamMemberAdapter: TeamMemberAdapter) { }
    
        adapt(item: any): Team {
            let teamMemberList: TeamMember[] = [];
            item.teamMembers.forEach((teamMember: any) => {
                teamMemberList.push(this.teamMemberAdapter.adapt(teamMember));
            });
            
            return new Team(
                item.idTeam,
                item.name,
                item.teamWorkMark,
                item.teamValidationMark,
                item.testBookLink,
                item.filePathScopeStatement,
                item.filePathFinalScopeStatement,
                item.filePathScopeStatementAnalysis,
                item.filePathReport,
                item.idProjectDev,
                item.idProjectValidation,
                teamMemberList
            );
        }
    }