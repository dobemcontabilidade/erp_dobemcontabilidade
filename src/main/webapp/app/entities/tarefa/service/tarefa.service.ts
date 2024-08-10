import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITarefa, NewTarefa } from '../tarefa.model';

export type PartialUpdateTarefa = Partial<ITarefa> & Pick<ITarefa, 'id'>;

type RestOf<T extends ITarefa | NewTarefa> = Omit<T, 'dataLegal'> & {
  dataLegal?: string | null;
};

export type RestTarefa = RestOf<ITarefa>;

export type NewRestTarefa = RestOf<NewTarefa>;

export type PartialUpdateRestTarefa = RestOf<PartialUpdateTarefa>;

export type EntityResponseType = HttpResponse<ITarefa>;
export type EntityArrayResponseType = HttpResponse<ITarefa[]>;

@Injectable({ providedIn: 'root' })
export class TarefaService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/tarefas');

  create(tarefa: NewTarefa): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tarefa);
    return this.http
      .post<RestTarefa>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(tarefa: ITarefa): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tarefa);
    return this.http
      .put<RestTarefa>(`${this.resourceUrl}/${this.getTarefaIdentifier(tarefa)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(tarefa: PartialUpdateTarefa): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tarefa);
    return this.http
      .patch<RestTarefa>(`${this.resourceUrl}/${this.getTarefaIdentifier(tarefa)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestTarefa>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestTarefa[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getTarefaIdentifier(tarefa: Pick<ITarefa, 'id'>): number {
    return tarefa.id;
  }

  compareTarefa(o1: Pick<ITarefa, 'id'> | null, o2: Pick<ITarefa, 'id'> | null): boolean {
    return o1 && o2 ? this.getTarefaIdentifier(o1) === this.getTarefaIdentifier(o2) : o1 === o2;
  }

  addTarefaToCollectionIfMissing<Type extends Pick<ITarefa, 'id'>>(
    tarefaCollection: Type[],
    ...tarefasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const tarefas: Type[] = tarefasToCheck.filter(isPresent);
    if (tarefas.length > 0) {
      const tarefaCollectionIdentifiers = tarefaCollection.map(tarefaItem => this.getTarefaIdentifier(tarefaItem));
      const tarefasToAdd = tarefas.filter(tarefaItem => {
        const tarefaIdentifier = this.getTarefaIdentifier(tarefaItem);
        if (tarefaCollectionIdentifiers.includes(tarefaIdentifier)) {
          return false;
        }
        tarefaCollectionIdentifiers.push(tarefaIdentifier);
        return true;
      });
      return [...tarefasToAdd, ...tarefaCollection];
    }
    return tarefaCollection;
  }

  protected convertDateFromClient<T extends ITarefa | NewTarefa | PartialUpdateTarefa>(tarefa: T): RestOf<T> {
    return {
      ...tarefa,
      dataLegal: tarefa.dataLegal?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restTarefa: RestTarefa): ITarefa {
    return {
      ...restTarefa,
      dataLegal: restTarefa.dataLegal ? dayjs(restTarefa.dataLegal) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestTarefa>): HttpResponse<ITarefa> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestTarefa[]>): HttpResponse<ITarefa[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
