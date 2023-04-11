import { Injectable } from "@angular/core";
import { HttpClient, HttpErrorResponse, HttpHeaders } from "@angular/common/http";
import { Observable } from "rxjs/internal/Observable";
import { catchError } from "rxjs/internal/operators/catchError";
import { throwError } from "rxjs/internal/observable/throwError";
import { map } from "rxjs";
import { Team, TeamAdapter } from "../data/models/team.model";

@Injectable({
    providedIn: "root"
})
export class ApiTeamService {
    private baseUrl = "http://localhost:4200/api/teams";

    constructor(private http: HttpClient, private adapter: TeamAdapter) {
    }
    
    getAllTeams(): Observable<Team[]> {	
        return this.http.get<any[]>(this.baseUrl)
        .pipe(
            map((data: any[]) => data.map((item) => this.adapter.adapt(item)))
        )
        .pipe(
            catchError(this.handleError)
        );
    }

    applyForTeam(teamId: number, userId: number): Observable<any> {
        const url = `${this.baseUrl}/${teamId}/${userId}`;	
        return this.http.put(url, {})
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