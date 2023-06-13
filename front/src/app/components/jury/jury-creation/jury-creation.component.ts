import { Component, Output, OnInit, EventEmitter } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Jury } from 'src/app/core/data/models/jury.model';
import { TeachingStaff } from 'src/app/core/data/models/teaching-staff.model';
import { ApiJuryService } from 'src/app/core/services/api-jury.service';
import { ApiTeachingStaffService } from 'src/app/core/services/api-teaching-staff.service';

@Component({
  selector: 'app-jury-creation',
  templateUrl: './jury-creation.component.html',
  styleUrls: ['./jury-creation.component.scss']
})
export class JuryCreationComponent implements OnInit {
  teachingStaffs!: TeachingStaff[];
  @Output() juryCreation = new EventEmitter<Jury>();

  selectedOption1: string = "0";
  selectedOption2: string = "0";

  errorMessage: string = "";

  constructor(private apiJuryService: ApiJuryService, 
              private apiTeachingStaffService: ApiTeachingStaffService, 
              private _snackBar: MatSnackBar){}

  ngOnInit(): void {
    this.getAllData();
  }

  getAllData(): void{
    this.apiTeachingStaffService.getAllTeachingStaff()
        .subscribe(data => {
            this.teachingStaffs = data;
          }
        );
  }

  isSubmitable() : boolean{
    const option1 = parseInt(this.selectedOption1, 10);
    const option2 = parseInt(this.selectedOption2, 10);

    if (option1 > 0 && option2 > 0 && option1 != option2) {
      return false;
    }
    return true;
  }

  onSubmit() : void {
    const option1 = parseInt(this.selectedOption1, 10);
    const option2 = parseInt(this.selectedOption2, 10);

    this.apiJuryService.addJury(option1, option2)
      .subscribe(
        data => {this.showSuccess(data)},
        error => {this.showError(error)},
      );
  }

  showSuccess(data : Jury) {
    this._snackBar.open("Le jury, constitué de " + data.ts1.user.lastname + " et " + data.ts2.user.lastname + ", a bien été créé.", "Fermer", {
      duration: 5000,
    });
    this.juryCreation.emit(data);
  }

  showError(error: { status: number; }) {
    switch (error.status) {
      case 409:
        this.errorMessage = "Le jury existe déjà";
        break;
      case 500:
        this.errorMessage = "Une erreur est survenue, veuillez contacter l'administrateur";
        break;
      default:
        this.errorMessage = "Une erreur est survenue, veuillez contacter l'administrateur";
    }
  }

}