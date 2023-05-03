import { HttpClient, HttpEvent, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Team } from '../data/models/team.model';

@Injectable({
  providedIn: 'root'
})
export class ApiUploadFileService {

  private baseUrl = "http://localhost:4200/api/teams";

  constructor(private _http: HttpClient) { }

  public upload(file: File, team: number, fileType: string): Observable<HttpEvent<any>> {
    const formData: FormData = new FormData();

    formData.append('file', file);
    formData.append('teamId', team.toString());
    formData.append('fileType', fileType);

    const req = new HttpRequest('POST', `${this.baseUrl}/upload`, formData, {
      reportProgress: true,
      responseType: 'json'
    });

    return this._http.request(req);
  }

  getFiles(): Observable<any> {
    return this._http.get(`${this.baseUrl}/files`);
  }
}
