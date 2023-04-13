import { Injectable } from "@angular/core";
import { User, UserAdapter } from "../data/models/user.model";
import { Role } from "../data/models/role.model";
import { Observable } from "rxjs/internal/Observable";

@Injectable()
export class UserDataService {
    currentUser$!: Observable<User>;
    currentUserRole$!: Observable<string[]>

    constructor(
        private userAdapter: UserAdapter
    ) {}

    // Getters and setters
    public getCurrentUser(): Observable<User> | null{
        if (this.currentUser$ != null){
            return this.currentUser$;
        }
        else {
            let data = localStorage.getItem("currentUser");
            if (data == null) {
                return null;
            }
            this.setCurrentUser(this.userAdapter.adapt(JSON.parse(data)));            
            return this.currentUser$;
        }
    }

    public setCurrentUser(user: User): void {
        this.clearCurrentUser();

        this.currentUser$ = new Observable(observer => {
            observer.next(user);
            observer.complete();
        });

        this.currentUserRole$ = new Observable(observer => {
            observer.next(user.roles.map((role: Role) => role.role));
            observer.complete();
        });

        localStorage.setItem("currentUser", JSON.stringify(user));
    }

    public getCurrentUserRoles(): Observable<string[]> | null{
        let currentUser = this.getCurrentUser();
        if (currentUser == null) {
            return null;
        }
        return this.currentUserRole$;
    }

    public isLoggedIn(): boolean {
        let currentUser = this.getCurrentUser();
        if (currentUser == null) {
            return false;
        }
        return this.getCurrentUser != null;
    }

    public clearCurrentUser(): void {
        localStorage.removeItem("currentUser");
    }

    public cleanAllData(): void {
        localStorage.clear();
    }
}