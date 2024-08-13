import { ISubclasseCnae } from 'app/entities/subclasse-cnae/subclasse-cnae.model';
import { IRamo } from 'app/entities/ramo/ramo.model';
import { IEmpresa } from 'app/entities/empresa/empresa.model';
import { IEmpresaModelo } from 'app/entities/empresa-modelo/empresa-modelo.model';
import { TipoSegmentoEnum } from 'app/entities/enumerations/tipo-segmento-enum.model';

export interface ISegmentoCnae {
  id: number;
  nome?: string | null;
  descricao?: string | null;
  icon?: string | null;
  imagem?: string | null;
  tags?: string | null;
  tipo?: keyof typeof TipoSegmentoEnum | null;
  subclasseCnaes?: ISubclasseCnae[] | null;
  ramo?: IRamo | null;
  empresas?: IEmpresa[] | null;
  empresaModelos?: IEmpresaModelo[] | null;
}

export type NewSegmentoCnae = Omit<ISegmentoCnae, 'id'> & { id: null };
