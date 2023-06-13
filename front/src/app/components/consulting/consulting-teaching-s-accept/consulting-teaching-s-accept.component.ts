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

  specialities: string[] = [];

  bool!: boolean;

  teachingStaffLoaded = false; // Ajout d'un indicateur pour suivre si teachingStaff est chargé

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
        .subscribe(
          (teachingStaff: TeachingStaff) => {
            this.teachingStaff = teachingStaff;
            this.filterBySpeciality(this.consulting);
            this.teachingStaffLoaded = true; // Indique que teachingStaff est chargé
          },
          (error) => {
            console.error('Error while fetching teaching staff:', error);
          }
        );
    }
  }

  openSnackBar() {
    this._snackBar.open('Vous avez accepté ce consulting', 'Fermer', {
      duration: 5000,
    });
  }

  filterBySpeciality(consulting: Consulting): boolean {
    if (!this.teachingStaffLoaded) {
      return false; // Quitte la fonction si teachingStaff n'est pas encore chargé
    }
    if (this.teachingStaff.isInfrastructureSpecialist) {
      this.specialities.push('Infrastructure');
    }
    if (this.teachingStaff.isDevelopmentSpecialist) {
      this.specialities.push('Développement');
    }
    if (this.teachingStaff.isModelingSpecialist) {
      this.specialities.push('Modélisation');
    }
    return this.specialities.includes(consulting.speciality);
  }

  validConsulting(consulting: Consulting): void {
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
