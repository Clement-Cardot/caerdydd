import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormControl, FormGroupDirective, NgForm } from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { ActivatedRoute, Router } from '@angular/router';
import { LogInService } from '../../services/log-in.service';

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
    private activatedRoute: ActivatedRoute,
    private logInService: LogInService) {
  }

  ngOnInit() {
    this.loginForm  =  this.formBuilder.group({
      username: this.usernameFormControl,
      password: this.passwordFormControl
    });
    this.activatedRoute.url.subscribe(url => {
      console.log(url)
    });
  }

  loggingIn(): void {
    if(this.loginForm.invalid){
      return;
    } else {
      this.logInService.signIn(this.loginForm.value).subscribe(data => {
          if(data) {
            this.router.navigateByUrl("dashboard");
            console.log("twd");
          } else {
            this.router.navigateByUrl("error");
          }
      });
    }
  }
}
