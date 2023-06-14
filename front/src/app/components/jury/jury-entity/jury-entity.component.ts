import { Component, Input} from '@angular/core';
import { Jury } from 'src/app/core/data/models/jury.model';

@Component({
  selector: 'app-jury-entity',
  templateUrl: './jury-entity.component.html',
  styleUrls: ['./jury-entity.component.scss']
})
export class JuryEntityComponent {
  @Input() jury!: Jury;
}
