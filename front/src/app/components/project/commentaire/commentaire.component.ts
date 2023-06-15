import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Team } from 'src/app/core/data/models/team.model';

@Component({
  selector: 'app-commentaire',
  templateUrl: './commentaire.component.html',
  styleUrls: ['./commentaire.component.scss']
})
export class CommentaireComponent {
  @Input() team!: Team;
  @Output() updatableEvent = new EventEmitter();
}
