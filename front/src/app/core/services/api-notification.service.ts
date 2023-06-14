import { Injectable } from "@angular/core";
import { HttpClient, HttpErrorResponse } from "@angular/common/http";
import { Observable } from "rxjs/internal/Observable";
import { catchError } from "rxjs/internal/operators/catchError";
import { throwError } from "rxjs/internal/observable/throwError";
import { map } from "rxjs/operators";
import { Notification, NotificationAdapter} from "../data/models/notification.model";
import { environment } from "src/environments/environment";

@Injectable({
    providedIn: "root"
})
export class ApiNotificationService {
    private baseUrl = environment.apiURL + "/notification";

    constructor(
        private http: HttpClient, 
        private notificationAdapter: NotificationAdapter
    ) {
    }

    getNotificationsByUserId(userId: number): Observable<Notification[]> {
        const url = `${this.baseUrl}/user/${userId}`;
        return this.http.get<any[]>(url)
        .pipe(
            map((data: any[]) => data.map(item => this.notificationAdapter.adapt(item))),
            catchError(this.handleError)
        );
    }

    getAllNotifications(): Observable<Notification[]> {
        const url = `${this.baseUrl}/all`;
        return this.http.get<any[]>(url)
        .pipe(
            map((data: any[]) => data.map(item => this.notificationAdapter.adapt(item))),
            catchError(this.handleError)
        );
    }

    createNotification(notification: Notification): Observable<Notification> {
        const url = `${this.baseUrl}/create`;
        return this.http.post<any>(url, notification)
        .pipe(
            map((data: any) => this.notificationAdapter.adapt(data)),
            catchError(this.handleError)
        );
    }

    changeStatusRead(notificationId: number): Observable<Notification> {
        const url = `${this.baseUrl}/read/${notificationId}`;
        return this.http.put<any>(url, {})
        .pipe(
            map((data: any) => this.notificationAdapter.adapt(data)),
            catchError(this.handleError)
        );
    }

    private handleError(error: HttpErrorResponse) {
        if (error.status === 0) {
            console.error('An error occurred:', error.error);
        } else {
            console.error(`Backend returned code ${error.status}, body was: `, error.error);
        }
        return throwError({ status: error.status, message: 'Something bad happened; please try again later.' });
    }
}
