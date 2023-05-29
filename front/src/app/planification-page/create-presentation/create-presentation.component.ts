import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Jury } from 'src/app/core/data/models/jury.model';
import { Project } from 'src/app/core/data/models/project.model';
import { Presentation, PresentationPayload } from 'src/app/core/data/models/presentation.model';
import { ApiPresentationService } from 'src/app/core/services/api-presentation.service';
import { ApiJuryService } from 'src/app/core/services/api-jury.service';
import { ApiProjectService } from 'src/app/core/services/api-project.service';
import { FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-create-presentation',
  templateUrl: './create-presentation.component.html',
  styleUrls: ['./create-presentation.component.scss']
})
export class CreatePresentationComponent implements OnInit {
  presentationForm!: FormGroup;
  juries!: Jury[];
  projects!: Project[];

  constructor(
    private apiPresentationService: ApiPresentationService, 
    private apiJuryService: ApiJuryService,
    private apiProjectService: ApiProjectService,
    private _snackBar: MatSnackBar
  ) { }

  ngOnInit(): void {
    this.presentationForm = new FormGroup({
      'type': new FormControl(null, Validators.required),
      'datetimeBegin': new FormControl(null, Validators.required),
      'datetimeEnd': new FormControl(null),
      'room': new FormControl(null, Validators.required),
      'idJury': new FormControl(null, Validators.required),
      'idProject': new FormControl(null, Validators.required)
    });

    this.apiJuryService.getAllJuries().subscribe(juries => {this.juries = juries;});
    this.apiProjectService.getAllSubjects().subscribe(projects => this.projects = projects);
  }

  onSubmit() {
    if (!this.presentationForm.valid) {
      return;
    }
    
    const beginDate = new Date(this.presentationForm.value['datetimeBegin']);
    const endDate = new Date(beginDate);

    // Récupérer le décalage horaire en minutes et le convertir en heures
    const timezoneOffsetHours = beginDate.getTimezoneOffset() / 60;
    
    // Ajuster les heures en fonction du décalage horaire
    beginDate.setHours(beginDate.getHours() - timezoneOffsetHours);
    endDate.setHours(endDate.getHours() - timezoneOffsetHours + 1);

    const presentationPayload: PresentationPayload = {
        type: this.presentationForm.value['type'],
        datetimeBegin: beginDate,
        datetimeEnd: endDate,
        room: this.presentationForm.value['room'],
        jury: {
            idJury: this.presentationForm.value['idJury'],
        },
        project: {
            idProject: this.presentationForm.value['idProject'],
        }
    };

    this.apiPresentationService.createPresentation(presentationPayload)
        .subscribe(
            data => { this.showSuccess(data) },
            error => { this.showError(error) },
        );
  }

  

  showSuccess(data: Presentation) {
    this._snackBar.open("La présentation a été  créée avec succès", "Fermer", {
      duration: 5000,
    });
    this.presentationForm.reset();
  }

  showError(error: any) {
    let errorMessage = "Erreur lors de la création de la présentation : ";
    switch (error.status) {
        case 404:
            errorMessage += "Des informations n'ont pas été trouvées";
            break;
        case 409:
            errorMessage += "L'un des enseignants du Jury n'est pas disponible à ce moment";
            break;
        case 403:
            errorMessage += "L'utilisateur n'a pas les rôles nécessaires pour effectuer cette action";
            break;
        case 400:
            errorMessage += "Une présentation ne peut pas se terminer avant qu'elle ne commence";
            break;
        case 500:
            errorMessage += "Réessayer ultérieurement";
            break;
        default:
            errorMessage += error.message;
    }
    this._snackBar.open(errorMessage, "Fermer", {
        duration: 5000,
    });
  }
}