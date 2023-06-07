import { Component, Input } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Presentation } from 'src/app/core/data/models/presentation.model';
import { Project } from 'src/app/core/data/models/project.model';
import { ApiPresentationService } from 'src/app/core/services/api-presentation.service';
import { ApiProjectService } from 'src/app/core/services/api-project.service';

@Component({
  selector: 'app-jury-commentary',
  templateUrl: './jury-commentary.component.html',
  styleUrls: ['./jury-commentary.component.scss']
})
export class JuryCommentaryComponent {
  panelOpenState = false;
  commentary !: string;
  errorMessage !: string;
  project !: Project;
  currentDate !: Date;
  presentationDate !: Date;

  constructor(private apiPresentationService : ApiPresentationService,
              private _snackBar: MatSnackBar){}

  @Input() presentation!: Presentation;

  ngOnInit(){
    this.presentationDate = new Date(this.presentation.datetimeBegin);
    this.currentDate = new Date();
    this.commentary = this.presentation.jury1Notes;
  }

  onSubmit() {
    if (this.presentation.id !== undefined) {
      const idPresentation = parseInt(this.presentation.id.toString(), 10);
      this.apiPresentationService.updateJuryNotes(idPresentation, this.commentary)
        .subscribe(
          data => {
            console.log('Mise à jour des notes du jury effectuée avec succès :', data);
            this.showSuccess();
          },
          error => { this.showError(error);}
        );
    }
  }

  showSuccess() {
    this._snackBar.open("Le commentaire a été mis à jour.", "Fermer", {
      duration: 5000,
    });
  }

  showError(error: { status: number; }) {
    this.errorMessage = "Une erreur inconnue est survenue, veuillez contacter l'administrateur. (" + error.status + ")";
  }
  
}
