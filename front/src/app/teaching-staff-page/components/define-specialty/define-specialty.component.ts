import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-define-specialty',
  templateUrl: './define-specialty.component.html',
  styleUrls: ['./define-specialty.component.scss'],
})
export class DefineSpecialtyComponent implements OnInit {
  ngOptions = [
    { value: 'Infrastructure', viewValue: 'Infrastructure' },
    { value: 'Développement', viewValue: 'Développement' },
    { value: 'Modélisation', viewValue: 'Modélisation' },
  ];

  specialty1!: string;

  specialty2!: string;

  specialty3!: string;

  afficheContenu!: boolean;

  afficheContenu2!: boolean;

  afficheBouton!: boolean;

  afficheBouton2!: boolean;

  ngOnInit(): void {
    this.afficheContenu = false;
    this.afficheContenu2 = false;
    this.afficheBouton = true;
    this.afficheBouton2 = true;
  }

  afficherContenu(): void {
    this.afficheContenu = true;
  }

  afficherContenu2(): void {
    this.afficheContenu2 = true;
  }

  cacherBouton(): void {
    this.afficheBouton = false;
  }

  cacherBouton2(): void {
    this.afficheBouton2 = false;
  }

  onSubmit() {
    if (this.specialty1) {
      console.log('My input: ', this.specialty1);
    }
    if (this.specialty2) {
      console.log('My input: ', this.specialty2);
    }
    if (this.specialty3) {
      console.log('My input: ', this.specialty3);
    }
  }
}
