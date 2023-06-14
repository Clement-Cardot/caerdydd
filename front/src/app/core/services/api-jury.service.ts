import { Injectable } from "@angular/core";
import { HttpClient, HttpErrorResponse } from "@angular/common/http";
import { Observable } from "rxjs/internal/Observable";
import { catchError } from "rxjs/internal/operators/catchError";
import { throwError } from "rxjs/internal/observable/throwError";
import { map } from "rxjs";
import { environment } from "../../../environments/environment";
import { Jury, JuryAdapter } from "../data/models/jury.model";
import { TeachingStaff } from "../data/models/teaching-staff.model";


@Injectable({
    providedIn: "root"
})
export class ApiJuryService {
    private baseUrl = environment.apiURL + "/juries";

    constructor(
        private http: HttpClient,
        private juryAdapter: JuryAdapter
        ) {
    }

    addJury(juryMemberLDId: number, juryMemberCSSId: number): Observable<Jury> {
        const url = `${this.baseUrl}/add/${juryMemberLDId}/${juryMemberCSSId}`;
        return this.http.put(url, null)
        .pipe(
            map((data: any) => this.juryAdapter.adapt(data))
        )
        .pipe(
            catchError(this.handleError)
        );
    }

    getAllJuries(): Observable<Jury[]> {
        return this.http.get<any[]>(this.baseUrl)
            .pipe(
                map(data => {
                    const juries = data.map((item: any) =>
                        this.juryAdapter.adapt(item)
                    );

                    return juries;
                })
            )
            .pipe(
                catchError(this.handleError)
            );
    }

    private handleError(error: HttpErrorResponse) {
        if (error.status === 0) {
            // A client-side or network error occurred. Handle it accordingly.
            console.error('An error occurred:', error.error);
          } else {
            // The backend returned an unsuccessful response code.
            // The response body may contain clues as to what went wrong.
            console.error(
              `Backend returned code ${error.status}, body was: `, error.error);
          }
          // Return an observable with a user-facing error message.
          return throwError(error.status);
        }
}
