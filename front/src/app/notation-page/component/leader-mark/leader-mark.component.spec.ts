import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LeaderMarkComponent } from './leader-mark.component';

describe('LeaderMarkComponent', () => {
  let component: LeaderMarkComponent;
  let fixture: ComponentFixture<LeaderMarkComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LeaderMarkComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LeaderMarkComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
