import { ComponentFixture, TestBed } from '@angular/core/testing';

import { JuryCreationComponent } from './jury-creation.component';

describe('JuryCreationComponent', () => {
  let component: JuryCreationComponent;
  let fixture: ComponentFixture<JuryCreationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ JuryCreationComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(JuryCreationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
