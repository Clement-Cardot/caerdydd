import { MediaMatcher } from '@angular/cdk/layout';
import {
  ChangeDetectorRef,
  Component,
  OnChanges,
  OnDestroy,
  OnInit,
} from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/core/data/models/user.model';
import { UserDataService } from 'src/app/core/services/user-data.service';

@Component({
  selector: 'app-sidenav',
  templateUrl: './sidenav.component.html',
  styleUrls: ['./sidenav.component.scss'],
})
export class SidenavComponent implements OnInit, OnChanges, OnDestroy {
  mobileQuery: MediaQueryList;
  pageName!: string;

  fillerNav = Array.from({ length: 5 }, (_, i) => `Nav Item ${i + 1}`);
  navLink = new Array<string>();

  currentUser: User | undefined = undefined;

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
    this.pageName = 'Accueil';
    this.userDataService.getCurrentUser().subscribe((user: User | undefined) => {
      this.currentUser = user;
      this.initButtons();
    });
  }

  ngOnChanges(): void {
    this.initButtons();
  }

  ngOnDestroy(): void {
    this.mobileQuery.removeListener(this._mobileQueryListener);
  }

  initButtons() {
    this.navLink = new Array<string>();
    if (this.currentUser != null) {
      if (this.currentUser.getRoles() == null) {
        this.router.navigateByUrl('/');
      }
      // Global
      // this.navLink.push('Tableau de bord');

      // Option Leader
      if (this.currentUser.getRoles().includes("OPTION_LEADER_ROLE")) {
        this.navLink.push("Administration");
        this.navLink.push("Notes");
        this.navLink.push("Sujets");
      }

      // Jury Members
      if (this.currentUser.getRoles().includes("JURY_MEMBER_ROLE") && !this.currentUser.getRoles().includes("OPTION_LEADER_ROLE")) {
        this.navLink.push("Notes");
      }

      // Planning
      if (this.currentUser.getRoles().includes('PLANNING_ROLE')) {
        this.navLink.push('Planification');
      }

      // All except Student
      if (!this.currentUser.getRoles().includes('STUDENT_ROLE')) {
        this.navLink.push('Calendrier');
      }

      // Teaching Staff
      if (this.currentUser.getRoles().includes('TEACHING_STAFF_ROLE')) {
        this.navLink.push('Consultings');
        this.navLink.push('Equipes');
        this.navLink.push('Corps Enseignant');
      }

      // Team Member
      if (this.currentUser.getRoles().includes('TEAM_MEMBER_ROLE')) {
        this.navLink.push('Mon équipe');
        this.navLink.push('Projet Développement');
        this.navLink.push('Projet Validation');
        this.navLink.push('Commentaires Présentation');
      }

      // Student
      if (this.currentUser.getRoles().includes('STUDENT_ROLE')) {
        this.navLink.push('Equipes');
      }

      // Jury Member
      if (this.currentUser.getRoles().includes('JURY_MEMBER_ROLE')) {
        this.navLink.push('Commentaires Présentation');
      }
    }
  }

  switchPage(pageName: string) {
    this.pageName = pageName;
    switch (pageName) {
      case 'Tableau de bord':
        this.router.navigateByUrl('/dashboard');
        break;
      case 'Administration':
        this.router.navigateByUrl('/administration');
        break;
      case 'Planification':
        this.router.navigateByUrl('/planning');
        break;
      case 'Calendrier':
        this.router.navigateByUrl('/calendar');
        break;
      case 'Consultings':
        this.router.navigateByUrl('/consultings');
        break;
      case 'Commentaires Présentation':
        this.router.navigateByUrl('/presentationCommentary');
        break;
      case 'Notes':
        this.router.navigateByUrl('/marks');
        break;
      case 'Sujets':
        this.router.navigateByUrl('/subjects');
        break;
      case 'Equipes':
        this.router.navigateByUrl('/teams');
        break;
      case 'Corps Enseignant':
        this.router.navigateByUrl('/teachingStaff');
        break;
      case 'Mon équipe':
        this.router.navigateByUrl('/my-team');
        break;
      case 'Projet Développement':
        this.router.navigateByUrl('/dev-project');
        break;
      case 'Projet Validation':
        this.router.navigateByUrl('/validation-project');
        break;
      default:
        this.router.navigateByUrl('/dashboard');
        break;
    }
  }
}
