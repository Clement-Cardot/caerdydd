import { Adapter } from "../adapter";
import { Injectable } from "@angular/core";
import { User, UserAdapter } from "./user.model";

export class Notification {
    constructor(
        public idNotification: number,
        public message: string,
        public link: string,
        public user: User,
        public isRead: boolean,
    ) { }
}

@Injectable({
    providedIn: 'root'
})
export class NotificationAdapter implements Adapter<Notification> {

    constructor(private userAdapter: UserAdapter) {}

    adapt(item: any): Notification {
        return new Notification(
            item.idNotification,
            item.message,
            item.link,
            this.userAdapter.adapt(item.user),
            item.isRead,
        );
    }
}
