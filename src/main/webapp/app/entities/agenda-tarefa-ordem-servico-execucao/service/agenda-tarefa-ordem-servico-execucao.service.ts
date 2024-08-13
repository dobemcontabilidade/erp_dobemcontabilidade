import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAgendaTarefaOrdemServicoExecucao, NewAgendaTarefaOrdemServicoExecucao } from '../agenda-tarefa-ordem-servico-execucao.model';

export type PartialUpdateAgendaTarefaOrdemServicoExecucao = Partial<IAgendaTarefaOrdemServicoExecucao> &
  Pick<IAgendaTarefaOrdemServicoExecucao, 'id'>;

type RestOf<T extends IAgendaTarefaOrdemServicoExecucao | NewAgendaTarefaOrdemServicoExecucao> = Omit<T, 'horaInicio' | 'horaFim'> & {
  horaInicio?: string | null;
  horaFim?: string | null;
};

export type RestAgendaTarefaOrdemServicoExecucao = RestOf<IAgendaTarefaOrdemServicoExecucao>;

export type NewRestAgendaTarefaOrdemServicoExecucao = RestOf<NewAgendaTarefaOrdemServicoExecucao>;

export type PartialUpdateRestAgendaTarefaOrdemServicoExecucao = RestOf<PartialUpdateAgendaTarefaOrdemServicoExecucao>;

export type EntityResponseType = HttpResponse<IAgendaTarefaOrdemServicoExecucao>;
export type EntityArrayResponseType = HttpResponse<IAgendaTarefaOrdemServicoExecucao[]>;

@Injectable({ providedIn: 'root' })
export class AgendaTarefaOrdemServicoExecucaoService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/agenda-tarefa-ordem-servico-execucaos');

  create(agendaTarefaOrdemServicoExecucao: NewAgendaTarefaOrdemServicoExecucao): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(agendaTarefaOrdemServicoExecucao);
    return this.http
      .post<RestAgendaTarefaOrdemServicoExecucao>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(agendaTarefaOrdemServicoExecucao: IAgendaTarefaOrdemServicoExecucao): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(agendaTarefaOrdemServicoExecucao);
    return this.http
      .put<RestAgendaTarefaOrdemServicoExecucao>(
        `${this.resourceUrl}/${this.getAgendaTarefaOrdemServicoExecucaoIdentifier(agendaTarefaOrdemServicoExecucao)}`,
        copy,
        { observe: 'response' },
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(agendaTarefaOrdemServicoExecucao: PartialUpdateAgendaTarefaOrdemServicoExecucao): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(agendaTarefaOrdemServicoExecucao);
    return this.http
      .patch<RestAgendaTarefaOrdemServicoExecucao>(
        `${this.resourceUrl}/${this.getAgendaTarefaOrdemServicoExecucaoIdentifier(agendaTarefaOrdemServicoExecucao)}`,
        copy,
        { observe: 'response' },
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestAgendaTarefaOrdemServicoExecucao>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestAgendaTarefaOrdemServicoExecucao[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAgendaTarefaOrdemServicoExecucaoIdentifier(agendaTarefaOrdemServicoExecucao: Pick<IAgendaTarefaOrdemServicoExecucao, 'id'>): number {
    return agendaTarefaOrdemServicoExecucao.id;
  }

  compareAgendaTarefaOrdemServicoExecucao(
    o1: Pick<IAgendaTarefaOrdemServicoExecucao, 'id'> | null,
    o2: Pick<IAgendaTarefaOrdemServicoExecucao, 'id'> | null,
  ): boolean {
    return o1 && o2
      ? this.getAgendaTarefaOrdemServicoExecucaoIdentifier(o1) === this.getAgendaTarefaOrdemServicoExecucaoIdentifier(o2)
      : o1 === o2;
  }

  addAgendaTarefaOrdemServicoExecucaoToCollectionIfMissing<Type extends Pick<IAgendaTarefaOrdemServicoExecucao, 'id'>>(
    agendaTarefaOrdemServicoExecucaoCollection: Type[],
    ...agendaTarefaOrdemServicoExecucaosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const agendaTarefaOrdemServicoExecucaos: Type[] = agendaTarefaOrdemServicoExecucaosToCheck.filter(isPresent);
    if (agendaTarefaOrdemServicoExecucaos.length > 0) {
      const agendaTarefaOrdemServicoExecucaoCollectionIdentifiers = agendaTarefaOrdemServicoExecucaoCollection.map(
        agendaTarefaOrdemServicoExecucaoItem => this.getAgendaTarefaOrdemServicoExecucaoIdentifier(agendaTarefaOrdemServicoExecucaoItem),
      );
      const agendaTarefaOrdemServicoExecucaosToAdd = agendaTarefaOrdemServicoExecucaos.filter(agendaTarefaOrdemServicoExecucaoItem => {
        const agendaTarefaOrdemServicoExecucaoIdentifier = this.getAgendaTarefaOrdemServicoExecucaoIdentifier(
          agendaTarefaOrdemServicoExecucaoItem,
        );
        if (agendaTarefaOrdemServicoExecucaoCollectionIdentifiers.includes(agendaTarefaOrdemServicoExecucaoIdentifier)) {
          return false;
        }
        agendaTarefaOrdemServicoExecucaoCollectionIdentifiers.push(agendaTarefaOrdemServicoExecucaoIdentifier);
        return true;
      });
      return [...agendaTarefaOrdemServicoExecucaosToAdd, ...agendaTarefaOrdemServicoExecucaoCollection];
    }
    return agendaTarefaOrdemServicoExecucaoCollection;
  }

  protected convertDateFromClient<
    T extends IAgendaTarefaOrdemServicoExecucao | NewAgendaTarefaOrdemServicoExecucao | PartialUpdateAgendaTarefaOrdemServicoExecucao,
  >(agendaTarefaOrdemServicoExecucao: T): RestOf<T> {
    return {
      ...agendaTarefaOrdemServicoExecucao,
      horaInicio: agendaTarefaOrdemServicoExecucao.horaInicio?.toJSON() ?? null,
      horaFim: agendaTarefaOrdemServicoExecucao.horaFim?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(
    restAgendaTarefaOrdemServicoExecucao: RestAgendaTarefaOrdemServicoExecucao,
  ): IAgendaTarefaOrdemServicoExecucao {
    return {
      ...restAgendaTarefaOrdemServicoExecucao,
      horaInicio: restAgendaTarefaOrdemServicoExecucao.horaInicio ? dayjs(restAgendaTarefaOrdemServicoExecucao.horaInicio) : undefined,
      horaFim: restAgendaTarefaOrdemServicoExecucao.horaFim ? dayjs(restAgendaTarefaOrdemServicoExecucao.horaFim) : undefined,
    };
  }

  protected convertResponseFromServer(
    res: HttpResponse<RestAgendaTarefaOrdemServicoExecucao>,
  ): HttpResponse<IAgendaTarefaOrdemServicoExecucao> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(
    res: HttpResponse<RestAgendaTarefaOrdemServicoExecucao[]>,
  ): HttpResponse<IAgendaTarefaOrdemServicoExecucao[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
