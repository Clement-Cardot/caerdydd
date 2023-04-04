import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BoxValidateSubjectComponent } from './box-validate-subject.component';

describe('BoxValidateSubjectComponent', () => {
  let component: BoxValidateSubjectComponent;
  let fixture: ComponentFixture<BoxValidateSubjectComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BoxValidateSubjectComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BoxValidateSubjectComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
