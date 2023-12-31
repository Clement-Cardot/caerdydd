import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormControl, FormGroupDirective, NgForm } from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { UserDataService } from 'src/app/core/services/user-data.service';
import { ApiAuthService } from 'src/app/core/services/api-auth.service';
import { Router } from '@angular/router';
import { User } from 'src/app/core/data/models/user.model';

export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const isSubmitted = form?.submitted;
    return !!( control?.invalid && (control.dirty || control.touched || isSubmitted));
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
    private userDataService: UserDataService
    ) {
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
      this.usernameFormControl.markAsUntouched();
      this.passwordFormControl.markAsUntouched();

      this.usernameFormControl.updateValueAndValidity();
      this.passwordFormControl.updateValueAndValidity();

      this.apiAuthService.tryToLogIn(this.loginForm.value.login, this.loginForm.value.password).subscribe(
          userResponse => {
            if(userResponse) {
                this.userDataService.setCurrentUser(userResponse);
                this.userDataService.getCurrentUser().subscribe((user: User | undefined) => {
                  this.currentUser = user;
                });
                this.redirectUserAfterLogin();
            }
          }, 
          error => {
              // error est le code d'erreur HTTP
              if (error === 401) {
                  // Affiche un message si l'authentification a échoué
                  this.usernameFormControl.setErrors({ 'incorrect': true });
                  this.passwordFormControl.setErrors({ 'incorrect': true });
              }
          }
      );
    }
  }

  redirectUserAfterLogin(): void {
    if (this.currentUser != null) {
      if (this.currentUser.getRoles() == null) {
        this.router.navigateByUrl('/').catch(() => {
            console.error("Error while redirecting to home page");
          }
        );
      }

      // Option Leader
      if (this.currentUser.getRoles().includes("OPTION_LEADER_ROLE")) {
        this.router.navigateByUrl("marks").catch(() => {
            console.error("Error while redirecting to marks page");
          }
        );
      }

      // Jury Members
      if (this.currentUser.getRoles().includes("JURY_MEMBER_ROLE") && !this.currentUser.getRoles().includes("OPTION_LEADER_ROLE")) {
        this.router.navigateByUrl("marks").catch(() => {
            console.error("Error while redirecting to marks page");
          }
        );
      }

      // Planning
      if (this.currentUser.getRoles().includes('PLANNING_ROLE')) {
        this.router.navigateByUrl("planning").catch(() => {
            console.error("Error while redirecting to planning page");
          }
        );
      }

      // Teaching Staff
      if (this.currentUser.getRoles().includes('TEACHING_STAFF_ROLE')) {
        this.router.navigateByUrl("teachingStaff").catch(() => {
            console.error("Error while redirecting to teachingStaff page");
          }
        );
      }

      // Team Member
      if (this.currentUser.getRoles().includes('TEAM_MEMBER_ROLE')) {
        this.router.navigateByUrl("dev-project").catch(() => {
            console.error("Error while redirecting to dev-project page");
          }
        );
      }

      // Student
      if (this.currentUser.getRoles().includes('STUDENT_ROLE')) {
        this.router.navigateByUrl("teams").catch(() => {
            console.error("Error while redirecting to teams page");
          }
        );
      }
    }
    
  }

  onChange(): void {
    this.usernameFormControl.setErrors({ 'incorrect': false });
    this.passwordFormControl.setErrors({ 'incorrect': false });

    this.usernameFormControl.markAsTouched();
    this.passwordFormControl.markAsTouched();

    this.usernameFormControl.updateValueAndValidity();
    this.passwordFormControl.updateValueAndValidity();
  }

  public togglePasswordVisibility(): void {
    this.showPassword = !this.showPassword;
  }
}
