import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConsultingImportComponent } from './consulting-import.component';

describe('ConsultingImportComponent', () => {
  let component: ConsultingImportComponent;
  let fixture: ComponentFixture<ConsultingImportComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ConsultingImportComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ConsultingImportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
