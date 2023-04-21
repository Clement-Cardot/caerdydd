import { Injectable } from "@angular/core";
import { Project } from "../../core/data/models/project.model";

@Injectable({
    providedIn: 'root'
})
export class ProjectService {
    boxValidateSubject: Project[] = [
        {
            team: "Team 1",
            subject: "Emergency Management Assisant",
            description: "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed euismod, nunc sit amet ultricies lacinia, nunc nisl aliquam mauris, vitae ultricies nisl nunc eget nisl. Sed euismod, nunc sit amet ultricies lacinia, nunc nisl aliquam mauris, vitae ultricies nisl nunc eget nisl.",
            is_validated: true
        },
        {
            team: "Team 2",
            subject: "Subject 2",
            description: "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed euismod, nunc sit amet ultricies lacinia, nunc nisl aliquam mauris, vitae ultricies nisl nunc eget nisl. Sed euismod, nunc sit amet ultricies lacinia, nunc nisl aliquam mauris, vitae ultricies nisl nunc eget nisl.",
            is_validated: false
        },
        {
            team: "Team 3",
            subject: "Subject 3",
            description: "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed euismod, nunc sit amet ultricies lacinia, nunc nisl aliquam mauris, vitae ultricies nisl nunc eget nisl. Sed euismod, nunc sit amet ultricies lacinia, nunc nisl aliquam mauris, vitae ultricies nisl nunc eget nisl.",
            is_validated: false
        },
        {
            team: "Team 4",
            subject: "Subject 4",
            description: "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed euismod, nunc sit amet ultricies lacinia, nunc nisl aliquam mauris, vitae ultricies nisl nunc eget nisl. Sed euismod, nunc sit amet ultricies lacinia, nunc nisl aliquam mauris, vitae ultricies nisl nunc eget nisl.",
            is_validated: false
        },
        {
            team: "Team 5",
            subject: "Subject 5",
            description: "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed euismod, nunc sit amet ultricies lacinia, nunc nisl aliquam mauris, vitae ultricies nisl nunc eget nisl. Sed euismod, nunc sit amet ultricies lacinia, nunc nisl aliquam mauris, vitae ultricies nisl nunc eget nisl.",
            is_validated: false
        }
    ]

    getAllValidateSubject(): Project[] {
        return this.boxValidateSubject;
    }
}