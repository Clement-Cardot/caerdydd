import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Team } from 'src/app/core/data/models/team.model';
import { User } from 'src/app/core/data/models/user.model';
import { ApiTeamService } from 'src/app/core/services/api-team.service';
import { UserDataService } from 'src/app/core/services/user-data.service';

@Component({
  selector: 'app-team-info',
  templateUrl: './team-info.component.html',
  styleUrls: ['./team-info.component.scss']
})
export class TeamInfoComponent implements OnInit {

  id!: string;

  team!: Team;

  testBookLink!: string;

  constructor(private route: ActivatedRoute, private apiTeamService: ApiTeamService) {  }
  
  
  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];
    this.apiTeamService.getTeam(+this.id).subscribe(data => {
      this.team = data;
    });
    this.apiTeamService.getTestBookLinkDev(+this.id).subscribe(data => { this.testBookLink = data; });
  }

}
