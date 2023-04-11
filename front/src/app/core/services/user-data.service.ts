import { Injectable } from "@angular/core";
import { User, UserAdapter } from "../data/models/user.model";

@Injectable()
export class UserDataService {

    constructor(
        private userAdapter: UserAdapter
    ) {}

    // Getters and setters
    public getCurrentUser(): User {
        let data = localStorage.getItem("currentUser");
        if (data == null) {
            data = "";
        }
        return this.userAdapter.adapt(JSON.parse(data));
    }

    public setCurrentUser(user: User): void {
        this.clearCurrentUser();
        localStorage.setItem("currentUser", JSON.stringify(user));
    }

    public clearCurrentUser(): void {
        localStorage.removeItem("currentUser");
    }

    public cleanAllData(): void {
        localStorage.clear();
    }
}