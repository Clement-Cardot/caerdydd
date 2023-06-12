import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { UserDataService } from '../services/user-data.service';
import { User } from '../data/models/user.model';

@Injectable({
  providedIn: 'root'
})
export class RoleGuard {

  currentUser: User | undefined = undefined;

  constructor(private router: Router, private userDataService: UserDataService) {
    this.userDataService.getCurrentUser().subscribe((user: User | undefined) => {
      this.currentUser = user;
    });
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    let roles = route.data['roles'] as Array<string>;
    for (const role of roles) {
      if (this.canActivateByRole(role)) {
        return true;
      }
    }
    this.router.navigateByUrl("/dashboard");
    return false;
  }

  canActivateByRole(role: string): boolean {
    if (this.currentUser == null || this.currentUser.getRoles() == null || this.currentUser.getRoles() == undefined || this.currentUser.getRoles().length == 0) {
      return false;
    }
    else if (this.currentUser.getRoles().includes(role)) {
      return true;
    }
    return false;
  }
}
