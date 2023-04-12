import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { UserDataService } from './core/services/user-data.service';
import { ApiUserService } from './core/services/api-user.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'TAF';
  login = true;

  constructor(
    private router: Router,
    private userDataService: UserDataService,
    private apiUserService: ApiUserService
    ) {}

  ngOnInit() {
    if(this.router.url != "/") {
      this.login = false;
    }
    this.refreshCurrentUser();
  }

  refreshCurrentUser() {
    console.log("refreshCurrentUser");
    if(this.userDataService.getCurrentUser().id != null) {
      this.apiUserService.getUserById(this.userDataService.getCurrentUser().id)
              .subscribe( user => 
                this.userDataService.setCurrentUser(user),
              );
    }
  }
}
