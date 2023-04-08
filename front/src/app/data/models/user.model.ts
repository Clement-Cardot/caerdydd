export class User {
    public id!: number;
    public firstName!: string;
    public lastName!: string;
    public login!: string;
    public password!: string;
    public email!: string;
    public speciality!: string;
    public roles!: string[];

    // Constructor
    constructor(id: number, firstName: string, lastName: string, login: string, password: string, email: string, speciality: string, roles: string[]) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
        this.email = email;
        this.speciality = speciality;
        this.roles = roles;
    }
}