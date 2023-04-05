import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewValidateSubjectsComponent } from './view-validate-subjects.component';

describe('ViewValidateSubjectsComponent', () => {
  let component: ViewValidateSubjectsComponent;
  let fixture: ComponentFixture<ViewValidateSubjectsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ViewValidateSubjectsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ViewValidateSubjectsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
