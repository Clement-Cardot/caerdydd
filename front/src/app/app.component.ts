import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'TAF';
  login = true;

  constructor(private router: Router) {}

  ngOnInit() {
    if(this.router.url != "/") {
      this.login = false;
    }
  }
}
