import { Component, OnInit, Input } from '@angular/core';
import { TeamList } from '../../models/team_list.model';

@Component({
  selector: 'app-team-list',
  templateUrl: './team-list.component.html',
  styleUrls: ['./team-list.component.scss']
})

export class TeamListComponent {
  @Input() team_list!: TeamList;
}



