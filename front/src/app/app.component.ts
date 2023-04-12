import { UserDataService } from './core/services/user-data.service';
import { ApiUserService } from './core/services/api-user.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, NavigationEnd, Router } from '@angular/router';
import { filter, map } from 'rxjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  title = 'TAF';

  constructor(private router: Router, private activatedRoute: ActivatedRoute, private userDataService: UserDataService, private apiUserService: ApiUserService) {
    router.events.pipe(
      filter((e): e is NavigationEnd => e instanceof NavigationEnd),
      map(e => {
        console.log(e);
      })
    );
  }

  ngOnInit(): void {}

  public getLoginStatut(): boolean {
    return !this.userDataService.isLoggedIn();
  }

  refreshCurrentUser() {
    console.log("refreshCurrentUser");
    let currentUser = this.userDataService.getCurrentUser();
    if(currentUser != null && currentUser.id != null) {
      this.apiUserService.getUserById(currentUser.id)
              .subscribe( user => 
                this.userDataService.setCurrentUser(user),
              );
    }
  }
}
