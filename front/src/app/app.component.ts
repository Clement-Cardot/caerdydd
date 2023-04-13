import { UserDataService } from './core/services/user-data.service';
import { ApiUserService } from './core/services/api-user.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, NavigationEnd, Router } from '@angular/router';
import { filter, map } from 'rxjs';
import { User } from './core/data/models/user.model';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  title = 'TAF';
  currentUser: User | null = null;

  constructor(private router: Router, private activatedRoute: ActivatedRoute, private userDataService: UserDataService, private apiUserService: ApiUserService) {
    router.events.pipe(
      filter((e): e is NavigationEnd => e instanceof NavigationEnd),
      map(e => {
        console.log(e);
      })
    );
  }

  ngOnInit(): void {
    this.userDataService.getCurrentUser().subscribe((user: User | null) => {
      this.currentUser = user;
    });
  }

  isLoggedIn(): boolean {
    return this.currentUser != null;
  }
}
