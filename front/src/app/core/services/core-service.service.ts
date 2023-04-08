import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class CoreServiceService {

  private login!: boolean;

  constructor() {
    this.setLoginFalse();
  }

  public setLoginTrue() {
    this.login = true;
  }

  public setLoginFalse() {
    this.login = false;
  }

  public getLogin(): boolean {
    return this.login
  }
}
