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

  constructor(private router: Router,
    private activatedRoute: ActivatedRoute,
    private coreService: CoreServiceService) {}

  ngOnInit(): void {
    if(this.router.url === "/") {
      this.coreService.setLoginTrue();
    }
    console.log(this.router.url);
    this.isOnLoginPage();
    console.log(this.coreService.getLogin());
  }

  public getLoginStatut(): boolean {
    return this.coreService.getLogin();
  }

  private isOnLoginPage(): void {
    
    // console.log(this.activatedRoute.snapshot.paramMap.get('url'));

  }
}
