import { Component, Input, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Consulting } from 'src/app/core/data/models/consulting.model';
import { User } from 'src/app/core/data/models/user.model';
import { ApiConsultingService } from 'src/app/core/services/api-consulting.service';
import { UserDataService } from 'src/app/core/services/user-data.service';
import { PlannedTimingAvailability } from 'src/app/core/data/models/planned-timing-availability.model';
import { TeachingStaff } from 'src/app/core/data/models/teaching-staff.model';
import { ApiTeachingStaffService } from 'src/app/core/services/api-teaching-staff.service';
import { ApiUserService } from 'src/app/core/services/api-user.service';

@Component({
  selector: 'app-consulting-teaching-s-accept',
  templateUrl: './consulting-teaching-s-accept.component.html',
  styleUrls: ['./consulting-teaching-s-accept.component.scss'],
})
export class ConsultingTeachingSAcceptComponent implements OnInit {
  @Input() consulting!: Consulting;

  currentUser!: User | null;

  teachingStaff!: TeachingStaff;

  plannedAvailabilities: PlannedTimingAvailability[] = [];

  constructor(
    private _snackBar: MatSnackBar,
    private userDataService: UserDataService,
    private apiConsultingService: ApiConsultingService,
    private apiTeachingStaffService: ApiTeachingStaffService,
    public apiUserService: ApiUserService
  ) {}

  ngOnInit() {
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
        });
    }
  }

  openSnackBar() {
    this._snackBar.open('Vous avez accepté ce consulting', 'Fermer', {
      duration: 5000,
    });
  }

  validConsulting(consulting: Consulting): void {
    /*     for (const plannedAvailability of this.plannedAvailabilities) {
      if (
        consulting.plannedTimingConsulting ===
        plannedAvailability.plannedTimingConsulting
      ) {
        console.log('Consulting 1 : ' + consulting);
        consulting.plannedTimingAvailability = plannedAvailability;
        console.log('Consulting 2 : ' + consulting);
        this.apiConsultingService.updateConsulting(consulting);
      }
    }
 */
    this.apiConsultingService.updateConsulting(consulting).subscribe(
      (response) => {
        console.log('Response from server: ', response);
      },
      (error) => {
        console.error('Error:', error);
      }
    );
    this.openSnackBar();
  }
}
