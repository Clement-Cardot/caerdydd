import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DevProjectComponent } from './dev-project.component';

describe('DevProjectComponent', () => {
  let component: DevProjectComponent;
  let fixture: ComponentFixture<DevProjectComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DevProjectComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DevProjectComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
