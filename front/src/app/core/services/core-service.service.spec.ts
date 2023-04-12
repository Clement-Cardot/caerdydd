import { TestBed } from '@angular/core/testing';

<<<<<<<< HEAD:front/src/app/core/services/core-service.service.spec.ts
import { CoreServiceService } from './core-service.service';
========
import { LogInService } from './log-in.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';

>>>>>>>> 8aa9b5b78ec47cd178a884c9a66cd18cb3309ed4:front/src/app/login-page/services/log-in.service.spec.ts

describe('CoreServiceService', () => {
  let service: CoreServiceService;

  beforeEach(() => {
<<<<<<<< HEAD:front/src/app/core/services/core-service.service.spec.ts
    TestBed.configureTestingModule({});
    service = TestBed.inject(CoreServiceService);
========
    TestBed.configureTestingModule({
      imports: [
        HttpClientTestingModule
      ],
      providers: [
        LogInService
      ]
    });
    service = TestBed.inject(LogInService);
>>>>>>>> 8aa9b5b78ec47cd178a884c9a66cd18cb3309ed4:front/src/app/login-page/services/log-in.service.spec.ts
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
