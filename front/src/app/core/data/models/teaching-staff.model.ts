import { User, UserAdapter } from './user.model';
import { Injectable } from '@angular/core';
import { Adapter } from '../adapter';

export class TeachingStaff {
  constructor(
    public user: User,
    public idUser: number,
    public isInfrastructureSpecialist: boolean,
    public isDevelopmentSpecialist: boolean,
    public isModelingSpecialist: boolean,
    public isOptionLeader: boolean,
    public isSubjectValidator: boolean
  ) {}
}

@Injectable({
  providedIn: 'root',
})
export class TeachingStaffAdapter implements Adapter<TeachingStaff> {
  constructor(private userAdapter: UserAdapter) {}

  adapt(item: any): TeachingStaff {
    return new TeachingStaff(
      this.userAdapter.adapt(item.user),
      item.idUser,
      item.isInfrastructureSpecialist,
      item.isDevelopmentSpecialist,
      item.isModelingSpecialist,
      item.isOptionLeader,
      item.isSubjectValidator
    );
  }
}
