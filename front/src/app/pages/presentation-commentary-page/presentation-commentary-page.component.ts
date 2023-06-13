import { Component, OnInit } from '@angular/core';
import { Presentation } from 'src/app/core/data/models/presentation.model';
import { ApiPresentationService } from 'src/app/core/services/api-presentation.service';
import { UserDataService } from 'src/app/core/services/user-data.service';

@Component({
  selector: 'app-presentation-commentary',
  templateUrl: './presentation-commentary-page.component.html',
  styleUrls: ['./presentation-commentary-page.component.scss']
})
export class PresentationCommentaryPageComponent implements OnInit {
    constructor(private apiPresentationService: ApiPresentationService,
                private userDataService: UserDataService){}

    presentations !: Presentation[];

    ngOnInit(): void {
      this.getAllData();
    }

    getAllData(): void{
      const currentUser = this.userDataService.getCurrentUser().getValue();
      let userId = 0;

      if (currentUser && typeof currentUser.id === 'number') {
        userId = currentUser.id;
      }

      this.apiPresentationService.getTeachingStaffPresentations(userId)
          .subscribe(data => {
            this.presentations = data;
          }
        );
    }
}
