export interface StudentsList {
    id: number;
    name: string;
    surname: string;
    speciality: string;
  }

export class TeamList {
    id!: number;
    teamName!: string;
    displayedColumns: string[] = ['id', 'name', 'surname', 'speciality'];
    dataSource!: StudentsList[];
}