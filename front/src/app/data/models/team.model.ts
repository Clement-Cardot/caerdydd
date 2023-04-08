import { TeamMembers } from "./team-members.model";

export class Team {
    public idTeam!: number;
    public name!: string;
    public teamWorkMark!: number;
    public teamValidationMark!: number;
    public testBookLink!: string;
    public filePathScopeStatement!: string;
    public filePathFinalScopeStatement!: string;
    public filePathScopeStatementAnalysis!: string;
    public filePathReport!: string;
    public idProjectDev!: number;
    public idProjectValidation!: number;

    public teamMembers!: TeamMembers[];

}