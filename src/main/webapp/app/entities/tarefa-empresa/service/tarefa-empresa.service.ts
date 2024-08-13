import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITarefaEmpresa, NewTarefaEmpresa } from '../tarefa-empresa.model';

export type PartialUpdateTarefaEmpresa = Partial<ITarefaEmpresa> & Pick<ITarefaEmpresa, 'id'>;

type RestOf<T extends ITarefaEmpresa | NewTarefaEmpresa> = Omit<T, 'dataHora'> & {
  dataHora?: string | null;
};

export type RestTarefaEmpresa = RestOf<ITarefaEmpresa>;

export type NewRestTarefaEmpresa = RestOf<NewTarefaEmpresa>;

export type PartialUpdateRestTarefaEmpresa = RestOf<PartialUpdateTarefaEmpresa>;

export type EntityResponseType = HttpResponse<ITarefaEmpresa>;
export type EntityArrayResponseType = HttpResponse<ITarefaEmpresa[]>;

@Injectable({ providedIn: 'root' })
export class TarefaEmpresaService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/tarefa-empresas');

  create(tarefaEmpresa: NewTarefaEmpresa): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tarefaEmpresa);
    return this.http
      .post<RestTarefaEmpresa>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(tarefaEmpresa: ITarefaEmpresa): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tarefaEmpresa);
    return this.http
      .put<RestTarefaEmpresa>(`${this.resourceUrl}/${this.getTarefaEmpresaIdentifier(tarefaEmpresa)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(tarefaEmpresa: PartialUpdateTarefaEmpresa): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tarefaEmpresa);
    return this.http
      .patch<RestTarefaEmpresa>(`${this.resourceUrl}/${this.getTarefaEmpresaIdentifier(tarefaEmpresa)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestTarefaEmpresa>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestTarefaEmpresa[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getTarefaEmpresaIdentifier(tarefaEmpresa: Pick<ITarefaEmpresa, 'id'>): number {
    return tarefaEmpresa.id;
  }

  compareTarefaEmpresa(o1: Pick<ITarefaEmpresa, 'id'> | null, o2: Pick<ITarefaEmpresa, 'id'> | null): boolean {
    return o1 && o2 ? this.getTarefaEmpresaIdentifier(o1) === this.getTarefaEmpresaIdentifier(o2) : o1 === o2;
  }

  addTarefaEmpresaToCollectionIfMissing<Type extends Pick<ITarefaEmpresa, 'id'>>(
    tarefaEmpresaCollection: Type[],
    ...tarefaEmpresasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const tarefaEmpresas: Type[] = tarefaEmpresasToCheck.filter(isPresent);
    if (tarefaEmpresas.length > 0) {
      const tarefaEmpresaCollectionIdentifiers = tarefaEmpresaCollection.map(tarefaEmpresaItem =>
        this.getTarefaEmpresaIdentifier(tarefaEmpresaItem),
      );
      const tarefaEmpresasToAdd = tarefaEmpresas.filter(tarefaEmpresaItem => {
        const tarefaEmpresaIdentifier = this.getTarefaEmpresaIdentifier(tarefaEmpresaItem);
        if (tarefaEmpresaCollectionIdentifiers.includes(tarefaEmpresaIdentifier)) {
          return false;
        }
        tarefaEmpresaCollectionIdentifiers.push(tarefaEmpresaIdentifier);
        return true;
      });
      return [...tarefaEmpresasToAdd, ...tarefaEmpresaCollection];
    }
    return tarefaEmpresaCollection;
  }

  protected convertDateFromClient<T extends ITarefaEmpresa | NewTarefaEmpresa | PartialUpdateTarefaEmpresa>(tarefaEmpresa: T): RestOf<T> {
    return {
      ...tarefaEmpresa,
      dataHora: tarefaEmpresa.dataHora?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restTarefaEmpresa: RestTarefaEmpresa): ITarefaEmpresa {
    return {
      ...restTarefaEmpresa,
      dataHora: restTarefaEmpresa.dataHora ? dayjs(restTarefaEmpresa.dataHora) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestTarefaEmpresa>): HttpResponse<ITarefaEmpresa> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestTarefaEmpresa[]>): HttpResponse<ITarefaEmpresa[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
