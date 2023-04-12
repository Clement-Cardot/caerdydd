import { Injectable } from "@angular/core";
import { User, UserAdapter } from "../data/models/user.model";
import { Role } from "../data/models/role.model";

@Injectable()
export class UserDataService {

    constructor(
        private userAdapter: UserAdapter
    ) {}

    // Getters and setters
    public getCurrentUser(): User | null{
        let data = localStorage.getItem("currentUser");
        if (data == null) {
            return null;
        }
        return this.userAdapter.adapt(JSON.parse(data));
    }

    public setCurrentUser(user: User): void {
        this.clearCurrentUser();
        localStorage.setItem("currentUser", JSON.stringify(user));
    }

    public getCurrentUserRoles(): Role[] | null{
        let currentUser = this.getCurrentUser();
        if (currentUser == null) {
            return null;
        }
        return currentUser.roles;
    }

    public isLoggedIn(): boolean {
        let currentUser = this.getCurrentUser();
        if (currentUser == null) {
            return false;
        }
        return currentUser.id != null;
    }

    public clearCurrentUser(): void {
        localStorage.removeItem("currentUser");
    }

    public cleanAllData(): void {
        localStorage.clear();
    }
}