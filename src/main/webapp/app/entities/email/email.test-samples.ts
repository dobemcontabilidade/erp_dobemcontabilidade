import { IEmail, NewEmail } from './email.model';

export const sampleWithRequiredData: IEmail = {
  id: 21191,
  email: 'Isaac_Franco@live.com',
};

export const sampleWithPartialData: IEmail = {
  id: 26316,
  email: 'Gabriel23@live.com',
  principal: true,
};

export const sampleWithFullData: IEmail = {
  id: 26898,
  email: 'Lorenzo_Franco@bol.com.br',
  principal: true,
};

export const sampleWithNewData: NewEmail = {
  email: 'Frederico6@bol.com.br',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
