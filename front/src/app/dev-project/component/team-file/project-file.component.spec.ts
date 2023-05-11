import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProjectFileComponent } from './project-file.component';

describe('TeamFileComponent', () => {
  let component: ProjectFileComponent;
  let fixture: ComponentFixture<ProjectFileComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ProjectFileComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProjectFileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
