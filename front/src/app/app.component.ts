import { UserDataService } from './core/services/user-data.service';
import { ApiUserService } from './core/services/api-user.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, NavigationEnd, Router } from '@angular/router';
import { filter, map } from 'rxjs';
import { CoreServiceService } from './core/services/core-service.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  title = 'TAF';

  constructor(private router: Router, private coreService: CoreServiceService, private activatedRoute: ActivatedRoute, private userDataService: UserDataService, private apiUserService: ApiUserService) {
    router.events.pipe(
      filter((e): e is NavigationEnd => e instanceof NavigationEnd),
      map(e => {
        console.log(e);
      })
    );
  }

  ngOnInit(): void {}

  public getLoginStatut(): boolean {
    return this.coreService.getLogin();
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
