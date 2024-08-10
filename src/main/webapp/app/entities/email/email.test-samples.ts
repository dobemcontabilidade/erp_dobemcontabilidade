import { IEmail, NewEmail } from './email.model';

export const sampleWithRequiredData: IEmail = {
  id: 14672,
  email: 'Sirineu.Barros@bol.com.br',
};

export const sampleWithPartialData: IEmail = {
  id: 9607,
  email: 'Natalia_Franco64@gmail.com',
  principal: false,
};

export const sampleWithFullData: IEmail = {
  id: 8646,
  email: 'Liz84@gmail.com',
  principal: false,
};

export const sampleWithNewData: NewEmail = {
  email: 'Breno38@live.com',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
