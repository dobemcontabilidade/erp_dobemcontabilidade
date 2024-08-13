import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITarefaOrdemServicoExecucao, NewTarefaOrdemServicoExecucao } from '../tarefa-ordem-servico-execucao.model';

export type PartialUpdateTarefaOrdemServicoExecucao = Partial<ITarefaOrdemServicoExecucao> & Pick<ITarefaOrdemServicoExecucao, 'id'>;

type RestOf<T extends ITarefaOrdemServicoExecucao | NewTarefaOrdemServicoExecucao> = Omit<T, 'dataEntrega' | 'dataAgendada'> & {
  dataEntrega?: string | null;
  dataAgendada?: string | null;
};

export type RestTarefaOrdemServicoExecucao = RestOf<ITarefaOrdemServicoExecucao>;

export type NewRestTarefaOrdemServicoExecucao = RestOf<NewTarefaOrdemServicoExecucao>;

export type PartialUpdateRestTarefaOrdemServicoExecucao = RestOf<PartialUpdateTarefaOrdemServicoExecucao>;

export type EntityResponseType = HttpResponse<ITarefaOrdemServicoExecucao>;
export type EntityArrayResponseType = HttpResponse<ITarefaOrdemServicoExecucao[]>;

@Injectable({ providedIn: 'root' })
export class TarefaOrdemServicoExecucaoService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/tarefa-ordem-servico-execucaos');

  create(tarefaOrdemServicoExecucao: NewTarefaOrdemServicoExecucao): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tarefaOrdemServicoExecucao);
    return this.http
      .post<RestTarefaOrdemServicoExecucao>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(tarefaOrdemServicoExecucao: ITarefaOrdemServicoExecucao): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tarefaOrdemServicoExecucao);
    return this.http
      .put<RestTarefaOrdemServicoExecucao>(
        `${this.resourceUrl}/${this.getTarefaOrdemServicoExecucaoIdentifier(tarefaOrdemServicoExecucao)}`,
        copy,
        { observe: 'response' },
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(tarefaOrdemServicoExecucao: PartialUpdateTarefaOrdemServicoExecucao): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tarefaOrdemServicoExecucao);
    return this.http
      .patch<RestTarefaOrdemServicoExecucao>(
        `${this.resourceUrl}/${this.getTarefaOrdemServicoExecucaoIdentifier(tarefaOrdemServicoExecucao)}`,
        copy,
        { observe: 'response' },
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestTarefaOrdemServicoExecucao>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestTarefaOrdemServicoExecucao[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getTarefaOrdemServicoExecucaoIdentifier(tarefaOrdemServicoExecucao: Pick<ITarefaOrdemServicoExecucao, 'id'>): number {
    return tarefaOrdemServicoExecucao.id;
  }

  compareTarefaOrdemServicoExecucao(
    o1: Pick<ITarefaOrdemServicoExecucao, 'id'> | null,
    o2: Pick<ITarefaOrdemServicoExecucao, 'id'> | null,
  ): boolean {
    return o1 && o2 ? this.getTarefaOrdemServicoExecucaoIdentifier(o1) === this.getTarefaOrdemServicoExecucaoIdentifier(o2) : o1 === o2;
  }

  addTarefaOrdemServicoExecucaoToCollectionIfMissing<Type extends Pick<ITarefaOrdemServicoExecucao, 'id'>>(
    tarefaOrdemServicoExecucaoCollection: Type[],
    ...tarefaOrdemServicoExecucaosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const tarefaOrdemServicoExecucaos: Type[] = tarefaOrdemServicoExecucaosToCheck.filter(isPresent);
    if (tarefaOrdemServicoExecucaos.length > 0) {
      const tarefaOrdemServicoExecucaoCollectionIdentifiers = tarefaOrdemServicoExecucaoCollection.map(tarefaOrdemServicoExecucaoItem =>
        this.getTarefaOrdemServicoExecucaoIdentifier(tarefaOrdemServicoExecucaoItem),
      );
      const tarefaOrdemServicoExecucaosToAdd = tarefaOrdemServicoExecucaos.filter(tarefaOrdemServicoExecucaoItem => {
        const tarefaOrdemServicoExecucaoIdentifier = this.getTarefaOrdemServicoExecucaoIdentifier(tarefaOrdemServicoExecucaoItem);
        if (tarefaOrdemServicoExecucaoCollectionIdentifiers.includes(tarefaOrdemServicoExecucaoIdentifier)) {
          return false;
        }
        tarefaOrdemServicoExecucaoCollectionIdentifiers.push(tarefaOrdemServicoExecucaoIdentifier);
        return true;
      });
      return [...tarefaOrdemServicoExecucaosToAdd, ...tarefaOrdemServicoExecucaoCollection];
    }
    return tarefaOrdemServicoExecucaoCollection;
  }

  protected convertDateFromClient<
    T extends ITarefaOrdemServicoExecucao | NewTarefaOrdemServicoExecucao | PartialUpdateTarefaOrdemServicoExecucao,
  >(tarefaOrdemServicoExecucao: T): RestOf<T> {
    return {
      ...tarefaOrdemServicoExecucao,
      dataEntrega: tarefaOrdemServicoExecucao.dataEntrega?.toJSON() ?? null,
      dataAgendada: tarefaOrdemServicoExecucao.dataAgendada?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restTarefaOrdemServicoExecucao: RestTarefaOrdemServicoExecucao): ITarefaOrdemServicoExecucao {
    return {
      ...restTarefaOrdemServicoExecucao,
      dataEntrega: restTarefaOrdemServicoExecucao.dataEntrega ? dayjs(restTarefaOrdemServicoExecucao.dataEntrega) : undefined,
      dataAgendada: restTarefaOrdemServicoExecucao.dataAgendada ? dayjs(restTarefaOrdemServicoExecucao.dataAgendada) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestTarefaOrdemServicoExecucao>): HttpResponse<ITarefaOrdemServicoExecucao> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(
    res: HttpResponse<RestTarefaOrdemServicoExecucao[]>,
  ): HttpResponse<ITarefaOrdemServicoExecucao[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
