import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LoginComponent } from './login.component';
import { MockService } from 'ng-mocks';
import { LogInService } from '../../services/log-in.service';
import { of } from 'rxjs';

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  let logInService: jasmine.SpyObj<LogInService>;

  beforeEach(async () => {
    logInService = jasmine.createSpyObj(LogInService, ['getLoginValidation', 'signIn']);
    logInService.getLoginValidation.and.returnValue(of(true));

    await TestBed.configureTestingModule({
      declarations: [ LoginComponent ],
      providers: [
       {
        provide: LogInService,
        useValue: logInService
       }
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
    expect(logInService.getLoginValidation).toHaveBeenCalled()
    expect(logInService.getLoginValidation({} as any)).toBe(of(true))
  });
});
