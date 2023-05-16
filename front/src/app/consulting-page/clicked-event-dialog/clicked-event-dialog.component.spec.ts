import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ClickedEventDialogComponent } from './clicked-event-dialog.component';

describe('ClickedEventDialogComponent', () => {
  let component: ClickedEventDialogComponent;
  let fixture: ComponentFixture<ClickedEventDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ClickedEventDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ClickedEventDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
