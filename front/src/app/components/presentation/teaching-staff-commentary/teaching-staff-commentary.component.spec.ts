import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TeachingStaffCommentaryComponent } from './teaching-staff-commentary.component';

describe('JuryCommentaryComponent', () => {
  let component: TeachingStaffCommentaryComponent;
  let fixture: ComponentFixture<TeachingStaffCommentaryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TeachingStaffCommentaryComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TeachingStaffCommentaryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
