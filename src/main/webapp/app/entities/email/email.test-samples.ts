import { IEmail, NewEmail } from './email.model';

export const sampleWithRequiredData: IEmail = {
  id: 25213,
  email: 'Isaac_Martins@bol.com.br',
};

export const sampleWithPartialData: IEmail = {
  id: 5275,
  email: 'Lucas.Macedo@bol.com.br',
  principal: true,
};

export const sampleWithFullData: IEmail = {
  id: 26798,
  email: 'Felix3@yahoo.com',
  principal: true,
};

export const sampleWithNewData: NewEmail = {
  email: 'Pablo_Macedo97@gmail.com',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
