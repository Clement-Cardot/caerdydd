import { Student } from "./student-leader-mark.model";

export interface Team {
	id: number;
	name: string;
	teamMark: number;
	validationMark: number;
	students : Student[];
}