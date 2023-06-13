import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Consulting } from 'src/app/core/data/models/consulting.model';
import { DialogAnnotationsComponent } from './dialog-annotations/dialog-annotations.component';
import { User } from 'src/app/core/data/models/user.model';
import { UserDataService } from 'src/app/core/services/user-data.service';

export interface DialogData {
  consulting: Consulting;
  isFinished: boolean;
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

  constructor(public dialog: MatDialog, public userDataService: UserDataService) {}

  ngOnInit(): void {
    this.currentUserSubscription = this.userDataService.getCurrentUser().subscribe((user: User | undefined) => {
      this.currentUser = user;
    });
  }

  ngOnDestroy(): void {
    this.currentUserSubscription.unsubscribe();
  }

  isFinished() : boolean {
    return this.consulting.plannedTimingConsulting.datetimeEnd.getTime() < new Date().getTime();
  }
  
  openDialog(): void {
    const dialogRef = this.dialog.open(DialogAnnotationsComponent, {
      data: {consulting : this.consulting},
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }

  isCurrentUserATeachingStaff() {
    if (this.currentUser == null) {
      console.log("User is not connected");
      return false;
    }
    if (this.currentUser.getRoles().includes("TEACHING_STAFF_ROLE")){
      return true;
    }
    return false; 
  }

}
