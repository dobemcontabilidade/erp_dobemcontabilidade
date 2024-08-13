import { IFuncionario } from 'app/entities/funcionario/funcionario.model';
import { IAgenteIntegracaoEstagio } from 'app/entities/agente-integracao-estagio/agente-integracao-estagio.model';
import { IInstituicaoEnsino } from 'app/entities/instituicao-ensino/instituicao-ensino.model';
import { NaturezaEstagioEnum } from 'app/entities/enumerations/natureza-estagio-enum.model';
import { SituacaoFuncionarioEnum } from 'app/entities/enumerations/situacao-funcionario-enum.model';
import { CategoriaTrabalhadorEnum } from 'app/entities/enumerations/categoria-trabalhador-enum.model';
import { TipoVinculoTrabalhoEnum } from 'app/entities/enumerations/tipo-vinculo-trabalho-enum.model';
import { FgtsOpcaoEnum } from 'app/entities/enumerations/fgts-opcao-enum.model';
import { TipoDocumentoEnum } from 'app/entities/enumerations/tipo-documento-enum.model';
import { PeriodoExperienciaEnum } from 'app/entities/enumerations/periodo-experiencia-enum.model';
import { TipoAdmisaoEnum } from 'app/entities/enumerations/tipo-admisao-enum.model';
import { PeriodoIntermitenteEnum } from 'app/entities/enumerations/periodo-intermitente-enum.model';
import { IndicativoAdmissaoEnum } from 'app/entities/enumerations/indicativo-admissao-enum.model';

export interface IContratoFuncionario {
  id: number;
  salarioFixo?: boolean | null;
  salarioVariavel?: boolean | null;
  estagio?: boolean | null;
  naturezaEstagioEnum?: keyof typeof NaturezaEstagioEnum | null;
  ctps?: string | null;
  serieCtps?: number | null;
  orgaoEmissorDocumento?: string | null;
  dataValidadeDocumento?: string | null;
  dataAdmissao?: string | null;
  cargo?: string | null;
  descricaoAtividades?: string | null;
  situacao?: keyof typeof SituacaoFuncionarioEnum | null;
  valorSalarioFixo?: string | null;
  valorSalarioVariavel?: string | null;
  dataTerminoContrato?: string | null;
  datainicioContrato?: string | null;
  horasATrabalhadar?: number | null;
  codigoCargo?: string | null;
  categoriaTrabalhador?: keyof typeof CategoriaTrabalhadorEnum | null;
  tipoVinculoTrabalho?: keyof typeof TipoVinculoTrabalhoEnum | null;
  fgtsOpcao?: keyof typeof FgtsOpcaoEnum | null;
  tIpoDocumentoEnum?: keyof typeof TipoDocumentoEnum | null;
  periodoExperiencia?: keyof typeof PeriodoExperienciaEnum | null;
  tipoAdmisaoEnum?: keyof typeof TipoAdmisaoEnum | null;
  periodoIntermitente?: keyof typeof PeriodoIntermitenteEnum | null;
  indicativoAdmissao?: keyof typeof IndicativoAdmissaoEnum | null;
  numeroPisNisPasep?: number | null;
  funcionario?: IFuncionario | null;
  agenteIntegracaoEstagio?: IAgenteIntegracaoEstagio | null;
  instituicaoEnsino?: IInstituicaoEnsino | null;
}

export type NewContratoFuncionario = Omit<IContratoFuncionario, 'id'> & { id: null };
