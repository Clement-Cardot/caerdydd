import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Consulting } from 'src/app/core/data/models/consulting.model';
import { DialogAnnotationsComponent } from './dialog-annotations/dialog-annotations.component';
import { User } from 'src/app/core/data/models/user.model';
import { UserDataService } from 'src/app/core/services/user-data.service';
import { ApiConsultingService } from 'src/app/core/services/api-consulting.service';

export interface DialogData {
  consulting: Consulting;
  newNotes: string;
  isTeachingStaffOfConsulting: boolean;
}

@Component({
  selector: 'app-consulting-info',
  templateUrl: './consulting-info.component.html',
  styleUrls: ['./consulting-info.component.scss']
})

export class ConsultingInfoComponent implements OnInit, OnDestroy{
  @Input() consulting!: Consulting;
  currentUser!: User | undefined;
  currentUserSubscription: any;

  newNotes!: string;
  isTeachingStaffOfConsulting! : boolean;


  constructor(public dialog: MatDialog, public userDataService: UserDataService, public apiConsultingService: ApiConsultingService) {}

  ngOnInit(): void {
    this.currentUserSubscription = this.userDataService.getCurrentUser().subscribe((user: User | undefined) => {
      this.currentUser = user;
    });
    this.isTeachingStaffOfConsulting = this.isCurrentUserTeachingStaffOfConsulting();
  }

  ngOnDestroy(): void {
    this.currentUserSubscription.unsubscribe();
  }

  isFinished() : boolean {
    return this.consulting.plannedTimingConsulting.datetimeEnd.getTime() < new Date().getTime();
  }
  
  openDialog(): void {
    if(this.consulting.notes == null) {
      this.consulting.notes = "";
    } else {
      this.newNotes = this.consulting.notes;
    }
    const dialogRef = this.dialog.open(DialogAnnotationsComponent, {
      data: {consulting : this.consulting, newNotes: this.newNotes, isTeachingStaffOfConsulting: this.isTeachingStaffOfConsulting},
      width: '50%'
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
      if(result != undefined && this.isTeachingStaffOfConsulting) {
        this.newNotes = result;
        this.setNotesConsulting(this.newNotes);
      }
    });
  }

  isCurrentUserATeachingStaff(): boolean {
    if (this.currentUser == null) {
      console.log("User is not connected");
      return false;
    }
    if (this.currentUser.getRoles().includes("TEACHING_STAFF_ROLE")){
      return true;
    }
    return false; 
  }

  isCurrentUserTeachingStaffOfConsulting() : boolean {
    if(this.isCurrentUserATeachingStaff() && this.currentUser != null && this.consulting.plannedTimingAvailability?.teachingStaff.user.id == this.currentUser.id) {
      return true;
    }
    return false;
  }

  setNotesConsulting(newNotes: string) {
    this.apiConsultingService.setNotesConsulting(this.consulting, this.newNotes).subscribe((consultingResponse) => {
      console.log(consultingResponse);
      this.consulting = consultingResponse;
    }
    );
  }

}
