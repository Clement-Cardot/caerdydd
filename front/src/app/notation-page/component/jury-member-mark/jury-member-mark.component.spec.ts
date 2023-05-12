import { ComponentFixture, TestBed } from '@angular/core/testing';

import { JuryMemberMarkComponent } from './jury-member-mark.component';

describe('JuryMemberMarkComponent', () => {
  let component: JuryMemberMarkComponent;
  let fixture: ComponentFixture<JuryMemberMarkComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ JuryMemberMarkComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(JuryMemberMarkComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
