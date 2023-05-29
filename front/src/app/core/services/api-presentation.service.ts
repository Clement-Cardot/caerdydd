import { Injectable } from "@angular/core";
import { HttpClient, HttpErrorResponse } from "@angular/common/http";
import { Observable } from "rxjs/internal/Observable";
import { catchError } from "rxjs/internal/operators/catchError";
import { throwError } from "rxjs/internal/observable/throwError";
import { map } from "rxjs/operators";
import { Presentation, PresentationAdapter, PresentationPayload } from "../data/models/presentation.model";
import { environment } from "src/environments/environment";

@Injectable({
    providedIn: "root"
})
export class ApiPresentationService {
    private baseUrl = environment.apiURL + "/presentation";

    constructor(
        private http: HttpClient, 
        private presentationAdapter: PresentationAdapter
    ) {
    }

    getPresentationById(presentationId: number): Observable<Presentation> {
        const url = `${this.baseUrl}/${presentationId}`;
        return this.http.get<any>(url)
        .pipe(
            map((data: any) => this.presentationAdapter.adapt(data)),
            catchError(this.handleError)
        );
    }

    createPresentation(presentation: PresentationPayload): Observable<Presentation> {
        const url = `${this.baseUrl}`;
        return this.http.put<any>(url, presentation)
            .pipe(
                map((data: any) => this.presentationAdapter.adapt(data)),
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
        return throwError({ status: error.status, message: 'Something bad happened; please try again later.' });
    }
    
}
