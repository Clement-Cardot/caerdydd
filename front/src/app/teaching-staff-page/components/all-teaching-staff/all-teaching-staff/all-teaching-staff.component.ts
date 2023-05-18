import { Component } from '@angular/core';
import { TeachingStaff } from 'src/app/core/data/models/teaching-staff.model';
import { User } from 'src/app/core/data/models/user.model';
import { ApiTeachingStaffService } from 'src/app/core/services/api-teaching-staff.service';
import { UserDataService } from 'src/app/core/services/user-data.service';

@Component({
  selector: 'app-all-teaching-staff',
  templateUrl: './all-teaching-staff.component.html',
  styleUrls: ['./all-teaching-staff.component.scss'],
})
export class AllTeachingStaffComponent {
  teachingStaffs!: TeachingStaff[];
  currentUser!: User | null;

  constructor(
    private apiTeachingStaffService: ApiTeachingStaffService,
    public userDataService: UserDataService
  ) {}

  ngOnInit(): void {
    this.getAllTS();
    this.userDataService.getCurrentUser().subscribe((user: User | null) => {
      this.currentUser = user;
    });
  }

  public getAllTS() {
    this.apiTeachingStaffService.getAllTeachingStaff().subscribe((data) => {
      this.teachingStaffs = data;
    });
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
