import {MediaMatcher} from '@angular/cdk/layout';
import {ChangeDetectorRef, Component, OnChanges, OnDestroy, OnInit} from '@angular/core';
import { Router } from '@angular/router';
import { Role } from 'src/app/core/data/models/role.model';
import { User } from 'src/app/core/data/models/user.model';
import { UserDataService } from 'src/app/core/services/user-data.service';

@Component({
  selector: 'app-sidenav',
  templateUrl: './sidenav.component.html',
  styleUrls: ['./sidenav.component.scss']
})
export class SidenavComponent implements OnInit, OnChanges {
mobileQuery: MediaQueryList;
  pageName!: String;
  currentUserRoles!: string[];
  fillerNav = Array.from({length: 5}, (_, i) => `Nav Item ${i + 1}`);

  navLink = new Array<string>;

  private _mobileQueryListener: () => void;

  constructor(
        changeDetectorRef: ChangeDetectorRef, 
        media: MediaMatcher, 
        private router: Router, 
        private userDataService: UserDataService
    ) {
    this.mobileQuery = media.matchMedia('(max-width: 600px)');
    this._mobileQueryListener = () => changeDetectorRef.detectChanges();
    this.mobileQuery.addListener(this._mobileQueryListener);
  }

  ngOnInit(): void {
    this.pageName = "Accueil";
    this.userDataService.getCurrentUserRoles()?.subscribe(data => {
      this.currentUserRoles = data;
    });
    this.update();
  }

  ngOnDestroy(): void {
    this.mobileQuery.removeListener(this._mobileQueryListener);
  }

  ngOnChanges(): void {
    console.log("Init navbar On Change")
    this.update();
  }

  update() {
    this.navLink = new Array<string>;

    if (this.currentUserRoles == null) {
      this.router.navigateByUrl("/");
    }
    else if (this.currentUserRoles.includes("OPTION_LEADER_ROLE")) {
      this.navLink.push("Dashboard");
      this.navLink.push("Admin Panel")
      this.navLink.push("Profil");
      this.navLink.push("Equipes");
      this.navLink.push("Notifications");
    }
    else if (this.currentUserRoles.includes("TEACHING_STAFF_ROLE")) {
      this.navLink.push("Dashboard");
      this.navLink.push("Profil");
      this.navLink.push("Equipes");
      this.navLink.push("Notifications");
    }
    else if (this.currentUserRoles.includes("PLANNING_ROLE")) {
      this.navLink.push("Dashboard");
      this.navLink.push("Profil");
      this.navLink.push("Planification");
      this.navLink.push("Notifications");
    }
    else if (this.currentUserRoles.includes("TEAM_MEMBER_ROLE")) {
      this.navLink.push("Dashboard");
      this.navLink.push("Profil");
      this.navLink.push("Mon équipe");
      this.navLink.push("Projet DEV");
      this.navLink.push("Projet Validation");
      this.navLink.push("Notifications");
    }
    else if (this.currentUserRoles.includes("STUDENT_ROLE")) {
      this.navLink.push("Dashboard");
      this.navLink.push("Profil");
      this.navLink.push("Equipes");
    }
  }

  switchPage(pageName: string) {
    console.log(pageName);
    this.pageName = pageName;
    switch (pageName) {
      case "Profil":
        this.router.navigateByUrl("/profil");
        break;
      case "Equipes":
        this.router.navigateByUrl("/teams");
        break;
      case "Notification":
        this.router.navigateByUrl("/notifications");
        break;
      case "Dashboard":
        this.router.navigateByUrl("/dashboard");
        break;
      case "Planiification":
        this.router.navigateByUrl("/planning");
        break;
      case "Mon équipe":
        this.router.navigateByUrl("/my-team");
        break;
      case "Projet DEV":
        this.router.navigateByUrl("/dev-project");
        break;
      case "Projet Validation":
        this.router.navigateByUrl("/validation-project");
        break;
      default:
        this.router.navigateByUrl("/dashboard");
        break;
    }
  }
}
