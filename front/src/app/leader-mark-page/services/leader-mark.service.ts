import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Observable, forkJoin, of } from 'rxjs';
import { catchError, map } from 'rxjs/operators';

import { Student } from '../models/student-leader-mark.model';
import { Team } from '../models/team-leader-mark.model';

@Injectable({
  providedIn: 'root'
})
export class StudentService {
  static getStudents: any;
	static getTeamById: any;

  constructor(private http: HttpClient) { }

  // C'est un peu de la merde toutes ces méthodes, mais sans la back c'est dure de se projeter

  getTeamById(id: number): Observable<Team | null> {
  const url = `http://localhost:4200/api/team/${id}`;
  return this.http.get<Team>(url).pipe(
    catchError((error: HttpErrorResponse) => {
      console.error(error);
      return of(null);
    })
  );
  }

  getStudentsByTeam(team : Team): Observable<Student[]> {
    return this.http.get<Student[]>('http://localhost:8080/api/team/')
      .pipe(
        map((studentsData: Student[]) => studentsData.map((student: Student) => ({
          id: student.id,
          name: student.name,
          firstname: student.firstname,
          individualMark: student.individualMark,
          teamMark: team.teamMark,
          validationMark: team.validationMark,
          bonusMalus: student.bonusMalus,
          finalMark: 0,
        })))
      );
  }

  putStudents(students: Student[]): Observable<Student[]> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    const url = 'http://localhost:8080/api/students/';
  
    // itérer sur le tableau de students et exécuter une requête PUT pour chaque étudiant
    const requests = students.map(student => this.http.put<Student>(url + student.id, student, { headers }));
  
    // combiner les résultats de toutes les requêtes en un seul Observable
    return forkJoin(requests);
  }
  
}
