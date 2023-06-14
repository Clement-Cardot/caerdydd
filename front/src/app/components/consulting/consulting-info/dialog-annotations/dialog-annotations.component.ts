import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { DialogData } from '../consulting-info.component';

@Component({
  selector: 'app-dialog-annotations',
  templateUrl: './dialog-annotations.component.html',
  styleUrls: ['./dialog-annotations.component.scss']
})
export class DialogAnnotationsComponent {
    constructor(
      public dialogRef: MatDialogRef<DialogAnnotationsComponent>,
      @Inject(MAT_DIALOG_DATA) public data: DialogData,
    ) {}

    onCloseClick(): void {
      this.dialogRef.close();
    }
}
