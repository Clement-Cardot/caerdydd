import { Injectable } from "@angular/core";
import { HttpClient, HttpErrorResponse } from "@angular/common/http";
import { Observable } from "rxjs/internal/Observable";
import { catchError } from "rxjs/internal/operators/catchError";
import { throwError } from "rxjs/internal/observable/throwError";
import { map } from "rxjs";
import { environment } from "../../../environments/environment";
import { Consulting, ConsultingAdapter } from "../data/models/consulting.model";

@Injectable({
    providedIn: "root"
})
export class ApiConsultingService {
    private baseUrl = environment.apiURL + "/consulting";

    constructor(
        private http: HttpClient, 
        private consultingAdapter: ConsultingAdapter
        ) {
    }

    getAllConsultings(): Observable<Consulting[]> {
        const url = this.baseUrl;
        return this.http.get<any>(url)
        .pipe(
            map((data: any[]) => data.map((item) => this.consultingAdapter.adapt(item)))
        )
        .pipe(
            catchError(this.handleError)
        );
    }
    
    upload(file: File): Observable<Consulting[]> {
        const formData: FormData = new FormData();
        formData.append('file', file);

        const url = `${this.baseUrl}/upload`;
        return this.http.post<any>(url, formData)
        .pipe(
            map((data: any[]) => data.map((item) => this.consultingAdapter.adapt(item)))
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