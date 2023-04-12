import { Student } from "./student-leader-mark.model";

export interface Team {
	idTeam: number;
	name: string;
	students : Student[];
}