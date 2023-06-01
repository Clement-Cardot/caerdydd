import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StudentImportComponent } from './student-import.component';

describe('StudentImportComponent', () => {
  let component: StudentImportComponent;
  let fixture: ComponentFixture<StudentImportComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ StudentImportComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(StudentImportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
