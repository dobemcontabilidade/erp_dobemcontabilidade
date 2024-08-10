import { IPerfilContadorDepartamento, NewPerfilContadorDepartamento } from './perfil-contador-departamento.model';

export const sampleWithRequiredData: IPerfilContadorDepartamento = {
  id: 16606,
};

export const sampleWithPartialData: IPerfilContadorDepartamento = {
  id: 18266,
  quantidadeEmpresas: 9486,
};

export const sampleWithFullData: IPerfilContadorDepartamento = {
  id: 2002,
  quantidadeEmpresas: 17002,
  percentualExperiencia: 20366.33,
};

export const sampleWithNewData: NewPerfilContadorDepartamento = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
