import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAgendaTarefaRecorrenteExecucao, NewAgendaTarefaRecorrenteExecucao } from '../agenda-tarefa-recorrente-execucao.model';

export type PartialUpdateAgendaTarefaRecorrenteExecucao = Partial<IAgendaTarefaRecorrenteExecucao> &
  Pick<IAgendaTarefaRecorrenteExecucao, 'id'>;

type RestOf<T extends IAgendaTarefaRecorrenteExecucao | NewAgendaTarefaRecorrenteExecucao> = Omit<T, 'horaInicio' | 'horaFim'> & {
  horaInicio?: string | null;
  horaFim?: string | null;
};

export type RestAgendaTarefaRecorrenteExecucao = RestOf<IAgendaTarefaRecorrenteExecucao>;

export type NewRestAgendaTarefaRecorrenteExecucao = RestOf<NewAgendaTarefaRecorrenteExecucao>;

export type PartialUpdateRestAgendaTarefaRecorrenteExecucao = RestOf<PartialUpdateAgendaTarefaRecorrenteExecucao>;

export type EntityResponseType = HttpResponse<IAgendaTarefaRecorrenteExecucao>;
export type EntityArrayResponseType = HttpResponse<IAgendaTarefaRecorrenteExecucao[]>;

@Injectable({ providedIn: 'root' })
export class AgendaTarefaRecorrenteExecucaoService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/agenda-tarefa-recorrente-execucaos');

  create(agendaTarefaRecorrenteExecucao: NewAgendaTarefaRecorrenteExecucao): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(agendaTarefaRecorrenteExecucao);
    return this.http
      .post<RestAgendaTarefaRecorrenteExecucao>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(agendaTarefaRecorrenteExecucao: IAgendaTarefaRecorrenteExecucao): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(agendaTarefaRecorrenteExecucao);
    return this.http
      .put<RestAgendaTarefaRecorrenteExecucao>(
        `${this.resourceUrl}/${this.getAgendaTarefaRecorrenteExecucaoIdentifier(agendaTarefaRecorrenteExecucao)}`,
        copy,
        { observe: 'response' },
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(agendaTarefaRecorrenteExecucao: PartialUpdateAgendaTarefaRecorrenteExecucao): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(agendaTarefaRecorrenteExecucao);
    return this.http
      .patch<RestAgendaTarefaRecorrenteExecucao>(
        `${this.resourceUrl}/${this.getAgendaTarefaRecorrenteExecucaoIdentifier(agendaTarefaRecorrenteExecucao)}`,
        copy,
        { observe: 'response' },
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestAgendaTarefaRecorrenteExecucao>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestAgendaTarefaRecorrenteExecucao[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAgendaTarefaRecorrenteExecucaoIdentifier(agendaTarefaRecorrenteExecucao: Pick<IAgendaTarefaRecorrenteExecucao, 'id'>): number {
    return agendaTarefaRecorrenteExecucao.id;
  }

  compareAgendaTarefaRecorrenteExecucao(
    o1: Pick<IAgendaTarefaRecorrenteExecucao, 'id'> | null,
    o2: Pick<IAgendaTarefaRecorrenteExecucao, 'id'> | null,
  ): boolean {
    return o1 && o2
      ? this.getAgendaTarefaRecorrenteExecucaoIdentifier(o1) === this.getAgendaTarefaRecorrenteExecucaoIdentifier(o2)
      : o1 === o2;
  }

  addAgendaTarefaRecorrenteExecucaoToCollectionIfMissing<Type extends Pick<IAgendaTarefaRecorrenteExecucao, 'id'>>(
    agendaTarefaRecorrenteExecucaoCollection: Type[],
    ...agendaTarefaRecorrenteExecucaosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const agendaTarefaRecorrenteExecucaos: Type[] = agendaTarefaRecorrenteExecucaosToCheck.filter(isPresent);
    if (agendaTarefaRecorrenteExecucaos.length > 0) {
      const agendaTarefaRecorrenteExecucaoCollectionIdentifiers = agendaTarefaRecorrenteExecucaoCollection.map(
        agendaTarefaRecorrenteExecucaoItem => this.getAgendaTarefaRecorrenteExecucaoIdentifier(agendaTarefaRecorrenteExecucaoItem),
      );
      const agendaTarefaRecorrenteExecucaosToAdd = agendaTarefaRecorrenteExecucaos.filter(agendaTarefaRecorrenteExecucaoItem => {
        const agendaTarefaRecorrenteExecucaoIdentifier =
          this.getAgendaTarefaRecorrenteExecucaoIdentifier(agendaTarefaRecorrenteExecucaoItem);
        if (agendaTarefaRecorrenteExecucaoCollectionIdentifiers.includes(agendaTarefaRecorrenteExecucaoIdentifier)) {
          return false;
        }
        agendaTarefaRecorrenteExecucaoCollectionIdentifiers.push(agendaTarefaRecorrenteExecucaoIdentifier);
        return true;
      });
      return [...agendaTarefaRecorrenteExecucaosToAdd, ...agendaTarefaRecorrenteExecucaoCollection];
    }
    return agendaTarefaRecorrenteExecucaoCollection;
  }

  protected convertDateFromClient<
    T extends IAgendaTarefaRecorrenteExecucao | NewAgendaTarefaRecorrenteExecucao | PartialUpdateAgendaTarefaRecorrenteExecucao,
  >(agendaTarefaRecorrenteExecucao: T): RestOf<T> {
    return {
      ...agendaTarefaRecorrenteExecucao,
      horaInicio: agendaTarefaRecorrenteExecucao.horaInicio?.toJSON() ?? null,
      horaFim: agendaTarefaRecorrenteExecucao.horaFim?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restAgendaTarefaRecorrenteExecucao: RestAgendaTarefaRecorrenteExecucao): IAgendaTarefaRecorrenteExecucao {
    return {
      ...restAgendaTarefaRecorrenteExecucao,
      horaInicio: restAgendaTarefaRecorrenteExecucao.horaInicio ? dayjs(restAgendaTarefaRecorrenteExecucao.horaInicio) : undefined,
      horaFim: restAgendaTarefaRecorrenteExecucao.horaFim ? dayjs(restAgendaTarefaRecorrenteExecucao.horaFim) : undefined,
    };
  }

  protected convertResponseFromServer(
    res: HttpResponse<RestAgendaTarefaRecorrenteExecucao>,
  ): HttpResponse<IAgendaTarefaRecorrenteExecucao> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(
    res: HttpResponse<RestAgendaTarefaRecorrenteExecucao[]>,
  ): HttpResponse<IAgendaTarefaRecorrenteExecucao[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
