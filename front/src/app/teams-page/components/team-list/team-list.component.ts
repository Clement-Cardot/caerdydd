import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { UserDataService } from 'src/app/core/services/user-data.service';
import { Team } from 'src/app/core/data/models/team.model';
import { ApiTeamService } from 'src/app/core/services/api-team.service';
import { User } from 'src/app/core/data/models/user.model';

@Component({
  selector: 'app-team-list',
  templateUrl: './team-list.component.html',
  styleUrls: ['./team-list.component.scss']
})

export class TeamListComponent implements OnInit {
  @Input() team!: Team;
  @Output() applyEvent = new EventEmitter<number>();
  displayedColumns: string[] = ['id', 'name', 'surname', 'speciality'];
  currentUser!: User;
  currentUserRole!: string[];

  constructor(private apiTeamService: ApiTeamService, public userDataService: UserDataService) {  }
  
  
  ngOnInit(): void {
    this.userDataService.getCurrentUser()?.subscribe((user) => {
      this.currentUser = user;
    });
    this.userDataService.getCurrentUserRoles()?.subscribe((roles) => {
      this.currentUserRole = roles;
    });
  }

  applyInTeam(idTeam: number) {
    if (this.currentUser == null) {
      console.log("User is not connected");
      return;
    }
    this.apiTeamService.applyForTeam(idTeam, this.currentUser.id).subscribe((response) => {
      console.log(response);
      this.userDataService.setCurrentUser(response);
      this.update();
    });
  }

  isCurrentUserAStudent() {
    if (this.currentUser == null) {
      console.log("User is not connected");
      return false;
    }
    if (this.currentUserRole.includes("STUDENT_ROLE")){
      return true;
    }

    return false;
    
  }

  update(){
    this.applyEvent.emit(this.team.idTeam);
  }
}
