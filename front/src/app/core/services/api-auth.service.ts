import { Injectable } from "@angular/core";
import { HttpClient, HttpErrorResponse } from "@angular/common/http";
import { User, UserAdapter } from "../data/models/user.model";
import { Observable } from "rxjs/internal/Observable";
import { catchError } from "rxjs/internal/operators/catchError";
import { throwError } from "rxjs/internal/observable/throwError";
import { map } from "rxjs";

@Injectable({
    providedIn: "root"
})
export class ApiAuthService {
    private baseUrl = "http://localhost:4200/api/auth";

    constructor(private http: HttpClient, private adapter: UserAdapter) {
    }

    tryToLogIn(login: string, password: string): Observable<User> {
        const url = `${this.baseUrl}/login`;
        return this.http.post(url, { login, password })
        .pipe(
            map((data: any) => this.adapter.adapt(data))
        )
        .pipe(
            catchError(this.handleError)
        );
    }

    logout(): Observable<any> {
        const url = `${this.baseUrl}/logout`;
        return this.http.post(url, {})
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
