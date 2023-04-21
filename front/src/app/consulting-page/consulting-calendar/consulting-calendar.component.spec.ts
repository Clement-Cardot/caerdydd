import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConsultingCalendarComponent } from './consulting-calendar.component';

describe('ConsultingCalendarComponent', () => {
  let component: ConsultingCalendarComponent;
  let fixture: ComponentFixture<ConsultingCalendarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ConsultingCalendarComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ConsultingCalendarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
