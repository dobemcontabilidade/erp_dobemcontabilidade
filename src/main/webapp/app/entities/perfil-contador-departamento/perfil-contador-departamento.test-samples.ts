import { IPerfilContadorDepartamento, NewPerfilContadorDepartamento } from './perfil-contador-departamento.model';

export const sampleWithRequiredData: IPerfilContadorDepartamento = {
  id: 1290,
};

export const sampleWithPartialData: IPerfilContadorDepartamento = {
  id: 20674,
  quantidadeEmpresas: 19655,
};

export const sampleWithFullData: IPerfilContadorDepartamento = {
  id: 5617,
  quantidadeEmpresas: 18282,
  percentualExperiencia: 13382.28,
};

export const sampleWithNewData: NewPerfilContadorDepartamento = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
