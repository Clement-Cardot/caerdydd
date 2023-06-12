import { Injectable } from "@angular/core";
import { HttpClient, HttpErrorResponse } from "@angular/common/http";
import { Observable } from "rxjs/internal/Observable";
import { catchError } from "rxjs/internal/operators/catchError";
import { throwError } from "rxjs/internal/observable/throwError";
import { Cons, map } from "rxjs";
import { environment } from "../../../environments/environment";
import { PlannedTimingConsultingAdapter, PlannedTimingConsulting } from "../data/models/planned-timing-consulting.model";
import { PlannedTimingAvailability, PlannedTimingAvailabilityAdapter } from "../data/models/planned-timing-availability.model";
import { Consulting, ConsultingAdapter } from "../data/models/consulting.model";

@Injectable({
    providedIn: "root"
})
export class ApiConsultingService {
    private baseUrl = environment.apiURL + "/consulting";

    constructor(
        private http: HttpClient, 
        private plannedTimingConsultingAdapter: PlannedTimingConsultingAdapter,
        private plannedTimingAvailabilityAdapter: PlannedTimingAvailabilityAdapter,
        private consultingAdapter: ConsultingAdapter
        ) {
    }

    getAllPlannedTimingConsulting(): Observable<PlannedTimingConsulting[]> {
        const url = `${this.baseUrl}/plannedTiming`;
        return this.http.get<any>(url)
        .pipe(
            map((data: any[]) => data.map((item) => this.plannedTimingConsultingAdapter.adapt(item)))
        )
        .pipe(
            catchError(this.handleError)
        );
    }
    
    upload(file: File): Observable<PlannedTimingConsulting[]> {
        const formData: FormData = new FormData();
        formData.append('file', file);

        const url = `${this.baseUrl}/plannedTiming`;
        return this.http.put<any>(url, formData)
        .pipe(
            map((data: any[]) => data.map((item) => this.plannedTimingConsultingAdapter.adapt(item)))
        )
        .pipe(
            catchError(this.handleError)
        );
    }

    updateAvailability(consulting: PlannedTimingAvailability): Observable<PlannedTimingAvailability> {
        const url = `${this.baseUrl}/availability`;
        return this.http.post<PlannedTimingAvailability>(url, consulting)
        .pipe(
            map((data: any) => this.plannedTimingAvailabilityAdapter.adapt(data))
        )
        .pipe(
            catchError(this.handleError)
        );
    }

    createConsulting(consulting: Consulting): Observable<Consulting> {
        const url = `${this.baseUrl}/createConsulting`;
        return this.http.post<Consulting>(url, consulting)
        .pipe(
            map((data: any) => this.consultingAdapter.adapt(data))
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
          return throwError(() => error);
        }
}