import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { TeachingStaff } from 'src/app/core/data/models/teaching-staff.model';
import { ApiTeachingStaffService } from 'src/app/core/services/api-teaching-staff.service';
import { UserDataService } from 'src/app/core/services/user-data.service';
import { ApiUserService } from 'src/app/core/services/api-user.service';
import { ActivatedRoute } from '@angular/router';
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
    public apiUserService: ApiUserService,
  ) {}

  public ngOnInit(): void {
    // TODO : Récupèrer l'utilisateur courant
    // ...
    // TODO : Récupèrer le TS correspondant à l'ID du currentUser
    // ...

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

  // Quand clic sur valider :
  // 1 Mettre à jour les attributs du TS
  // 2 Renvoyer l'entité du TS via l'API Service

  /* public modifySpeciality() {
    this.ngOptions.forEach((opt) => {
      console.log('My input:', opt.value);
      console.log('My opt.checked:', opt.checked);
      this.apiTeachingStaffService.setSpeciality(
        opt.value,
        opt.checked,
        Number(this.route.snapshot.paramMap.get('id'))
      );
    });
  } */

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
    if (this.teachingStaff) {
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

      this.apiTeachingStaffService.setSpeciality(this.teachingStaff);
    }
  }

  /* public getSpecialities(): void {
    if (this.isCurrentUserATeachingStaff()) {
      this.teachingStaff = this.apiUserService.getUserById(
        Number(this.route.snapshot.paramMap.get('id'))
      );
      this.ngOptions.forEach((opt) => {
        if (this.teachingStaff.isDevelopmentSpecialist === true) {
          opt.checked = true;
        }
        if (this.teachingStaff.isInfrastructureSpecialist === true) {
          opt.checked = true;
        }
        if (this.teachingStaff.isModelingSpecialist === true) {
          opt.checked = true;
        }
      });
    }
  } */

  /* public createListSpeciality(
    speciality: string,
    addOrRemove: boolean
  ): string[] {
    let specialities: string[] = [];
    if (addOrRemove === true) {
      specialities.push(speciality);
    }
    return specialities;
  } */

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

  /*   public getSpecialities(): string[] {
    const idUser = this.userDataService.getCurrentUser().id;
    this.apiTeachingStaffService.getSpeciality(idUser).subscribe((data) => {
      if (this.teachingStaff.isDevelopmentSpecialist) {
        this.specialities.push('Développement');
      }
      if (this.teachingStaff.isInfrastrctureSpecialist) {
        this.specialities.push('Infrastructure');
      }
      if (this.teachingStaff.isModelingSpecialist) {
        this.specialities.push('Modélisation');
      }
    });
    return this.specialities;
  }
 */
}
