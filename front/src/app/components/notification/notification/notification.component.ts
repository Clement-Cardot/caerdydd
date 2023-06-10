import { Component, OnInit, OnDestroy ,ViewChild  } from '@angular/core';
import { MatMenuTrigger } from '@angular/material/menu';
import { Notification } from 'src/app/core/data/models/notification.model';
import { User } from 'src/app/core/data/models/user.model';
import { ApiNotificationService } from 'src/app/core/services/api-notification.service';
import { UserDataService } from 'src/app/core/services/user-data.service';

@Component({
  selector: 'app-notification',
  templateUrl: './notification.component.html',
  styleUrls: ['./notification.component.scss'],
})
export class NotificationComponent implements OnInit, OnDestroy {

  @ViewChild(MatMenuTrigger) trigger!: MatMenuTrigger;
  notifications: Notification[] = [];
  currentUser: User | null = null;

  refresh: any;

  constructor(
    private apiNotificationService: ApiNotificationService,
    private userDataService: UserDataService
  ) {}
  

  ngOnInit(): void {
    this.userDataService.getCurrentUser().subscribe((user: User | null) => {
      this.currentUser = user;
      if (this.currentUser) {
        let idUser = this.currentUser.id;
        this.getNotifications(idUser);
        this.refresh = setInterval(() => { this.getNotifications(idUser) },  5000 );
      }
    });
  }

  ngOnDestroy(): void {
    clearInterval(this.refresh);
  }

  getNotifications(userId: number): void {
    this.apiNotificationService.getNotificationsByUserId(userId).subscribe(
      (notifications) => {
        this.notifications = notifications;
      },
      (error) => {
        console.error(error);
      }
    );
  }
  

  markAsRead(notification: Notification): void {
    this.apiNotificationService.changeStatusRead(notification.idNotification).subscribe(
      (updatedNotification) => {
        const index = this.notifications.findIndex(n => n.idNotification === updatedNotification.idNotification);
        if (index !== -1) {
          this.notifications[index] = updatedNotification;
        }
      },
      (error) => {
        console.error(error);
      }
    );
  }

  unreadNotifications(): number {
    return this.notifications.filter(n => !n.isRead).length;
  }

  closeMenu() {
    this.trigger.closeMenu();
  }
  
}
