import { Injectable } from '@angular/core';
import { UserLogIn } from '../models/user-log-in.model';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, catchError, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LogInService {


  constructor(private _http: HttpClient) { }

  public getLoginValidation(userInfo: UserLogIn): Observable<boolean> {
    // const body = { login: userInfo.login, password: userInfo.password };
    return this._http.post<boolean>('http://localhost:4200/api/auth/login', userInfo)
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
