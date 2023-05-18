import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ValidationProjectComponent } from './validation-project.component';

describe('ValidationProjectComponent', () => {
  let component: ValidationProjectComponent;
  let fixture: ComponentFixture<ValidationProjectComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ValidationProjectComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ValidationProjectComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
