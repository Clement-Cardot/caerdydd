import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AllTeamsListComponent } from './all-teams-list.component';

describe('AllTeamsListComponent', () => {
  let component: AllTeamsListComponent;
  let fixture: ComponentFixture<AllTeamsListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AllTeamsListComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AllTeamsListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
