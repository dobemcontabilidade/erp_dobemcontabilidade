import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITermoContratoContabil, NewTermoContratoContabil } from '../termo-contrato-contabil.model';

export type PartialUpdateTermoContratoContabil = Partial<ITermoContratoContabil> & Pick<ITermoContratoContabil, 'id'>;

type RestOf<T extends ITermoContratoContabil | NewTermoContratoContabil> = Omit<T, 'dataCriacao'> & {
  dataCriacao?: string | null;
};

export type RestTermoContratoContabil = RestOf<ITermoContratoContabil>;

export type NewRestTermoContratoContabil = RestOf<NewTermoContratoContabil>;

export type PartialUpdateRestTermoContratoContabil = RestOf<PartialUpdateTermoContratoContabil>;

export type EntityResponseType = HttpResponse<ITermoContratoContabil>;
export type EntityArrayResponseType = HttpResponse<ITermoContratoContabil[]>;

@Injectable({ providedIn: 'root' })
export class TermoContratoContabilService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/termo-contrato-contabils');

  create(termoContratoContabil: NewTermoContratoContabil): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(termoContratoContabil);
    return this.http
      .post<RestTermoContratoContabil>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(termoContratoContabil: ITermoContratoContabil): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(termoContratoContabil);
    return this.http
      .put<RestTermoContratoContabil>(`${this.resourceUrl}/${this.getTermoContratoContabilIdentifier(termoContratoContabil)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(termoContratoContabil: PartialUpdateTermoContratoContabil): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(termoContratoContabil);
    return this.http
      .patch<RestTermoContratoContabil>(`${this.resourceUrl}/${this.getTermoContratoContabilIdentifier(termoContratoContabil)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestTermoContratoContabil>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestTermoContratoContabil[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getTermoContratoContabilIdentifier(termoContratoContabil: Pick<ITermoContratoContabil, 'id'>): number {
    return termoContratoContabil.id;
  }

  compareTermoContratoContabil(o1: Pick<ITermoContratoContabil, 'id'> | null, o2: Pick<ITermoContratoContabil, 'id'> | null): boolean {
    return o1 && o2 ? this.getTermoContratoContabilIdentifier(o1) === this.getTermoContratoContabilIdentifier(o2) : o1 === o2;
  }

  addTermoContratoContabilToCollectionIfMissing<Type extends Pick<ITermoContratoContabil, 'id'>>(
    termoContratoContabilCollection: Type[],
    ...termoContratoContabilsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const termoContratoContabils: Type[] = termoContratoContabilsToCheck.filter(isPresent);
    if (termoContratoContabils.length > 0) {
      const termoContratoContabilCollectionIdentifiers = termoContratoContabilCollection.map(termoContratoContabilItem =>
        this.getTermoContratoContabilIdentifier(termoContratoContabilItem),
      );
      const termoContratoContabilsToAdd = termoContratoContabils.filter(termoContratoContabilItem => {
        const termoContratoContabilIdentifier = this.getTermoContratoContabilIdentifier(termoContratoContabilItem);
        if (termoContratoContabilCollectionIdentifiers.includes(termoContratoContabilIdentifier)) {
          return false;
        }
        termoContratoContabilCollectionIdentifiers.push(termoContratoContabilIdentifier);
        return true;
      });
      return [...termoContratoContabilsToAdd, ...termoContratoContabilCollection];
    }
    return termoContratoContabilCollection;
  }

  protected convertDateFromClient<T extends ITermoContratoContabil | NewTermoContratoContabil | PartialUpdateTermoContratoContabil>(
    termoContratoContabil: T,
  ): RestOf<T> {
    return {
      ...termoContratoContabil,
      dataCriacao: termoContratoContabil.dataCriacao?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restTermoContratoContabil: RestTermoContratoContabil): ITermoContratoContabil {
    return {
      ...restTermoContratoContabil,
      dataCriacao: restTermoContratoContabil.dataCriacao ? dayjs(restTermoContratoContabil.dataCriacao) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestTermoContratoContabil>): HttpResponse<ITermoContratoContabil> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestTermoContratoContabil[]>): HttpResponse<ITermoContratoContabil[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
