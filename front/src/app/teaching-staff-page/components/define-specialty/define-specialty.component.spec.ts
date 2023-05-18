import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DefineSpecialtyComponent } from './define-specialty.component';

describe('DefineSpecialtyComponent', () => {
  let component: DefineSpecialtyComponent;
  let fixture: ComponentFixture<DefineSpecialtyComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DefineSpecialtyComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DefineSpecialtyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
