import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AllTeachingStaffComponent } from './all-teaching-staff.component';

describe('AllTeachingStaffComponent', () => {
  let component: AllTeachingStaffComponent;
  let fixture: ComponentFixture<AllTeachingStaffComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AllTeachingStaffComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AllTeachingStaffComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
