import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormControl, FormGroupDirective, NgForm } from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { UserDataService } from 'src/app/core/services/user-data.service';
import { ApiAuthService } from 'src/app/core/services/api-auth.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Role } from 'src/app/core/data/models/role.model';

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

  currentUserRoles!: string[];

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
      this.apiAuthService.tryToLogIn(this.loginForm.value.login, this.loginForm.value.password).subscribe(response => {
        if(response) {
            this.userDataService.setCurrentUser(response);
            this.userDataService.getCurrentUserRoles()?.subscribe((roles) => {
              this.currentUserRoles = roles;
            });
            console.log("Current User is : " + response.login);
            this.redirectDependingOnUserRole();
            
        } else {
            this.router.navigateByUrl("/");
        }
      });
    }
  }

  redirectDependingOnUserRole() {
    if (this.currentUserRoles == null || this.currentUserRoles == undefined || this.currentUserRoles.length == 0) {
      this.router.navigateByUrl("/");
    }
    else if (this.currentUserRoles.includes("OPTION_LEADER_ROLE")) {
      this.router.navigateByUrl("teams-creation");
    }
    else if (this.currentUserRoles.includes("TEAM_LEADER_ROLE")) {
      this.router.navigateByUrl("teams-creation");
    }
    else if (this.currentUserRoles.includes("TEAM_MEMBER_ROLE")) {
      this.router.navigateByUrl("teams"); // TODO : redirect to specific team page
    }
    else if (this.currentUserRoles.includes("STUDENT_ROLE")) {
      this.router.navigateByUrl("teams");
    }
    else {
      this.router.navigateByUrl("dashboard");
    }
  }
}
