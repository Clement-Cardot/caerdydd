import { Injectable } from "@angular/core";
import { ValidateSubject } from "../models/validate-subjects.model";

@Injectable({
    providedIn: 'root'
})
export class ValidateSubjectService {
    boxValidateSubject: ValidateSubject[] = [
        {
            team: "Team 1",
            subject: "Subject 1",
            is_validated: false
        },
        {
            team: "Team 2",
            subject: "Subject 2",
            is_validated: false
        },
        {
            team: "Team 3",
            subject: "Subject 3",
            is_validated: false
        },
        {
            team: "Team 4",
            subject: "Subject 4",
            is_validated: false
        },
        {
            team: "Team 5",
            subject: "Subject 5",
            is_validated: false
        }
    ]

    getAllValidateSubject(): ValidateSubject[] {
        return this.boxValidateSubject;
    }
}