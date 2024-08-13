import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import {
  IContadorResponsavelTarefaRecorrente,
  NewContadorResponsavelTarefaRecorrente,
} from '../contador-responsavel-tarefa-recorrente.model';

export type PartialUpdateContadorResponsavelTarefaRecorrente = Partial<IContadorResponsavelTarefaRecorrente> &
  Pick<IContadorResponsavelTarefaRecorrente, 'id'>;

type RestOf<T extends IContadorResponsavelTarefaRecorrente | NewContadorResponsavelTarefaRecorrente> = Omit<
  T,
  'dataAtribuicao' | 'dataRevogacao'
> & {
  dataAtribuicao?: string | null;
  dataRevogacao?: string | null;
};

export type RestContadorResponsavelTarefaRecorrente = RestOf<IContadorResponsavelTarefaRecorrente>;

export type NewRestContadorResponsavelTarefaRecorrente = RestOf<NewContadorResponsavelTarefaRecorrente>;

export type PartialUpdateRestContadorResponsavelTarefaRecorrente = RestOf<PartialUpdateContadorResponsavelTarefaRecorrente>;

export type EntityResponseType = HttpResponse<IContadorResponsavelTarefaRecorrente>;
export type EntityArrayResponseType = HttpResponse<IContadorResponsavelTarefaRecorrente[]>;

@Injectable({ providedIn: 'root' })
export class ContadorResponsavelTarefaRecorrenteService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/contador-responsavel-tarefa-recorrentes');

  create(contadorResponsavelTarefaRecorrente: NewContadorResponsavelTarefaRecorrente): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(contadorResponsavelTarefaRecorrente);
    return this.http
      .post<RestContadorResponsavelTarefaRecorrente>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(contadorResponsavelTarefaRecorrente: IContadorResponsavelTarefaRecorrente): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(contadorResponsavelTarefaRecorrente);
    return this.http
      .put<RestContadorResponsavelTarefaRecorrente>(
        `${this.resourceUrl}/${this.getContadorResponsavelTarefaRecorrenteIdentifier(contadorResponsavelTarefaRecorrente)}`,
        copy,
        { observe: 'response' },
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(contadorResponsavelTarefaRecorrente: PartialUpdateContadorResponsavelTarefaRecorrente): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(contadorResponsavelTarefaRecorrente);
    return this.http
      .patch<RestContadorResponsavelTarefaRecorrente>(
        `${this.resourceUrl}/${this.getContadorResponsavelTarefaRecorrenteIdentifier(contadorResponsavelTarefaRecorrente)}`,
        copy,
        { observe: 'response' },
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestContadorResponsavelTarefaRecorrente>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestContadorResponsavelTarefaRecorrente[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getContadorResponsavelTarefaRecorrenteIdentifier(
    contadorResponsavelTarefaRecorrente: Pick<IContadorResponsavelTarefaRecorrente, 'id'>,
  ): number {
    return contadorResponsavelTarefaRecorrente.id;
  }

  compareContadorResponsavelTarefaRecorrente(
    o1: Pick<IContadorResponsavelTarefaRecorrente, 'id'> | null,
    o2: Pick<IContadorResponsavelTarefaRecorrente, 'id'> | null,
  ): boolean {
    return o1 && o2
      ? this.getContadorResponsavelTarefaRecorrenteIdentifier(o1) === this.getContadorResponsavelTarefaRecorrenteIdentifier(o2)
      : o1 === o2;
  }

  addContadorResponsavelTarefaRecorrenteToCollectionIfMissing<Type extends Pick<IContadorResponsavelTarefaRecorrente, 'id'>>(
    contadorResponsavelTarefaRecorrenteCollection: Type[],
    ...contadorResponsavelTarefaRecorrentesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const contadorResponsavelTarefaRecorrentes: Type[] = contadorResponsavelTarefaRecorrentesToCheck.filter(isPresent);
    if (contadorResponsavelTarefaRecorrentes.length > 0) {
      const contadorResponsavelTarefaRecorrenteCollectionIdentifiers = contadorResponsavelTarefaRecorrenteCollection.map(
        contadorResponsavelTarefaRecorrenteItem =>
          this.getContadorResponsavelTarefaRecorrenteIdentifier(contadorResponsavelTarefaRecorrenteItem),
      );
      const contadorResponsavelTarefaRecorrentesToAdd = contadorResponsavelTarefaRecorrentes.filter(
        contadorResponsavelTarefaRecorrenteItem => {
          const contadorResponsavelTarefaRecorrenteIdentifier = this.getContadorResponsavelTarefaRecorrenteIdentifier(
            contadorResponsavelTarefaRecorrenteItem,
          );
          if (contadorResponsavelTarefaRecorrenteCollectionIdentifiers.includes(contadorResponsavelTarefaRecorrenteIdentifier)) {
            return false;
          }
          contadorResponsavelTarefaRecorrenteCollectionIdentifiers.push(contadorResponsavelTarefaRecorrenteIdentifier);
          return true;
        },
      );
      return [...contadorResponsavelTarefaRecorrentesToAdd, ...contadorResponsavelTarefaRecorrenteCollection];
    }
    return contadorResponsavelTarefaRecorrenteCollection;
  }

  protected convertDateFromClient<
    T extends
      | IContadorResponsavelTarefaRecorrente
      | NewContadorResponsavelTarefaRecorrente
      | PartialUpdateContadorResponsavelTarefaRecorrente,
  >(contadorResponsavelTarefaRecorrente: T): RestOf<T> {
    return {
      ...contadorResponsavelTarefaRecorrente,
      dataAtribuicao: contadorResponsavelTarefaRecorrente.dataAtribuicao?.toJSON() ?? null,
      dataRevogacao: contadorResponsavelTarefaRecorrente.dataRevogacao?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(
    restContadorResponsavelTarefaRecorrente: RestContadorResponsavelTarefaRecorrente,
  ): IContadorResponsavelTarefaRecorrente {
    return {
      ...restContadorResponsavelTarefaRecorrente,
      dataAtribuicao: restContadorResponsavelTarefaRecorrente.dataAtribuicao
        ? dayjs(restContadorResponsavelTarefaRecorrente.dataAtribuicao)
        : undefined,
      dataRevogacao: restContadorResponsavelTarefaRecorrente.dataRevogacao
        ? dayjs(restContadorResponsavelTarefaRecorrente.dataRevogacao)
        : undefined,
    };
  }

  protected convertResponseFromServer(
    res: HttpResponse<RestContadorResponsavelTarefaRecorrente>,
  ): HttpResponse<IContadorResponsavelTarefaRecorrente> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(
    res: HttpResponse<RestContadorResponsavelTarefaRecorrente[]>,
  ): HttpResponse<IContadorResponsavelTarefaRecorrente[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
