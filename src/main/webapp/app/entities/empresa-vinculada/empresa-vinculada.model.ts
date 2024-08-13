import { IFuncionario } from 'app/entities/funcionario/funcionario.model';
import { RegimePrevidenciarioEnum } from 'app/entities/enumerations/regime-previdenciario-enum.model';
import { UnidadePagamentoSalarioEnum } from 'app/entities/enumerations/unidade-pagamento-salario-enum.model';
import { JornadaEspecialEnum } from 'app/entities/enumerations/jornada-especial-enum.model';
import { TipoInscricaoEmpresaVinculadaEnum } from 'app/entities/enumerations/tipo-inscricao-empresa-vinculada-enum.model';
import { TipoContratoTrabalhoEnum } from 'app/entities/enumerations/tipo-contrato-trabalho-enum.model';
import { TipoRegimeTrabalhoEnum } from 'app/entities/enumerations/tipo-regime-trabalho-enum.model';
import { DiasDaSemanaEnum } from 'app/entities/enumerations/dias-da-semana-enum.model';
import { TipoJornadaEmpresaVinculadaEnum } from 'app/entities/enumerations/tipo-jornada-empresa-vinculada-enum.model';

export interface IEmpresaVinculada {
  id: number;
  nomeEmpresa?: string | null;
  cnpj?: string | null;
  remuneracaoEmpresa?: string | null;
  observacoes?: string | null;
  salarioFixo?: boolean | null;
  salarioVariavel?: boolean | null;
  valorSalarioFixo?: string | null;
  dataTerminoContrato?: string | null;
  numeroInscricao?: number | null;
  codigoLotacao?: number | null;
  descricaoComplementar?: string | null;
  descricaoCargo?: string | null;
  observacaoJornadaTrabalho?: string | null;
  mediaHorasTrabalhadasSemana?: number | null;
  regimePrevidenciario?: keyof typeof RegimePrevidenciarioEnum | null;
  unidadePagamentoSalario?: keyof typeof UnidadePagamentoSalarioEnum | null;
  jornadaEspecial?: keyof typeof JornadaEspecialEnum | null;
  tipoInscricaoEmpresaVinculada?: keyof typeof TipoInscricaoEmpresaVinculadaEnum | null;
  tipoContratoTrabalho?: keyof typeof TipoContratoTrabalhoEnum | null;
  tipoRegimeTrabalho?: keyof typeof TipoRegimeTrabalhoEnum | null;
  diasDaSemana?: keyof typeof DiasDaSemanaEnum | null;
  tipoJornadaEmpresaVinculada?: keyof typeof TipoJornadaEmpresaVinculadaEnum | null;
  funcionario?: IFuncionario | null;
}

export type NewEmpresaVinculada = Omit<IEmpresaVinculada, 'id'> & { id: null };
