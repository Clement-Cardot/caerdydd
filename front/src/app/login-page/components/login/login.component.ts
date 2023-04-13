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
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit  {

  loginForm!: FormGroup;
  matcher = new MyErrorStateMatcher();
  usernameFormControl = new FormControl('', [Validators.required]);
  passwordFormControl = new FormControl('', [Validators.required]);

  currentUser!: User | null;

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
      this.apiAuthService.tryToLogIn(this.loginForm.value.login, this.loginForm.value.password).subscribe(userResponse => {
            if(userResponse) {

                this.userDataService.setCurrentUser(userResponse);
                this.userDataService.getCurrentUser().subscribe((user: User | null) => {
                  this.currentUser = user;
                });

                console.log("Current User is : " + userResponse.login);
                this.redirectDependingOnUserRole();
                
            } else {
                this.router.navigateByUrl("/");
            }
          }
        );
    }
  }

  redirectDependingOnUserRole() {
    if (this.currentUser == null) {
      this.router.navigateByUrl("/");
    } else
    if (this.currentUser.getRoles() == null || this.currentUser.getRoles() == undefined || this.currentUser.getRoles().length == 0) {
      this.router.navigateByUrl("/");
    } else if (this.currentUser.getRoles().includes("PLANNING_ROLE")) {
      this.router.navigateByUrl("planning");
    } else if (this.currentUser.getRoles().includes("OPTION_LEADER_ROLE")) {
      this.router.navigateByUrl("teams-creation");
    } else if (this.currentUser.getRoles().includes("TEACHING_STAFF_ROLE")) {
      this.router.navigateByUrl("teams-creation");
    } else if (this.currentUser.getRoles().includes("TEAM_MEMBER_ROLE")) {
      this.router.navigateByUrl("teams");
    } else if (this.currentUser.getRoles().includes("STUDENT_ROLE")) {
      this.router.navigateByUrl("teams");
    } else {
      this.router.navigateByUrl("dashboard");
    }
  }
}
