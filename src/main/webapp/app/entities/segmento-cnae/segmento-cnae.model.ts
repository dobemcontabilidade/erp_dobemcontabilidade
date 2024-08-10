import { ISubclasseCnae } from 'app/entities/subclasse-cnae/subclasse-cnae.model';
import { IRamo } from 'app/entities/ramo/ramo.model';
import { IEmpresa } from 'app/entities/empresa/empresa.model';
import { TipoSegmentoEnum } from 'app/entities/enumerations/tipo-segmento-enum.model';

export interface ISegmentoCnae {
  id: number;
  nome?: string | null;
  descricao?: string | null;
  icon?: string | null;
  imagem?: string | null;
  tags?: string | null;
  tipo?: keyof typeof TipoSegmentoEnum | null;
  subclasseCnaes?: Pick<ISubclasseCnae, 'id'>[] | null;
  ramo?: Pick<IRamo, 'id' | 'nome'> | null;
  empresas?: Pick<IEmpresa, 'id'>[] | null;
}

export type NewSegmentoCnae = Omit<ISegmentoCnae, 'id'> & { id: null };
