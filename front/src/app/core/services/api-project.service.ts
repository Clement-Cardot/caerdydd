import { Injectable } from "@angular/core";
import { HttpClient, HttpErrorResponse } from "@angular/common/http";
import { Observable } from "rxjs/internal/Observable";
import { catchError } from "rxjs/internal/operators/catchError";
import { throwError } from "rxjs/internal/observable/throwError";
import { map } from "rxjs";
import { Project, ProjectAdapter } from "../data/models/project.model";
import { environment } from "src/environments/environment";

@Injectable({
    providedIn: "root"
})
export class ApiProjectService {
    private baseUrl = environment.apiURL + "/projects";

    constructor(
        private http: HttpClient, 
        private projectAdapter: ProjectAdapter
        ) {
    }

    getProjectById(projectId: number): Observable<Project> {
        const url = `${this.baseUrl}/${projectId}`;
        return this.http.get<any>(url)
        .pipe(
            map((data: any) => this.projectAdapter.adapt(data))
        )
        .pipe(
            catchError(this.handleError)
        );
    }
    
    updateProjectDescription(project: Project): Observable<Project> {
        const url = `${this.baseUrl}/description`;
        return this.http.put<any>(url, project)
        .pipe(
            map((data: any) => this.projectAdapter.adapt(data))
        )
        .pipe(
            catchError(this.handleError)
        );
    }

    updateProjectValidation(project: Project): Observable<Project> {
        const url = `${this.baseUrl}/validation`;
        return this.http.put<any>(url, project)
        .pipe(
            map((data: any) => this.projectAdapter.adapt(data))
        )
        .pipe(
            catchError(this.handleError)
        );
    }

    getAllProjects(): Observable<Project[]> {
        return this.http.get<any[]>(this.baseUrl)
        .pipe(
            map((data: any[]) => data.map((item) => this.projectAdapter.adapt(item)))
        )
        .pipe(
            catchError(this.handleError)
        );
    }

    private handleError(error: HttpErrorResponse) {
        if (error.status === 0) {
            console.error('An error occurred:', error.error);
        } else {
            console.error(
              `Backend returned code ${error.status}, body was: `, error.error);
        }
        return throwError(() => new Error('Something bad happened; please try again later.'));
    }
}