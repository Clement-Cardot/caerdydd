import { Injectable } from "@angular/core";
import { HttpClient, HttpErrorResponse } from "@angular/common/http";
import { Observable } from "rxjs/internal/Observable";
import { catchError } from "rxjs/internal/operators/catchError";
import { throwError } from "rxjs/internal/observable/throwError";
import { map } from "rxjs";
import { Team, TeamAdapter } from "../data/models/team.model";
import { User, UserAdapter } from "../data/models/user.model";
import { environment } from "../../../environments/environment";

@Injectable({
    providedIn: "root"
})
export class ApiTeamService {
    private baseUrl = environment.apiURL + "/teams";

    constructor(
        private http: HttpClient, 
        private teamAdapter: TeamAdapter, 
        private userAdapter: UserAdapter
        ) {
    }
    
    getAllTeams(): Observable<Team[]> {	
        return this.http.get<any[]>(this.baseUrl)
        .pipe(
            map((data: any[]) => data.map((item) => this.teamAdapter.adapt(item)))
        )
        .pipe(
            catchError(this.handleError)
        );
    }

    getTeam(teamId: number): Observable<Team> {
        const url = `${this.baseUrl}/${teamId}`;
        return this.http.get<any>(url)
        .pipe(
            map((data: any) => this.teamAdapter.adapt(data))
        )
        .pipe(
            catchError(this.handleError)
        );
    }

    createTeams(nbTeamsPairs:number): Observable<Team[]> {
        const url = `${this.baseUrl}/createTeams`;
        return this.http.put<any[]>(url, nbTeamsPairs)
        .pipe(
            map((data: any[]) => data.map((item) => this.teamAdapter.adapt(item)))
        )
        .pipe(
            catchError(this.handleError)
        );
    }

    applyForTeam(teamId: number, userId: number): Observable<User> {
        const url = `${this.baseUrl}/${teamId}/${userId}`;	
        return this.http.put(url, {})
        .pipe(
            map((data: any) => this.userAdapter.adapt(data))
        )
        .pipe(
            catchError(this.handleError)
        );
    }

    setTeamWorkMarkTeam(teamId: number, teamWorkmark: number): Observable<Team> {
        const url = `${this.baseUrl}/teamWorkMark`;
        const formData: FormData = new FormData();
        formData.append('teamId', teamId.toString());
        formData.append('teamWorkMark', teamWorkmark.toString());
        return this.http.post(url, formData)
        .pipe(
            map((data: any) => this.teamAdapter.adapt(data))
        )
        .pipe(
            catchError(this.handleError)
        );
    }

    setTeamValidationMarkTeam(teamId: number, teamValidationMark: number): Observable<Team> {
        const url = `${this.baseUrl}/teamValidationMark`;
        const formData: FormData = new FormData();
        formData.append('teamId', teamId.toString());
        formData.append('teamValidationMark', teamValidationMark.toString());
        return this.http.post(url, formData)
        .pipe(
            map((data: any) => this.teamAdapter.adapt(data))
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