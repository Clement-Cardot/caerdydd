import { ComponentFixture, TestBed } from '@angular/core/testing';

import { JuryCommentaryComponent } from './jury-commentary.component';

describe('JuryCommentaryComponent', () => {
  let component: JuryCommentaryComponent;
  let fixture: ComponentFixture<JuryCommentaryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ JuryCommentaryComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(JuryCommentaryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
