import { Injectable } from '@angular/core';
import { UserLogIn } from '../models/user-log-in.model';
import { sha256 } from 'js-sha256';
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

  public signIn(userInfo: UserLogIn): Observable<boolean> {
    console.log(userInfo);
    userInfo.password = this.hashString(userInfo.password);
    console.log(userInfo.password);
    return this.getLoginValidation(userInfo);
  }

  private hashString(stringToHash: string): string {
    for (let i = 1; i < 3; i++) {
      stringToHash = sha256(stringToHash);
    }
    return stringToHash;
  }
}
