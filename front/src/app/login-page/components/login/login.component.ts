import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormControl, FormGroupDirective, NgForm } from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { Router } from '@angular/router';
import { UserDataService } from 'src/app/core/services/user-data.service';
import { ApiAuthService } from 'src/app/core/services/api-auth.service';
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

  constructor(private router: Router,
    private formBuilder: FormBuilder,
    private apiAuthService: ApiAuthService,
    private userDataService: UserDataService) {
  }

  ngOnInit() {
    this.loginForm  =  this.formBuilder.group({
      login: this.usernameFormControl,
      password: this.passwordFormControl
    });
  }

  loggingIn(): void {
    if(this.loginForm.invalid){
      return;
    } else {
      this.apiAuthService.tryToLogIn(this.loginForm.value.login, this.loginForm.value.password).subscribe(response => {
        if(response) {
            this.userDataService.setCurrentUser(response);
            console.log("Current User is : " + this.userDataService.getCurrentUser().login);
            this.router.navigateByUrl("teams");
        } else {
            this.router.navigateByUrl("error");
        }
      });
    }
  }
}
