import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormControl, FormGroupDirective, NgForm } from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { UserDataService } from 'src/app/core/services/user-data.service';
import { ApiAuthService } from 'src/app/core/services/api-auth.service';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from 'src/app/core/data/models/user.model';

export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const isSubmitted = form && form.submitted;
    return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
  }
}

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.scss']
})
export class LoginPageComponent implements OnInit {

  loginForm!: FormGroup;
  matcher = new MyErrorStateMatcher();
  usernameFormControl = new FormControl('', [Validators.required]);
  passwordFormControl = new FormControl('', [Validators.required]);
  public showPassword: boolean = false;

  currentUser: User | undefined = undefined;

  constructor(private router: Router,
    private formBuilder: FormBuilder,
    private apiAuthService: ApiAuthService,
    private userDataService: UserDataService,
    private activatedRoute: ActivatedRoute) {
  }

  ngOnInit() {
    this.loginForm  =  this.formBuilder.group({
      login: this.usernameFormControl,
      password: this.passwordFormControl
    });
    if (this.router.url == "/") {
      this.userDataService.clearCurrentUser();
    };
  }

  loggingIn(): void {
    if(this.loginForm.invalid){
      return;
    } else {
      this.apiAuthService.tryToLogIn(this.loginForm.value.login, this.loginForm.value.password).subscribe(
          userResponse => {
            if(userResponse) {
                this.userDataService.setCurrentUser(userResponse);
                this.userDataService.getCurrentUser().subscribe((user: User | undefined) => {
                  this.currentUser = user;
                  this.redirectUserAfterLogin();
                });
            }
          }, 
          error => {
              // error est le code d'erreur HTTP
              if (error === 401) {
                  // Affiche un message si l'authentification a échoué
                  this.usernameFormControl.setErrors({ 'incorrect': true });
                  this.passwordFormControl.setErrors({ 'incorrect': true });
              } else if (error === 404) {
                  // Affiche un message si l'utilisateur n'est pas trouvé
                  this.usernameFormControl.setErrors({ 'notFound': true });
              }
          }
      );
    }
  }

  redirectUserAfterLogin(): void {
    if (this.currentUser != null) {
      if (this.currentUser.getRoles() == null) {
        this.router.navigateByUrl('/');
      }

      // Option Leader
      if (this.currentUser.getRoles().includes("OPTION_LEADER_ROLE")) {
        this.router.navigateByUrl("marks");
      }

      // Jury Members
      if (this.currentUser.getRoles().includes("JURY_MEMBER_ROLE") && !this.currentUser.getRoles().includes("OPTION_LEADER_ROLE")) {
        this.router.navigateByUrl("marks");
      }

      // Planning
      if (this.currentUser.getRoles().includes('PLANNING_ROLE')) {
        this.router.navigateByUrl("planning");
      }

      // Teaching Staff
      if (this.currentUser.getRoles().includes('TEACHING_STAFF_ROLE')) {
        this.router.navigateByUrl("teachingStaff");
      }

      // Team Member
      if (this.currentUser.getRoles().includes('TEAM_MEMBER_ROLE')) {
        this.router.navigateByUrl("dev-project");
      }

      // Student
      if (this.currentUser.getRoles().includes('STUDENT_ROLE')) {
        this.router.navigateByUrl("teams");
      }
    }
    
  }


  public togglePasswordVisibility(): void {
    this.showPassword = !this.showPassword;
  }
}
