import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITarefaRecorrenteExecucao, NewTarefaRecorrenteExecucao } from '../tarefa-recorrente-execucao.model';

export type PartialUpdateTarefaRecorrenteExecucao = Partial<ITarefaRecorrenteExecucao> & Pick<ITarefaRecorrenteExecucao, 'id'>;

type RestOf<T extends ITarefaRecorrenteExecucao | NewTarefaRecorrenteExecucao> = Omit<T, 'dataEntrega' | 'dataAgendada'> & {
  dataEntrega?: string | null;
  dataAgendada?: string | null;
};

export type RestTarefaRecorrenteExecucao = RestOf<ITarefaRecorrenteExecucao>;

export type NewRestTarefaRecorrenteExecucao = RestOf<NewTarefaRecorrenteExecucao>;

export type PartialUpdateRestTarefaRecorrenteExecucao = RestOf<PartialUpdateTarefaRecorrenteExecucao>;

export type EntityResponseType = HttpResponse<ITarefaRecorrenteExecucao>;
export type EntityArrayResponseType = HttpResponse<ITarefaRecorrenteExecucao[]>;

@Injectable({ providedIn: 'root' })
export class TarefaRecorrenteExecucaoService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/tarefa-recorrente-execucaos');

  create(tarefaRecorrenteExecucao: NewTarefaRecorrenteExecucao): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tarefaRecorrenteExecucao);
    return this.http
      .post<RestTarefaRecorrenteExecucao>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(tarefaRecorrenteExecucao: ITarefaRecorrenteExecucao): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tarefaRecorrenteExecucao);
    return this.http
      .put<RestTarefaRecorrenteExecucao>(
        `${this.resourceUrl}/${this.getTarefaRecorrenteExecucaoIdentifier(tarefaRecorrenteExecucao)}`,
        copy,
        { observe: 'response' },
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(tarefaRecorrenteExecucao: PartialUpdateTarefaRecorrenteExecucao): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tarefaRecorrenteExecucao);
    return this.http
      .patch<RestTarefaRecorrenteExecucao>(
        `${this.resourceUrl}/${this.getTarefaRecorrenteExecucaoIdentifier(tarefaRecorrenteExecucao)}`,
        copy,
        { observe: 'response' },
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestTarefaRecorrenteExecucao>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestTarefaRecorrenteExecucao[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getTarefaRecorrenteExecucaoIdentifier(tarefaRecorrenteExecucao: Pick<ITarefaRecorrenteExecucao, 'id'>): number {
    return tarefaRecorrenteExecucao.id;
  }

  compareTarefaRecorrenteExecucao(
    o1: Pick<ITarefaRecorrenteExecucao, 'id'> | null,
    o2: Pick<ITarefaRecorrenteExecucao, 'id'> | null,
  ): boolean {
    return o1 && o2 ? this.getTarefaRecorrenteExecucaoIdentifier(o1) === this.getTarefaRecorrenteExecucaoIdentifier(o2) : o1 === o2;
  }

  addTarefaRecorrenteExecucaoToCollectionIfMissing<Type extends Pick<ITarefaRecorrenteExecucao, 'id'>>(
    tarefaRecorrenteExecucaoCollection: Type[],
    ...tarefaRecorrenteExecucaosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const tarefaRecorrenteExecucaos: Type[] = tarefaRecorrenteExecucaosToCheck.filter(isPresent);
    if (tarefaRecorrenteExecucaos.length > 0) {
      const tarefaRecorrenteExecucaoCollectionIdentifiers = tarefaRecorrenteExecucaoCollection.map(tarefaRecorrenteExecucaoItem =>
        this.getTarefaRecorrenteExecucaoIdentifier(tarefaRecorrenteExecucaoItem),
      );
      const tarefaRecorrenteExecucaosToAdd = tarefaRecorrenteExecucaos.filter(tarefaRecorrenteExecucaoItem => {
        const tarefaRecorrenteExecucaoIdentifier = this.getTarefaRecorrenteExecucaoIdentifier(tarefaRecorrenteExecucaoItem);
        if (tarefaRecorrenteExecucaoCollectionIdentifiers.includes(tarefaRecorrenteExecucaoIdentifier)) {
          return false;
        }
        tarefaRecorrenteExecucaoCollectionIdentifiers.push(tarefaRecorrenteExecucaoIdentifier);
        return true;
      });
      return [...tarefaRecorrenteExecucaosToAdd, ...tarefaRecorrenteExecucaoCollection];
    }
    return tarefaRecorrenteExecucaoCollection;
  }

  protected convertDateFromClient<
    T extends ITarefaRecorrenteExecucao | NewTarefaRecorrenteExecucao | PartialUpdateTarefaRecorrenteExecucao,
  >(tarefaRecorrenteExecucao: T): RestOf<T> {
    return {
      ...tarefaRecorrenteExecucao,
      dataEntrega: tarefaRecorrenteExecucao.dataEntrega?.toJSON() ?? null,
      dataAgendada: tarefaRecorrenteExecucao.dataAgendada?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restTarefaRecorrenteExecucao: RestTarefaRecorrenteExecucao): ITarefaRecorrenteExecucao {
    return {
      ...restTarefaRecorrenteExecucao,
      dataEntrega: restTarefaRecorrenteExecucao.dataEntrega ? dayjs(restTarefaRecorrenteExecucao.dataEntrega) : undefined,
      dataAgendada: restTarefaRecorrenteExecucao.dataAgendada ? dayjs(restTarefaRecorrenteExecucao.dataAgendada) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestTarefaRecorrenteExecucao>): HttpResponse<ITarefaRecorrenteExecucao> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestTarefaRecorrenteExecucao[]>): HttpResponse<ITarefaRecorrenteExecucao[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
