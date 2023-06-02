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

  displayedColumns: string[] = ['name', 'surname', 'speciality'];

  currentUser!: User | null;

  panelOpenState = false;

  testBookLink!: string | null;

  constructor(private apiTeamService: ApiTeamService, public userDataService: UserDataService) {  }
  
  
  ngOnInit(): void {
    this.userDataService.getCurrentUser().subscribe((user: User | null) => {
                                      this.currentUser = user;
                                    });
  }

  applyInTeam(idTeam: number) {
    if (this.currentUser == null) {
      console.log("User is not connected");
      return;
    }
    this.apiTeamService.applyForTeam(idTeam, this.currentUser.id).subscribe((userResponse) => {
      console.log(userResponse);
      this.userDataService.setCurrentUser(userResponse);
      this.update();
      }
    );
  }

  isCurrentUserAStudent() {
    if (this.currentUser == null) {
      console.log("User is not connected");
      return false;
    }
    if (this.currentUser.getRoles().includes("STUDENT_ROLE")){
      return true;
    }
    return false; 
  }

  isCurrentUserATeachingStaff() {
    if (this.currentUser == null) {
      console.log("User is not connected");
      return false;
    }
    if (this.currentUser.getRoles().includes("TEACHING_STAFF_ROLE")){
      return true;
    }
    return false; 
  }

  update(){
    this.applyEvent.emit(this.team.idTeam);
  }

  getTestBookLink(teamId: number) {
    this.apiTeamService.getTestBookLinkDev(teamId).subscribe(
      (link: string) => {
        this.testBookLink = link;
      },
      (error) => {
        console.error("Error getting test book link:", error);
      }
    );
  }
}
