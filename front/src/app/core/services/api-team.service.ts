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
  providedIn: 'root',
})
export class ApiTeamService {
  private baseUrl = environment.apiURL + '/teams';

  constructor(
    private http: HttpClient,
    private teamAdapter: TeamAdapter,
    private userAdapter: UserAdapter
  ) {}

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

  addTestBookLink(team: Team): Observable<Team> {
    const url = `${this.baseUrl}/testBookLink`;
    return this.http.put<Team>(url, team)
    .pipe(
      map((data: any) => this.teamAdapter.adapt(data))
    )
    .pipe(
      catchError(this.handleError)
    );
  }

  getTestBookLinkDev(teamId: number): Observable<string> {
    const url = `${this.baseUrl}/${teamId}/testBookLinkDev`;
    return this.http.get(url, { responseType: 'text' })
      .pipe(
        catchError(this.handleError)
      );
  }

  getTestBookLinkValidation(teamId: number): Observable<string> {
    const url = `${this.baseUrl}/${teamId}/testBookLinkValidation`;
    return this.http.get(url, { responseType: 'text' })
      .pipe(
        catchError(this.handleError)
      );
  }

  setTeamMarks(teamId: number, teamWorkmark: number, teamValidationMark: number): Observable<Team> {
    const url = `${this.baseUrl}/teamMarks`;
    const formData: FormData = new FormData();
    formData.append('teamId', teamId.toString());
    formData.append('teamWorkMark', teamWorkmark.toString());
    formData.append('teamValidationMark', teamValidationMark.toString());

    return this.http.post(url, formData)
      .pipe(
        map((data: any) => this.teamAdapter.adapt(data)),
        catchError(this.handleError)
      );
  }

  setCommentOnReport(idTeam: number, comment: string) {
      const formData: FormData = new FormData();

      formData.append('idTeam', idTeam.toString());
      formData.append('comment', comment);

      return this.http.put(`${this.baseUrl}/setCommentOnReport`, formData )
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
      console.error(`Backend returned code ${error.status}, body was: `, error.error);
    }
    // Return an observable with a user-facing error message.
    return throwError(() => new Error('Something bad happened; please try again later.'));
  }
}
