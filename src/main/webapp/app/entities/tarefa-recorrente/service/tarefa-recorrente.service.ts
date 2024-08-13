import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITarefaRecorrente, NewTarefaRecorrente } from '../tarefa-recorrente.model';

export type PartialUpdateTarefaRecorrente = Partial<ITarefaRecorrente> & Pick<ITarefaRecorrente, 'id'>;

type RestOf<T extends ITarefaRecorrente | NewTarefaRecorrente> = Omit<T, 'dataAdmin'> & {
  dataAdmin?: string | null;
};

export type RestTarefaRecorrente = RestOf<ITarefaRecorrente>;

export type NewRestTarefaRecorrente = RestOf<NewTarefaRecorrente>;

export type PartialUpdateRestTarefaRecorrente = RestOf<PartialUpdateTarefaRecorrente>;

export type EntityResponseType = HttpResponse<ITarefaRecorrente>;
export type EntityArrayResponseType = HttpResponse<ITarefaRecorrente[]>;

@Injectable({ providedIn: 'root' })
export class TarefaRecorrenteService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/tarefa-recorrentes');

  create(tarefaRecorrente: NewTarefaRecorrente): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tarefaRecorrente);
    return this.http
      .post<RestTarefaRecorrente>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(tarefaRecorrente: ITarefaRecorrente): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tarefaRecorrente);
    return this.http
      .put<RestTarefaRecorrente>(`${this.resourceUrl}/${this.getTarefaRecorrenteIdentifier(tarefaRecorrente)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(tarefaRecorrente: PartialUpdateTarefaRecorrente): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tarefaRecorrente);
    return this.http
      .patch<RestTarefaRecorrente>(`${this.resourceUrl}/${this.getTarefaRecorrenteIdentifier(tarefaRecorrente)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestTarefaRecorrente>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestTarefaRecorrente[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getTarefaRecorrenteIdentifier(tarefaRecorrente: Pick<ITarefaRecorrente, 'id'>): number {
    return tarefaRecorrente.id;
  }

  compareTarefaRecorrente(o1: Pick<ITarefaRecorrente, 'id'> | null, o2: Pick<ITarefaRecorrente, 'id'> | null): boolean {
    return o1 && o2 ? this.getTarefaRecorrenteIdentifier(o1) === this.getTarefaRecorrenteIdentifier(o2) : o1 === o2;
  }

  addTarefaRecorrenteToCollectionIfMissing<Type extends Pick<ITarefaRecorrente, 'id'>>(
    tarefaRecorrenteCollection: Type[],
    ...tarefaRecorrentesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const tarefaRecorrentes: Type[] = tarefaRecorrentesToCheck.filter(isPresent);
    if (tarefaRecorrentes.length > 0) {
      const tarefaRecorrenteCollectionIdentifiers = tarefaRecorrenteCollection.map(tarefaRecorrenteItem =>
        this.getTarefaRecorrenteIdentifier(tarefaRecorrenteItem),
      );
      const tarefaRecorrentesToAdd = tarefaRecorrentes.filter(tarefaRecorrenteItem => {
        const tarefaRecorrenteIdentifier = this.getTarefaRecorrenteIdentifier(tarefaRecorrenteItem);
        if (tarefaRecorrenteCollectionIdentifiers.includes(tarefaRecorrenteIdentifier)) {
          return false;
        }
        tarefaRecorrenteCollectionIdentifiers.push(tarefaRecorrenteIdentifier);
        return true;
      });
      return [...tarefaRecorrentesToAdd, ...tarefaRecorrenteCollection];
    }
    return tarefaRecorrenteCollection;
  }

  protected convertDateFromClient<T extends ITarefaRecorrente | NewTarefaRecorrente | PartialUpdateTarefaRecorrente>(
    tarefaRecorrente: T,
  ): RestOf<T> {
    return {
      ...tarefaRecorrente,
      dataAdmin: tarefaRecorrente.dataAdmin?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restTarefaRecorrente: RestTarefaRecorrente): ITarefaRecorrente {
    return {
      ...restTarefaRecorrente,
      dataAdmin: restTarefaRecorrente.dataAdmin ? dayjs(restTarefaRecorrente.dataAdmin) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestTarefaRecorrente>): HttpResponse<ITarefaRecorrente> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestTarefaRecorrente[]>): HttpResponse<ITarefaRecorrente[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
