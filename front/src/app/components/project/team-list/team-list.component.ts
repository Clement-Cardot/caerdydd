import { Component, EventEmitter, Input, OnDestroy, OnInit, Output } from '@angular/core';
import { UserDataService } from 'src/app/core/services/user-data.service';
import { Team } from 'src/app/core/data/models/team.model';
import { ApiTeamService } from 'src/app/core/services/api-team.service';
import { User } from 'src/app/core/data/models/user.model';

@Component({
  selector: 'app-team-list',
  templateUrl: './team-list.component.html',
  styleUrls: ['./team-list.component.scss']
})

export class TeamListComponent implements OnInit, OnDestroy {
  @Input() team!: Team;
  @Output() applyEvent = new EventEmitter<number>();

  displayedColumns: string[] = ['name', 'surname', 'speciality'];

  currentUser: User | undefined = undefined;
  currentUserSubscription: any;

  panelOpenState = false;

  testBookLink!: string | null;

  constructor(private apiTeamService: ApiTeamService, public userDataService: UserDataService) {  }
  
  
  ngOnInit(): void {
    this.currentUserSubscription = this.userDataService.getCurrentUser().subscribe((user: User | undefined) => {
                                      this.currentUser = user;
                                    });
  }

  ngOnDestroy(): void {
    this.currentUserSubscription.unsubscribe();
  }

  applyInTeam(idTeam: number) {
    if (this.currentUser == null) {
      console.error("User is not connected");
      return;
    }
    this.apiTeamService.applyForTeam(idTeam, this.currentUser.id).subscribe((userResponse) => {
      this.userDataService.setCurrentUser(userResponse);
      this.update();
      }
    );
  }

  isCurrentUserAStudent() {
    if (this.currentUser == null) {
      console.error("User is not connected");
      return false;
    }
    if (this.currentUser.getRoles().includes("STUDENT_ROLE")){
      return true;
    }
    return false; 
  }

  isCurrentUserATeachingStaff() {
    if (this.currentUser == null) {
      console.error("User is not connected");
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
