import { IUsuarioEmpresa } from 'app/entities/usuario-empresa/usuario-empresa.model';
import { IUsuarioContador } from 'app/entities/usuario-contador/usuario-contador.model';
import { ICriterioAvaliacaoAtor } from 'app/entities/criterio-avaliacao-ator/criterio-avaliacao-ator.model';
import { IOrdemServico } from 'app/entities/ordem-servico/ordem-servico.model';

export interface IFeedBackUsuarioParaContador {
  id: number;
  comentario?: string | null;
  pontuacao?: number | null;
  usuarioEmpresa?: IUsuarioEmpresa | null;
  usuarioContador?: IUsuarioContador | null;
  criterioAvaliacaoAtor?: ICriterioAvaliacaoAtor | null;
  ordemServico?: IOrdemServico | null;
}

export type NewFeedBackUsuarioParaContador = Omit<IFeedBackUsuarioParaContador, 'id'> & { id: null };
