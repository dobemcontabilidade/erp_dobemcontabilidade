import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITarefaOrdemServico, NewTarefaOrdemServico } from '../tarefa-ordem-servico.model';

export type PartialUpdateTarefaOrdemServico = Partial<ITarefaOrdemServico> & Pick<ITarefaOrdemServico, 'id'>;

type RestOf<T extends ITarefaOrdemServico | NewTarefaOrdemServico> = Omit<T, 'dataAdmin'> & {
  dataAdmin?: string | null;
};

export type RestTarefaOrdemServico = RestOf<ITarefaOrdemServico>;

export type NewRestTarefaOrdemServico = RestOf<NewTarefaOrdemServico>;

export type PartialUpdateRestTarefaOrdemServico = RestOf<PartialUpdateTarefaOrdemServico>;

export type EntityResponseType = HttpResponse<ITarefaOrdemServico>;
export type EntityArrayResponseType = HttpResponse<ITarefaOrdemServico[]>;

@Injectable({ providedIn: 'root' })
export class TarefaOrdemServicoService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/tarefa-ordem-servicos');

  create(tarefaOrdemServico: NewTarefaOrdemServico): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tarefaOrdemServico);
    return this.http
      .post<RestTarefaOrdemServico>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(tarefaOrdemServico: ITarefaOrdemServico): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tarefaOrdemServico);
    return this.http
      .put<RestTarefaOrdemServico>(`${this.resourceUrl}/${this.getTarefaOrdemServicoIdentifier(tarefaOrdemServico)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(tarefaOrdemServico: PartialUpdateTarefaOrdemServico): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tarefaOrdemServico);
    return this.http
      .patch<RestTarefaOrdemServico>(`${this.resourceUrl}/${this.getTarefaOrdemServicoIdentifier(tarefaOrdemServico)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestTarefaOrdemServico>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestTarefaOrdemServico[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getTarefaOrdemServicoIdentifier(tarefaOrdemServico: Pick<ITarefaOrdemServico, 'id'>): number {
    return tarefaOrdemServico.id;
  }

  compareTarefaOrdemServico(o1: Pick<ITarefaOrdemServico, 'id'> | null, o2: Pick<ITarefaOrdemServico, 'id'> | null): boolean {
    return o1 && o2 ? this.getTarefaOrdemServicoIdentifier(o1) === this.getTarefaOrdemServicoIdentifier(o2) : o1 === o2;
  }

  addTarefaOrdemServicoToCollectionIfMissing<Type extends Pick<ITarefaOrdemServico, 'id'>>(
    tarefaOrdemServicoCollection: Type[],
    ...tarefaOrdemServicosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const tarefaOrdemServicos: Type[] = tarefaOrdemServicosToCheck.filter(isPresent);
    if (tarefaOrdemServicos.length > 0) {
      const tarefaOrdemServicoCollectionIdentifiers = tarefaOrdemServicoCollection.map(tarefaOrdemServicoItem =>
        this.getTarefaOrdemServicoIdentifier(tarefaOrdemServicoItem),
      );
      const tarefaOrdemServicosToAdd = tarefaOrdemServicos.filter(tarefaOrdemServicoItem => {
        const tarefaOrdemServicoIdentifier = this.getTarefaOrdemServicoIdentifier(tarefaOrdemServicoItem);
        if (tarefaOrdemServicoCollectionIdentifiers.includes(tarefaOrdemServicoIdentifier)) {
          return false;
        }
        tarefaOrdemServicoCollectionIdentifiers.push(tarefaOrdemServicoIdentifier);
        return true;
      });
      return [...tarefaOrdemServicosToAdd, ...tarefaOrdemServicoCollection];
    }
    return tarefaOrdemServicoCollection;
  }

  protected convertDateFromClient<T extends ITarefaOrdemServico | NewTarefaOrdemServico | PartialUpdateTarefaOrdemServico>(
    tarefaOrdemServico: T,
  ): RestOf<T> {
    return {
      ...tarefaOrdemServico,
      dataAdmin: tarefaOrdemServico.dataAdmin?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restTarefaOrdemServico: RestTarefaOrdemServico): ITarefaOrdemServico {
    return {
      ...restTarefaOrdemServico,
      dataAdmin: restTarefaOrdemServico.dataAdmin ? dayjs(restTarefaOrdemServico.dataAdmin) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestTarefaOrdemServico>): HttpResponse<ITarefaOrdemServico> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestTarefaOrdemServico[]>): HttpResponse<ITarefaOrdemServico[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
