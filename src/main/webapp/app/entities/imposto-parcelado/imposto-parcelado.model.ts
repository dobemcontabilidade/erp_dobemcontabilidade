import { IParcelamentoImposto } from 'app/entities/parcelamento-imposto/parcelamento-imposto.model';
import { IImpostoAPagarEmpresa } from 'app/entities/imposto-a-pagar-empresa/imposto-a-pagar-empresa.model';

export interface IImpostoParcelado {
  id: number;
  diasAtraso?: number | null;
  parcelamentoImposto?: IParcelamentoImposto | null;
  impostoAPagarEmpresa?: IImpostoAPagarEmpresa | null;
}

export type NewImpostoParcelado = Omit<IImpostoParcelado, 'id'> & { id: null };
