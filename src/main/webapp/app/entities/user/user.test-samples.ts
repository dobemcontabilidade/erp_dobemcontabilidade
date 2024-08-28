import { IUser } from './user.model';

export const sampleWithRequiredData: IUser = {
  id: 'a8382a91-267a-46ff-81b3-8a09f1592ee1',
  login: 'Qiiim4',
};

export const sampleWithPartialData: IUser = {
  id: '901687ee-2a39-41d1-9e7f-1d2b8bae933f',
  login: '1A3={@Byhv\\IlbowA\\ej',
};

export const sampleWithFullData: IUser = {
  id: '599c18b6-a326-4f28-92b8-f6e805a69acd',
  login: 'sFy@Lm',
};
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
