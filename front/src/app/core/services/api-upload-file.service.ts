import { HttpClient, HttpErrorResponse, HttpEvent, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';
import { Team } from '../data/models/team.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ApiUploadFileService {

  private baseUrl = environment.apiURL + "/teams";

  constructor(private _http: HttpClient) { }

  public upload(file: File, team: number, fileType: string) {
    const formData: FormData = new FormData();

    formData.append('file', file);
    formData.append('teamId', team.toString());
    formData.append('fileType', fileType);

    // const req = new HttpRequest('POST', `${this.baseUrl}/upload`, formData, {
    //   reportProgress: true,
    //   responseType: 'json'
    // });

    return this._http.post(`${this.baseUrl}/upload`, formData )
        .pipe(
            catchError(this.handleError)
        );

    // return this._http.request(req);
  }

  getFiles(): Observable<any> {
    return this._http.get(`${this.baseUrl}/files`);
  }

  private handleError(error: HttpErrorResponse) {
    if (error.status === 0) {
      // A client-side or network error occurred. Handle it accordingly.
      console.error('Une erreur est survenue, veuillez réessayer plus tard:', error.error);
    } else if (error.status === 415) {
      console.error("Le fichier n'est pas un pdf :", error.error);
    } else {
      console.error(
        `Le Backend à retourné un code erreur: ${error.status}, le corp était: `, error.error);
      }
    // Return an observable with a user-facing error message.
    return throwError(() => error);
}
}
