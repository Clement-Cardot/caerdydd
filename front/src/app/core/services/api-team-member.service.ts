import { Injectable } from "@angular/core";
import { HttpClient, HttpErrorResponse } from "@angular/common/http";
import { Observable } from "rxjs/internal/Observable";
import { catchError } from "rxjs/internal/operators/catchError";
import { throwError } from "rxjs/internal/observable/throwError";
import { map } from "rxjs";
import { TeamMember, TeamMemberAdapter } from "../data/models/team-member.model";

@Injectable({
    providedIn: "root"
})
export class ApiTeamMemberService {
    private baseUrl = "http://localhost:4200/api/team-members";

    constructor(
        private http: HttpClient,
        private teamMemberAdapter: TeamMemberAdapter
    ) {}

    getTeamMemberById(teamMemberId: number): Observable<TeamMember> {
        const url = `${this.baseUrl}/${teamMemberId}`;
        return this.http.get<any>(url)
        .pipe(
            map((data: any) => this.teamMemberAdapter.adapt(data))
        )
        .pipe(
            catchError(this.handleError)
        );
    }

    private handleError(error: HttpErrorResponse) {
        if (error.status === 0) {
            console.error('An error occurred:', error.error);
        } else {
            console.error(
              `Backend returned code ${error.status}, body was: `, error.error);
        }
        return throwError(() => new Error('Something bad happened; please try again later.'));
    }
}
