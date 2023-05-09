import { Component } from '@angular/core';
import { ApiJuryService } from 'src/app/core/services/api-jury.service';

@Component({
  selector: 'app-jury-creation',
  templateUrl: './jury-creation.component.html',
  styleUrls: ['./jury-creation.component.scss']
})
export class JuryCreationComponent {
  selectedOption1!: string;
  selectedOption2!: string;

  constructor(private apiJuryService: ApiJuryService){}

  onSubmit(){
    const option1 = parseInt(this.selectedOption1, 10);
    const option2 = parseInt(this.selectedOption2, 10);

    this.apiJuryService.addJury(option1, option2).subscribe((response) => {
      console.log(response);
      });
		console.log("Jury " + option1 + " and " + option2 + " created.");
	}
}
