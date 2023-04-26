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
export class ApiUserService {
    private baseUrl = environment.apiURL + "/users";

    constructor(private http: HttpClient, private userAdapter: UserAdapter) {
    }
    
    getUserById(id: number): Observable<User> {
        const url = `${this.baseUrl}/${id}`;	
        return this.http.get(url)
        .pipe(
            map((data: any) => this.userAdapter.adapt(data))
        )
        .pipe(
            catchError(this.handleError)
        );
    }

    uploadStudents(file: File): Observable<User[]> {
        const formData: FormData = new FormData();
        formData.append('file', file);

        const url = `${this.baseUrl}/upload/students`;
        return this.http.post<any>(url, formData)
        .pipe(
            map((data: any[]) => data.map((item) => this.userAdapter.adapt(item)))
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
        return throwError(() => new Error('Something bad happened; please try again later.', ));
    }
}