import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Observable, forkJoin, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';
import { Team } from '../models/team-leader-mark.model';

export interface User {
  id: number;
  firstname: string;
  lastname: string;
}

export interface TeamMember {
  user: User;
  individualMark: number;
  bonusPenalty: number;
}

@Injectable({
  providedIn: 'root'
})
export class StudentService {
  private apiBaseUrl = 'http://localhost:8080/api/teams/1';

  constructor(private http: HttpClient) {}

  async getTeam(id: number): Promise<Team[]> {
    const apiUrl = 'http://localhost:4200/api/teams/1';
  
    try {
      const response = await fetch(apiUrl);
      const data = await response.json();
      console.log(data);
      return data;
    } catch (error) {
      console.error(error);
      return [];
    }
  }
  
}
