import { UserDataService } from './core/services/user-data.service';
import { ApiUserService } from './core/services/api-user.service';
import { Component, OnInit } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';
import { filter, map } from 'rxjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  title = 'TAF';
  login = false;

  constructor(private router: Router, private userDataService: UserDataService, private apiUserService: ApiUserService) {
    router.events.pipe(
      filter((e): e is NavigationEnd => e instanceof NavigationEnd),
      map(e => {
        console.log(e);
      })
    );
  }

  ngOnInit() {

    if(this.router.url === "/") {
      this.login = true;
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
