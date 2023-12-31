import { Injectable } from "@angular/core";
import { User, UserAdapter } from "../data/models/user.model";
import { BehaviorSubject } from "rxjs";

@Injectable()
export class UserDataService {
    currentUser: BehaviorSubject<User | undefined> = new BehaviorSubject<User | undefined>(undefined);

    constructor(
        private userAdapter: UserAdapter
    ) {}

    // Getters and setters
    public getCurrentUser(): BehaviorSubject<User | undefined> {
        let data = localStorage.getItem("currentUser");
        if (data == null) {
            return this.currentUser;
        }
        this.setCurrentUser(this.userAdapter.adapt(JSON.parse(data)));            
        return this.currentUser;
    }

    public setCurrentUser(user: User): void {
        this.currentUser.next(user);
        localStorage.setItem("currentUser", JSON.stringify(user));
    }

    public isLoggedIn(): boolean {
        return this.currentUser.value != null;
    }

    public clearCurrentUser(): void {
        this.currentUser.next(undefined);
        localStorage.removeItem("currentUser");
    }

    public cleanAllData(): void {
        localStorage.clear();
    }
}