import { Injectable } from '@angular/core';
import { UserLogIn } from '../models/user-log-in.model';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LogInService {

  constructor(private _http: HttpClient) { }

  public getLoginValidation(userInfo: UserLogIn) {
    return this._http.get<boolean>('http://localhost:4200/api/login?username=' + userInfo.username + '&password=' + userInfo.password);
  }
}
