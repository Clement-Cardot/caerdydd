import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs/internal/Observable';
import { catchError } from 'rxjs/internal/operators/catchError';
import { throwError } from 'rxjs/internal/observable/throwError';
import { map } from 'rxjs';
import { environment } from '../../../environments/environment';
import { HttpHeaders } from '@angular/common/http';
import {
  TeachingStaff,
  TeachingStaffAdapter,
} from '../data/models/teaching-staff.model';

@Injectable({
  providedIn: 'root',
})
export class ApiTeachingStaffService {
  private baseUrl = environment.apiURL + '/teachingStaffs';

  constructor(
    private http: HttpClient,
    private teachingStaffAdapter: TeachingStaffAdapter
  ) {}

  setSpeciality(teachingStaff: TeachingStaff): Observable<TeachingStaff> {
    const url = `${this.baseUrl}/modifySpeciality`;
    return this.http
      .post(url, teachingStaff)
      .pipe(map((data: any) => this.teachingStaffAdapter.adapt(data)))
      .pipe(catchError(this.handleError));
  }

  getTeachingStaff(teachingStaffId: number): Observable<TeachingStaff> {
    const url = `${this.baseUrl}/modifySpeciality`;
    return this.http
      .put(url, teachingStaffId)
      .pipe(map((data: any) => this.teachingStaffAdapter.adapt(data)))
      .pipe(catchError(this.handleError));
  }

  getAllTeachingStaff(): Observable<TeachingStaff[]> {
    return this.http
      .get<any[]>(this.baseUrl)
      .pipe(
        map((data: any[]) =>
          data.map((item) => this.teachingStaffAdapter.adapt(item))
        )
      )
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
    return throwError(
      () => new Error('Something bad happened; please try again later.')
    );
  }
}
