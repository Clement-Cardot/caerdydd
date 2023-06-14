import { Component, OnDestroy, OnInit } from '@angular/core';
import { TeachingStaff } from 'src/app/core/data/models/teaching-staff.model';
import { User } from 'src/app/core/data/models/user.model';
import { ApiTeachingStaffService } from 'src/app/core/services/api-teaching-staff.service';
import { UserDataService } from 'src/app/core/services/user-data.service';

@Component({
  selector: 'app-teaching-staff-page',
  templateUrl: './teaching-staff-page.component.html',
  styleUrls: ['./teaching-staff-page.component.scss'],
})
export class TeachingStaffPageComponent implements OnInit, OnDestroy {
  teachingStaffs: TeachingStaff[] = [];
  currentUser: User | undefined = undefined;
  currentUserSubscription: any;

  constructor(
    private apiTeachingStaffService: ApiTeachingStaffService,
    public userDataService: UserDataService
  ) {}

  ngOnInit(): void {
    this.getAllTS();
    this.currentUserSubscription = this.userDataService.getCurrentUser().subscribe((user: User | undefined) => {
      this.currentUser = user;
    });
  }

  ngOnDestroy(): void {
    this.currentUserSubscription.unsubscribe();
  }

  public getAllTS() {
    this.apiTeachingStaffService.getAllTeachingStaff().subscribe((data) => {
      this.teachingStaffs = data;
    });
  }

  getCurrentTeachingStaff() {
    return this.teachingStaffs.find(teachingStaff => teachingStaff.user.id === this.currentUser?.id);
  }
}
