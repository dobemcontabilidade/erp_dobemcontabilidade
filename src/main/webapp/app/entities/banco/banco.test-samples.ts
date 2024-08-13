import { IBanco, NewBanco } from './banco.model';

export const sampleWithRequiredData: IBanco = {
  id: 20855,
  nome: 'forenenst behave',
  codigo: 'absent after',
};

export const sampleWithPartialData: IBanco = {
  id: 31871,
  nome: 'unlike probable winds',
  codigo: 'why abaft go',
};

export const sampleWithFullData: IBanco = {
  id: 24076,
  nome: 'yippee ouch burial',
  codigo: 'than',
};

export const sampleWithNewData: NewBanco = {
  nome: 'once instructive',
  codigo: 'aha atop bend',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
