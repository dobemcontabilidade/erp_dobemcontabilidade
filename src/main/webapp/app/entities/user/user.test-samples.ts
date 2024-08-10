import { IUser } from './user.model';

export const sampleWithRequiredData: IUser = {
  id: 22157,
  login: 'eH',
};

export const sampleWithPartialData: IUser = {
  id: 21961,
  login: 'I',
};

export const sampleWithFullData: IUser = {
  id: 12742,
  login: 'C--fZ@GQ',
};
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
