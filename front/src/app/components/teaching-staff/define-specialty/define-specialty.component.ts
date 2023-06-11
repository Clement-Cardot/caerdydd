import { Component, OnInit } from '@angular/core';
import { TeachingStaff } from 'src/app/core/data/models/teaching-staff.model';
import { ApiTeachingStaffService } from 'src/app/core/services/api-teaching-staff.service';
import { UserDataService } from 'src/app/core/services/user-data.service';
import { ApiUserService } from 'src/app/core/services/api-user.service';
import { User } from 'src/app/core/data/models/user.model';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-define-specialty',
  templateUrl: './define-specialty.component.html',
  styleUrls: ['./define-specialty.component.scss'],
})
export class DefineSpecialtyComponent implements OnInit {
  teachingStaff!: TeachingStaff;
  currentUser: User | undefined = undefined;

  constructor(
    private apiTeachingStaffService: ApiTeachingStaffService,
    public userDataService: UserDataService,
    public apiUserService: ApiUserService,
    private _snackBar: MatSnackBar
  ) {}

  public ngOnInit(): void {
    this.userDataService.getCurrentUser().subscribe((user: User | undefined) => {
      this.currentUser = user;
    });
    if (this.currentUser == undefined) {
      console.log('User is not connected');
    } else {
      this.apiTeachingStaffService
        .getTeachingStaff(this.currentUser.id)
        .subscribe((teachingStaff: TeachingStaff) => {
          this.teachingStaff = teachingStaff;
        });
    }
  }

  modifySpeciality() {
    this.apiTeachingStaffService.setSpeciality(this.teachingStaff).subscribe(
      (response) => {
        this.openSnackBar();
      },
      (error) => {
        console.error('Error:', error);
      }
    );
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

  openSnackBar() {
    this._snackBar.open('Spécialités modifiées', 'Fermer', {
      duration: 5000,
    });
  }
}
