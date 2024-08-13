import { ITarefaRecorrenteEmpresaModelo, NewTarefaRecorrenteEmpresaModelo } from './tarefa-recorrente-empresa-modelo.model';

export const sampleWithRequiredData: ITarefaRecorrenteEmpresaModelo = {
  id: 15957,
};

export const sampleWithPartialData: ITarefaRecorrenteEmpresaModelo = {
  id: 17750,
  mesLegal: 'JULHO',
  recorrencia: 'SEMESTRAL',
};

export const sampleWithFullData: ITarefaRecorrenteEmpresaModelo = {
  id: 5531,
  diaAdmin: 4736,
  mesLegal: 'MARCO',
  recorrencia: 'SEMANAL',
};

export const sampleWithNewData: NewTarefaRecorrenteEmpresaModelo = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
