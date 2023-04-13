import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { UserDataService } from '../services/user-data.service';
import { Role } from '../data/models/role.model';

@Injectable({
  providedIn: 'root'
})
export class RoleGuard {

  constructor(private router: Router, private userDataService: UserDataService) {}

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
    let currentUserRoles = this.userDataService.getCurrentUser()?.getValue()?.roles.map((role: Role) => role.role);
    if (currentUserRoles == null || currentUserRoles == undefined || currentUserRoles.length == 0) {
      this.router.navigateByUrl("/");
    } else if (currentUserRoles.includes(role)) {
      return true;
    }
    return false;
  }
}
