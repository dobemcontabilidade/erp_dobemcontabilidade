import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITermoAdesaoContador, NewTermoAdesaoContador } from '../termo-adesao-contador.model';

export type PartialUpdateTermoAdesaoContador = Partial<ITermoAdesaoContador> & Pick<ITermoAdesaoContador, 'id'>;

type RestOf<T extends ITermoAdesaoContador | NewTermoAdesaoContador> = Omit<T, 'dataAdesao'> & {
  dataAdesao?: string | null;
};

export type RestTermoAdesaoContador = RestOf<ITermoAdesaoContador>;

export type NewRestTermoAdesaoContador = RestOf<NewTermoAdesaoContador>;

export type PartialUpdateRestTermoAdesaoContador = RestOf<PartialUpdateTermoAdesaoContador>;

export type EntityResponseType = HttpResponse<ITermoAdesaoContador>;
export type EntityArrayResponseType = HttpResponse<ITermoAdesaoContador[]>;

@Injectable({ providedIn: 'root' })
export class TermoAdesaoContadorService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/termo-adesao-contadors');

  create(termoAdesaoContador: NewTermoAdesaoContador): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(termoAdesaoContador);
    return this.http
      .post<RestTermoAdesaoContador>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(termoAdesaoContador: ITermoAdesaoContador): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(termoAdesaoContador);
    return this.http
      .put<RestTermoAdesaoContador>(`${this.resourceUrl}/${this.getTermoAdesaoContadorIdentifier(termoAdesaoContador)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(termoAdesaoContador: PartialUpdateTermoAdesaoContador): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(termoAdesaoContador);
    return this.http
      .patch<RestTermoAdesaoContador>(`${this.resourceUrl}/${this.getTermoAdesaoContadorIdentifier(termoAdesaoContador)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestTermoAdesaoContador>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestTermoAdesaoContador[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getTermoAdesaoContadorIdentifier(termoAdesaoContador: Pick<ITermoAdesaoContador, 'id'>): number {
    return termoAdesaoContador.id;
  }

  compareTermoAdesaoContador(o1: Pick<ITermoAdesaoContador, 'id'> | null, o2: Pick<ITermoAdesaoContador, 'id'> | null): boolean {
    return o1 && o2 ? this.getTermoAdesaoContadorIdentifier(o1) === this.getTermoAdesaoContadorIdentifier(o2) : o1 === o2;
  }

  addTermoAdesaoContadorToCollectionIfMissing<Type extends Pick<ITermoAdesaoContador, 'id'>>(
    termoAdesaoContadorCollection: Type[],
    ...termoAdesaoContadorsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const termoAdesaoContadors: Type[] = termoAdesaoContadorsToCheck.filter(isPresent);
    if (termoAdesaoContadors.length > 0) {
      const termoAdesaoContadorCollectionIdentifiers = termoAdesaoContadorCollection.map(termoAdesaoContadorItem =>
        this.getTermoAdesaoContadorIdentifier(termoAdesaoContadorItem),
      );
      const termoAdesaoContadorsToAdd = termoAdesaoContadors.filter(termoAdesaoContadorItem => {
        const termoAdesaoContadorIdentifier = this.getTermoAdesaoContadorIdentifier(termoAdesaoContadorItem);
        if (termoAdesaoContadorCollectionIdentifiers.includes(termoAdesaoContadorIdentifier)) {
          return false;
        }
        termoAdesaoContadorCollectionIdentifiers.push(termoAdesaoContadorIdentifier);
        return true;
      });
      return [...termoAdesaoContadorsToAdd, ...termoAdesaoContadorCollection];
    }
    return termoAdesaoContadorCollection;
  }

  protected convertDateFromClient<T extends ITermoAdesaoContador | NewTermoAdesaoContador | PartialUpdateTermoAdesaoContador>(
    termoAdesaoContador: T,
  ): RestOf<T> {
    return {
      ...termoAdesaoContador,
      dataAdesao: termoAdesaoContador.dataAdesao?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restTermoAdesaoContador: RestTermoAdesaoContador): ITermoAdesaoContador {
    return {
      ...restTermoAdesaoContador,
      dataAdesao: restTermoAdesaoContador.dataAdesao ? dayjs(restTermoAdesaoContador.dataAdesao) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestTermoAdesaoContador>): HttpResponse<ITermoAdesaoContador> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestTermoAdesaoContador[]>): HttpResponse<ITermoAdesaoContador[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
