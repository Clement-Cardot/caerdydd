import { Injectable } from "@angular/core";
import { TeamMember, TeamMemberAdapter } from "./team-member.model";
import { Adapter } from "../adapter";
import { Project, ProjectAdapter } from "./project.model";


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
        public isReportAnnotation: boolean,
        public reportComments: string,

        public teamMembers: TeamMember[],
        public projectDev: Project,
        public projectValidation: Project
    ) {}
}

@Injectable({
    providedIn: 'root'
})
export class TeamAdapter implements Adapter<Team> {

    constructor(private teamMemberAdapter: TeamMemberAdapter, private projectAdapter: ProjectAdapter) { }

        adapt(item: any): Team {
            let teamMemberList: TeamMember[] = [];
            if (item.teamMembers != null){
                item.teamMembers.forEach((teamMember: any) => {
                    teamMemberList.push(this.teamMemberAdapter.adapt(teamMember));
                });
            }
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
                item.isReportAnnotation,
                item.reportComments,
                teamMemberList,
                this.projectAdapter.adapt(item.projectDev),
                this.projectAdapter.adapt(item.projectValidation)
            );
        }
    }
