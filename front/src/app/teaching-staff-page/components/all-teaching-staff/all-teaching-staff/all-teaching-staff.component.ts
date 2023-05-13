import { Component } from '@angular/core';
import { TeachingStaff } from 'src/app/core/data/models/teaching-staff.model';
import { User } from 'src/app/core/data/models/user.model';
import { ApiTeachingStaffService } from 'src/app/core/services/api-teaching-staff.service';
import { ApiUserService } from 'src/app/core/services/api-user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-all-teaching-staff',
  templateUrl: './all-teaching-staff.component.html',
  styleUrls: ['./all-teaching-staff.component.scss'],
})
export class AllTeachingStaffComponent {
  teachingStaffs!: TeachingStaff[];

  constructor(
    private apiTeachingStaffService: ApiTeachingStaffService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.teachingStaffs = this.getAllTS();
  }

  public getAllTS(): TeachingStaff[] {
    this.apiTeachingStaffService.getAllTeachingStaff().subscribe((data) => {
      this.teachingStaffs = data;
    });
    return this.teachingStaffs;
  }

  public toDefineSpeciality(): void {
    this.router.navigateByUrl('/defineSpeciality');
  }
}
