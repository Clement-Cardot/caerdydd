import { Injectable } from "@angular/core";
import { HttpClient, HttpErrorResponse } from "@angular/common/http";
import { Observable } from "rxjs/internal/Observable";
import { catchError } from "rxjs/internal/operators/catchError";
import { throwError } from "rxjs/internal/observable/throwError";
import { map } from "rxjs";
import { TeamMember, TeamMemberAdapter } from "../data/models/team-member.model";
import { User, UserAdapter } from "../data/models/user.model";

@Injectable({
    providedIn: "root"
})
export class ApiTeamMemberService {
    private baseUrl = "http://localhost:4200/api/teamMembers";

    constructor(
        private http: HttpClient, 
        private teamMemberAdapter: TeamMemberAdapter, 
        private userAdapter: UserAdapter
        ) {
    }
    
    getAllTeamMembers(): Observable<TeamMember[]> {	
        return this.http.get<any[]>(this.baseUrl)
        .pipe(
            map((data: any[]) => data.map((item) => this.teamMemberAdapter.adapt(item)))
        )
        .pipe(
            catchError(this.handleError)
        );
    }

    getTeamMember(teamMemberId: number): Observable<TeamMember> {
        const url = `${this.baseUrl}/${teamMemberId}`;
        return this.http.get<any>(url)
        .pipe(
            map((data: any) => this.teamMemberAdapter.adapt(data))
        )
        .pipe(
            catchError(this.handleError)
        );
    }

    setBonusTeamMember(teamMemberId: number, bonus: number): Observable<TeamMember> {
        const url = `${this.baseUrl}/bonus/${teamMemberId}/${bonus}`;	
        return this.http.post(url, null)
        .pipe(
            map((data: any) => this.teamMemberAdapter.adapt(data))
        )
        .pipe(
            catchError(this.handleError)
        );
    }

    private handleError(error: HttpErrorResponse) {
        if (error.status === 0) {
            // A client-side or network error occurred. Handle it accordingly.
            console.error('An error occurred:', error.error);
          } else {
            // The backend returned an unsuccessful response code.
            // The response body may contain clues as to what went wrong.
            console.error(
              `Backend returned code ${error.status}, body was: `, error.error);
          }
          // Return an observable with a user-facing error message.
          return throwError(() => new Error('Something bad happened; please try again later.'));
        }
}