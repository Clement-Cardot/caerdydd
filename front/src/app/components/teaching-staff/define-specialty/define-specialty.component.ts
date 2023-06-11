import { Component, Input } from '@angular/core';
import { TeachingStaff } from 'src/app/core/data/models/teaching-staff.model';
import { ApiTeachingStaffService } from 'src/app/core/services/api-teaching-staff.service';
import { UserDataService } from 'src/app/core/services/user-data.service';
import { ApiUserService } from 'src/app/core/services/api-user.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-define-specialty',
  templateUrl: './define-specialty.component.html',
  styleUrls: ['./define-specialty.component.scss'],
})
export class DefineSpecialtyComponent {
  @Input() teachingStaff!: TeachingStaff | undefined;

  constructor(
    private apiTeachingStaffService: ApiTeachingStaffService,
    public userDataService: UserDataService,
    public apiUserService: ApiUserService,
    private _snackBar: MatSnackBar
  ) {}

  modifySpeciality() {
    if (this.teachingStaff == undefined) {
      console.error('Teaching staff is undefined');
      return;
    }
    this.apiTeachingStaffService.setSpeciality(this.teachingStaff).subscribe(
      (response) => {
        this.openSnackBar();
      },
      (error) => {
        console.error('Error:', error);
      }
    );
  }

  openSnackBar() {
    this._snackBar.open('Spécialités modifiées', 'Fermer', {
      duration: 5000,
    });
  }
}
