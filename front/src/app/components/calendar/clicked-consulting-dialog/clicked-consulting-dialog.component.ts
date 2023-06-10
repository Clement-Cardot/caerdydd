import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { PlannedTimingAvailability } from 'src/app/core/data/models/planned-timing-availability.model';
import { PlannedTimingConsulting } from 'src/app/core/data/models/planned-timing-consulting.model';
import { User } from 'src/app/core/data/models/user.model';
import { ApiConsultingService } from 'src/app/core/services/api-consulting.service';
import { UserDataService } from 'src/app/core/services/user-data.service';

interface DialogData {
  event : PlannedTimingConsulting;
}

@Component({
  selector: 'app-clicked-consulting-dialog',
  templateUrl: './clicked-consulting-dialog.component.html',
  styleUrls: ['./clicked-consulting-dialog.component.scss']
})
export class ClickedConsultingDialogComponent {

  currentUser: User | undefined = undefined;
  userRole!: string;
  myAvailability!: PlannedTimingAvailability;

  constructor(
    private apiConsultingService: ApiConsultingService,
    private userDataService: UserDataService,
    public dialogRef: MatDialogRef<ClickedConsultingDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: DialogData,
  ) {
    this.changeDialogDependingOnUserRole();
  }

  changeDialogDependingOnUserRole() {
    this.currentUser = this.userDataService.getCurrentUser().getValue();
    let currentUserRoles = this.currentUser?.getRoles();
    if (currentUserRoles == null || currentUserRoles == undefined || currentUserRoles.length == 0) {
      this.dialogRef.close();
    }
    else if (currentUserRoles.includes("TEAM_MEMBER_ROLE")) {
      this.userRole = "TEAM_MEMBER_ROLE";
    }
    else if (currentUserRoles.includes("TEACHING_STAFF_ROLE")) {
      this.userRole = "TEACHING_STAFF_ROLE";
      let result = this.data.event.availabilities.find(availability => availability.teachingStaff.user.id == this.currentUser?.id);
      if (result == null || result == undefined) {
        this.dialogRef.close();
      }
      else this.myAvailability = result;
    }
    else if (currentUserRoles.includes("PLANNING_ROLE")) {
      this.userRole = "PLANNING_ROLE";
    }
  }

  submit(){
    this.apiConsultingService.updateAvailability(this.myAvailability).subscribe();
    this.dialogRef.close();
  }

  onNoClick(): void {
    this.dialogRef.close();
  }
}
