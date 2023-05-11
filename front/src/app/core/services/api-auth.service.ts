import { Injectable } from "@angular/core";
import { HttpClient, HttpErrorResponse } from "@angular/common/http";
import { User, UserAdapter } from "../data/models/user.model";
import { Observable } from "rxjs/internal/Observable";
import { catchError } from "rxjs/internal/operators/catchError";
import { throwError } from "rxjs/internal/observable/throwError";
import { map } from "rxjs";
import { environment } from "../../../environments/environment";

@Injectable({
    providedIn: "root"
})
export class ApiAuthService {
    private baseUrl = environment.apiURL + "/auth";

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
        return throwError(() => error.status);
    }
}