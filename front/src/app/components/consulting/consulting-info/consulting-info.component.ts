import { Component, Input } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Consulting } from 'src/app/core/data/models/consulting.model';
import { DialogAnnotationsComponent } from './dialog-annotations/dialog-annotations.component';

export interface DialogData {
  consulting: Consulting;
  isFinished: boolean;
}

@Component({
  selector: 'app-consulting-info',
  templateUrl: './consulting-info.component.html',
  styleUrls: ['./consulting-info.component.scss']
})

export class ConsultingInfoComponent {
  @Input() consulting!: Consulting;

  constructor(public dialog: MatDialog) {}

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

}
