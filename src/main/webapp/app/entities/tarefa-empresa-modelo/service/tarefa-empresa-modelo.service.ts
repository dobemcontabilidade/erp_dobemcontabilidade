import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITarefaEmpresaModelo, NewTarefaEmpresaModelo } from '../tarefa-empresa-modelo.model';

export type PartialUpdateTarefaEmpresaModelo = Partial<ITarefaEmpresaModelo> & Pick<ITarefaEmpresaModelo, 'id'>;

type RestOf<T extends ITarefaEmpresaModelo | NewTarefaEmpresaModelo> = Omit<T, 'dataAdmin' | 'dataLegal'> & {
  dataAdmin?: string | null;
  dataLegal?: string | null;
};

export type RestTarefaEmpresaModelo = RestOf<ITarefaEmpresaModelo>;

export type NewRestTarefaEmpresaModelo = RestOf<NewTarefaEmpresaModelo>;

export type PartialUpdateRestTarefaEmpresaModelo = RestOf<PartialUpdateTarefaEmpresaModelo>;

export type EntityResponseType = HttpResponse<ITarefaEmpresaModelo>;
export type EntityArrayResponseType = HttpResponse<ITarefaEmpresaModelo[]>;

@Injectable({ providedIn: 'root' })
export class TarefaEmpresaModeloService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/tarefa-empresa-modelos');

  create(tarefaEmpresaModelo: NewTarefaEmpresaModelo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tarefaEmpresaModelo);
    return this.http
      .post<RestTarefaEmpresaModelo>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(tarefaEmpresaModelo: ITarefaEmpresaModelo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tarefaEmpresaModelo);
    return this.http
      .put<RestTarefaEmpresaModelo>(`${this.resourceUrl}/${this.getTarefaEmpresaModeloIdentifier(tarefaEmpresaModelo)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(tarefaEmpresaModelo: PartialUpdateTarefaEmpresaModelo): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(tarefaEmpresaModelo);
    return this.http
      .patch<RestTarefaEmpresaModelo>(`${this.resourceUrl}/${this.getTarefaEmpresaModeloIdentifier(tarefaEmpresaModelo)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestTarefaEmpresaModelo>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestTarefaEmpresaModelo[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getTarefaEmpresaModeloIdentifier(tarefaEmpresaModelo: Pick<ITarefaEmpresaModelo, 'id'>): number {
    return tarefaEmpresaModelo.id;
  }

  compareTarefaEmpresaModelo(o1: Pick<ITarefaEmpresaModelo, 'id'> | null, o2: Pick<ITarefaEmpresaModelo, 'id'> | null): boolean {
    return o1 && o2 ? this.getTarefaEmpresaModeloIdentifier(o1) === this.getTarefaEmpresaModeloIdentifier(o2) : o1 === o2;
  }

  addTarefaEmpresaModeloToCollectionIfMissing<Type extends Pick<ITarefaEmpresaModelo, 'id'>>(
    tarefaEmpresaModeloCollection: Type[],
    ...tarefaEmpresaModelosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const tarefaEmpresaModelos: Type[] = tarefaEmpresaModelosToCheck.filter(isPresent);
    if (tarefaEmpresaModelos.length > 0) {
      const tarefaEmpresaModeloCollectionIdentifiers = tarefaEmpresaModeloCollection.map(tarefaEmpresaModeloItem =>
        this.getTarefaEmpresaModeloIdentifier(tarefaEmpresaModeloItem),
      );
      const tarefaEmpresaModelosToAdd = tarefaEmpresaModelos.filter(tarefaEmpresaModeloItem => {
        const tarefaEmpresaModeloIdentifier = this.getTarefaEmpresaModeloIdentifier(tarefaEmpresaModeloItem);
        if (tarefaEmpresaModeloCollectionIdentifiers.includes(tarefaEmpresaModeloIdentifier)) {
          return false;
        }
        tarefaEmpresaModeloCollectionIdentifiers.push(tarefaEmpresaModeloIdentifier);
        return true;
      });
      return [...tarefaEmpresaModelosToAdd, ...tarefaEmpresaModeloCollection];
    }
    return tarefaEmpresaModeloCollection;
  }

  protected convertDateFromClient<T extends ITarefaEmpresaModelo | NewTarefaEmpresaModelo | PartialUpdateTarefaEmpresaModelo>(
    tarefaEmpresaModelo: T,
  ): RestOf<T> {
    return {
      ...tarefaEmpresaModelo,
      dataAdmin: tarefaEmpresaModelo.dataAdmin?.toJSON() ?? null,
      dataLegal: tarefaEmpresaModelo.dataLegal?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restTarefaEmpresaModelo: RestTarefaEmpresaModelo): ITarefaEmpresaModelo {
    return {
      ...restTarefaEmpresaModelo,
      dataAdmin: restTarefaEmpresaModelo.dataAdmin ? dayjs(restTarefaEmpresaModelo.dataAdmin) : undefined,
      dataLegal: restTarefaEmpresaModelo.dataLegal ? dayjs(restTarefaEmpresaModelo.dataLegal) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestTarefaEmpresaModelo>): HttpResponse<ITarefaEmpresaModelo> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestTarefaEmpresaModelo[]>): HttpResponse<ITarefaEmpresaModelo[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
