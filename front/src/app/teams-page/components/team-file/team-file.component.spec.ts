import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TeamFileComponent } from './team-file.component';

describe('TeamFileComponent', () => {
  let component: TeamFileComponent;
  let fixture: ComponentFixture<TeamFileComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TeamFileComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TeamFileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
