import { ICriterioAvaliacaoAtor } from 'app/entities/criterio-avaliacao-ator/criterio-avaliacao-ator.model';
import { IUsuarioEmpresa } from 'app/entities/usuario-empresa/usuario-empresa.model';
import { IContador } from 'app/entities/contador/contador.model';
import { IOrdemServico } from 'app/entities/ordem-servico/ordem-servico.model';

export interface IFeedBackContadorParaUsuario {
  id: number;
  comentario?: string | null;
  pontuacao?: number | null;
  criterioAvaliacaoAtor?: ICriterioAvaliacaoAtor | null;
  usuarioEmpresa?: IUsuarioEmpresa | null;
  contador?: IContador | null;
  ordemServico?: IOrdemServico | null;
}

export type NewFeedBackContadorParaUsuario = Omit<IFeedBackContadorParaUsuario, 'id'> & { id: null };
