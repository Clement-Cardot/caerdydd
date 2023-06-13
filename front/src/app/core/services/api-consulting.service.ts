import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs/internal/Observable';
import { catchError } from 'rxjs/internal/operators/catchError';
import { throwError } from 'rxjs/internal/observable/throwError';
import { map } from 'rxjs';
import { environment } from '../../../environments/environment';
import {
  PlannedTimingConsultingAdapter,
  PlannedTimingConsulting,
} from '../data/models/planned-timing-consulting.model';
import {
  PlannedTimingAvailability,
  PlannedTimingAvailabilityAdapter,
} from '../data/models/planned-timing-availability.model';
import { Consulting, ConsultingAdapter } from '../data/models/consulting.model';

@Injectable({
  providedIn: 'root',
})
export class ApiConsultingService {
  private baseUrl = environment.apiURL + '/consulting';

  constructor(
    private http: HttpClient,
    private plannedTimingConsultingAdapter: PlannedTimingConsultingAdapter,
    private plannedTimingAvailabilityAdapter: PlannedTimingAvailabilityAdapter,
    private consultingAdapter: ConsultingAdapter
  ) {}

  getAllPlannedTimingConsulting(): Observable<PlannedTimingConsulting[]> {
    const url = `${this.baseUrl}/plannedTiming`;
    return this.http
      .get<any>(url)
      .pipe(
        map((data: any[]) =>
          data.map((item) => this.plannedTimingConsultingAdapter.adapt(item))
        )
      )
      .pipe(catchError(this.handleError));
  }

  getConsultingForCurrentTeachingStaff(): Observable<Consulting[]> {
    const url = `${this.baseUrl}/TeachingStaff`;
    return this.http
      .get<any>(url)
      .pipe(
        map((data: any[]) =>
          data.map((item) => this.consultingAdapter.adapt(item))
        )
      )
      .pipe(catchError(this.handleError));
  }

  getAllWaitingAcceptationConsultings(): Observable<Consulting[]> {
    const url = `${this.baseUrl}/consultingsWaiting`;
    return this.http
      .get<any>(url)
      .pipe(
        map((data: any[]) =>
          data.map((item) => this.consultingAdapter.adapt(item))
        )
      )
      .pipe(catchError(this.handleError));
  }

  getConsultingForATeam(teamId: number): Observable<Consulting[]> {
    const url = `${this.baseUrl}/team/${teamId}`;
    return this.http
      .get<any>(url)
      .pipe(
        map((data: any[]) =>
          data.map((item) => this.consultingAdapter.adapt(item))
        )
      )
      .pipe(catchError(this.handleError));
  }

  getAllPlannedAvailabilities(): Observable<PlannedTimingAvailability[]> {
    const url = `${this.baseUrl}/plannedAvailability`;
    return this.http
      .get<any>(url)
      .pipe(
        map((data: any[]) =>
          data.map((item) => this.plannedTimingAvailabilityAdapter.adapt(item))
        )
      )
      .pipe(catchError(this.handleError));
  }

  getAllConsultings(): Observable<Consulting[]> {
    const url = `${this.baseUrl}/consultings`;
    return this.http
      .get<any>(url)
      .pipe(
        map((data: any[]) =>
          data.map((item) => this.consultingAdapter.adapt(item))
        )
      )
      .pipe(catchError(this.handleError));
  }

  getAllConsultingsInfra(): Observable<Consulting[]> {
    const url = `${this.baseUrl}/teachingStaffConsultingInfra`;
    return this.http
      .get<any>(url)
      .pipe(
        map((data: any[]) =>
          data.map((item) => this.consultingAdapter.adapt(item))
        )
      )
      .pipe(catchError(this.handleError));
  }

  getAllConsultingsDev(): Observable<Consulting[]> {
    const url = `${this.baseUrl}/teachingStaffConsultingDevelopment`;
    return this.http
      .get<any>(url)
      .pipe(
        map((data: any[]) =>
          data.map((item) => this.consultingAdapter.adapt(item))
        )
      )
      .pipe(catchError(this.handleError));
  }

  getAllConsultingsModeling(): Observable<Consulting[]> {
    const url = `${this.baseUrl}/teachingStaffConsultingModeling`;
    return this.http
      .get<any>(url)
      .pipe(
        map((data: any[]) =>
          data.map((item) => this.consultingAdapter.adapt(item))
        )
      )
      .pipe(catchError(this.handleError));
  }

  upload(file: File): Observable<PlannedTimingConsulting[]> {
    const formData: FormData = new FormData();
    formData.append('file', file);

    const url = `${this.baseUrl}/plannedTiming`;
    return this.http
      .put<any>(url, formData)
      .pipe(
        map((data: any[]) =>
          data.map((item) => this.plannedTimingConsultingAdapter.adapt(item))
        )
      )
      .pipe(catchError(this.handleError));
  }

  updateAvailability(
    consulting: PlannedTimingAvailability
  ): Observable<PlannedTimingAvailability> {
    const url = `${this.baseUrl}/availability`;
    return this.http
      .post<PlannedTimingAvailability>(url, consulting)
      .pipe(
        map((data: any) => this.plannedTimingAvailabilityAdapter.adapt(data))
      )
      .pipe(catchError(this.handleError));
  }

  updateConsulting(consulting: Consulting): Observable<Consulting> {
    const url = `${this.baseUrl}/consulting`;
    return this.http
      .post<Consulting>(url, consulting)
      .pipe(map((data: any) => this.consultingAdapter.adapt(data)))
      .pipe(catchError(this.handleError));
  }

  createConsulting(consulting: Consulting): Observable<Consulting> {
    const url = `${this.baseUrl}/createConsulting`;
    return this.http
      .put<Consulting>(url, consulting)
      .pipe(map((data: any) => this.consultingAdapter.adapt(data)))
      .pipe(catchError(this.handleError));
  }

  private handleError(error: HttpErrorResponse) {
    if (error.status === 0) {
      // A client-side or network error occurred. Handle it accordingly.
      console.error('An error occurred:', error.error);
    } else {
      // The backend returned an unsuccessful response code.
      // The response body may contain clues as to what went wrong.
      console.error(
        `Backend returned code ${error.status}, body was: `,
        error.error
      );
    }
    // Return an observable with a user-facing error message.
    return throwError(() => error);
  }
}
