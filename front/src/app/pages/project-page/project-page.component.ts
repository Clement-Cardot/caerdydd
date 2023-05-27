import { Component } from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import { Team } from "src/app/core/data/models/team.model";
import { ApiTeamService } from "src/app/core/services/api-team.service";

@Component({
    selector: 'app-project-page',
    templateUrl: './project-page.component.html',
    styleUrls: ['./project-page.component.scss']
  })
  export class ProjectPageComponent {
    
    id!: string;
    team!: Team;

    constructor(private route: ActivatedRoute, private apiTeamService: ApiTeamService) {  }

    ngOnInit(): void {
      this.id = this.route.snapshot.params['id'];
      this.apiTeamService.getTeam(+this.id).subscribe(data => {
        this.team = data;
      });
    }
  }