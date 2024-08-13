import { IServicoContabilEmpresaModelo, NewServicoContabilEmpresaModelo } from './servico-contabil-empresa-modelo.model';

export const sampleWithRequiredData: IServicoContabilEmpresaModelo = {
  id: 13979,
};

export const sampleWithPartialData: IServicoContabilEmpresaModelo = {
  id: 24114,
  obrigatorio: false,
};

export const sampleWithFullData: IServicoContabilEmpresaModelo = {
  id: 12073,
  obrigatorio: false,
};

export const sampleWithNewData: NewServicoContabilEmpresaModelo = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
