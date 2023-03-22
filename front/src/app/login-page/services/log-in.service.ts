import { Injectable } from '@angular/core';
import { UserLogIn } from '../models/user-log-in.model';

@Injectable({
  providedIn: 'root'
})
export class LogInService {

  constructor() { }

  public signIn(userInfo: UserLogIn) {
    localStorage.setItem('ACCESS_TOKEN', "access_token");
  }
}
