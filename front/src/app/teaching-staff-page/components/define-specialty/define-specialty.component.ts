import { Component, OnInit } from '@angular/core';
import { TeachingStaff } from 'src/app/core/data/models/teaching-staff.model';
import { ApiTeachingStaffService } from 'src/app/core/services/api-teaching-staff.service';
import { UserDataService } from 'src/app/core/services/user-data.service';
import { ApiUserService } from 'src/app/core/services/api-user.service';
import { User } from 'src/app/core/data/models/user.model';

@Component({
  selector: 'app-define-specialty',
  templateUrl: './define-specialty.component.html',
  styleUrls: ['./define-specialty.component.scss'],
})
export class DefineSpecialtyComponent implements OnInit {
  teachingStaff!: TeachingStaff;
  ngOptions: any[] = [];
  currentUser!: User | null;

  constructor(
    private apiTeachingStaffService: ApiTeachingStaffService,
    public userDataService: UserDataService,
    public apiUserService: ApiUserService
  ) {}

  public ngOnInit(): void {
    this.userDataService.getCurrentUser().subscribe((user: User | null) => {
      this.currentUser = user;
    });
    if (this.currentUser == null) {
      console.log('User is not connected');
    } else {
      this.apiTeachingStaffService
        .getTeachingStaff(this.currentUser.id)
        .subscribe((teachingStaff: TeachingStaff) => {
          this.teachingStaff = teachingStaff;
          this.initializeNgOptions();
        });
    }
  }

  private initializeNgOptions(): void {
    this.ngOptions = [
      {
        value: 'Infrastructure',
        viewValue: 'Infrastructure',
        checked: this.teachingStaff.isInfrastructureSpecialist,
      },
      {
        value: 'Developpement',
        viewValue: 'Développement',
        checked: this.teachingStaff.isDevelopmentSpecialist,
      },
      {
        value: 'Modelisation',
        viewValue: 'Modélisation',
        checked: this.teachingStaff.isModelingSpecialist,
      },
    ];
  }

  public modifySpeciality2() {
    console.log(
      'Old Teaching Staff is : ' +
        this.teachingStaff.isInfrastructureSpecialist +
        ', ' +
        this.teachingStaff.isDevelopmentSpecialist +
        ', ' +
        this.teachingStaff.isModelingSpecialist
    );
    for (const opt of this.ngOptions) {
      if (opt.value === 'Infrastructure') {
        this.teachingStaff.isInfrastructureSpecialist = opt.checked;
        console.log(
          'Infrastructure is:',
          this.teachingStaff.isInfrastructureSpecialist
        );
      }
      if (opt.value === 'Developpement') {
        this.teachingStaff.isDevelopmentSpecialist = opt.checked;
        console.log(
          'Developpement is:',
          this.teachingStaff.isDevelopmentSpecialist
        );
      }
      if (opt.value === 'Modelisation') {
        this.teachingStaff.isModelingSpecialist = opt.checked;
        console.log(
          'Modelisation is:',
          this.teachingStaff.isModelingSpecialist
        );
      }
    }
    console.log(
      'New Teaching Staff is : ' +
        this.teachingStaff.isInfrastructureSpecialist +
        ', ' +
        this.teachingStaff.isDevelopmentSpecialist +
        ', ' +
        this.teachingStaff.isModelingSpecialist
    );
    this.apiTeachingStaffService.setSpeciality(this.teachingStaff);
  }

  isCurrentUserATeachingStaff() {
    if (this.currentUser == null) {
      console.log('User is not connected');
      return false;
    }
    if (this.currentUser.getRoles().includes('TEACHING_STAFF_ROLE')) {
      return true;
    }
    return false;
  }
}
