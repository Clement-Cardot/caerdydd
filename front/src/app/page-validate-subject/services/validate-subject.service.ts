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
        }
    ]

    getAllValidateSubject(): ValidateSubject[] {
        return this.boxValidateSubject;
    }
}