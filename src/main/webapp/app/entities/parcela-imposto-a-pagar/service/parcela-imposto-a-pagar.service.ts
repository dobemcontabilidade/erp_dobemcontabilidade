import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IParcelaImpostoAPagar, NewParcelaImpostoAPagar } from '../parcela-imposto-a-pagar.model';

export type PartialUpdateParcelaImpostoAPagar = Partial<IParcelaImpostoAPagar> & Pick<IParcelaImpostoAPagar, 'id'>;

type RestOf<T extends IParcelaImpostoAPagar | NewParcelaImpostoAPagar> = Omit<T, 'dataVencimento' | 'dataPagamento'> & {
  dataVencimento?: string | null;
  dataPagamento?: string | null;
};

export type RestParcelaImpostoAPagar = RestOf<IParcelaImpostoAPagar>;

export type NewRestParcelaImpostoAPagar = RestOf<NewParcelaImpostoAPagar>;

export type PartialUpdateRestParcelaImpostoAPagar = RestOf<PartialUpdateParcelaImpostoAPagar>;

export type EntityResponseType = HttpResponse<IParcelaImpostoAPagar>;
export type EntityArrayResponseType = HttpResponse<IParcelaImpostoAPagar[]>;

@Injectable({ providedIn: 'root' })
export class ParcelaImpostoAPagarService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/parcela-imposto-a-pagars');

  create(parcelaImpostoAPagar: NewParcelaImpostoAPagar): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(parcelaImpostoAPagar);
    return this.http
      .post<RestParcelaImpostoAPagar>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(parcelaImpostoAPagar: IParcelaImpostoAPagar): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(parcelaImpostoAPagar);
    return this.http
      .put<RestParcelaImpostoAPagar>(`${this.resourceUrl}/${this.getParcelaImpostoAPagarIdentifier(parcelaImpostoAPagar)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(parcelaImpostoAPagar: PartialUpdateParcelaImpostoAPagar): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(parcelaImpostoAPagar);
    return this.http
      .patch<RestParcelaImpostoAPagar>(`${this.resourceUrl}/${this.getParcelaImpostoAPagarIdentifier(parcelaImpostoAPagar)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestParcelaImpostoAPagar>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestParcelaImpostoAPagar[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getParcelaImpostoAPagarIdentifier(parcelaImpostoAPagar: Pick<IParcelaImpostoAPagar, 'id'>): number {
    return parcelaImpostoAPagar.id;
  }

  compareParcelaImpostoAPagar(o1: Pick<IParcelaImpostoAPagar, 'id'> | null, o2: Pick<IParcelaImpostoAPagar, 'id'> | null): boolean {
    return o1 && o2 ? this.getParcelaImpostoAPagarIdentifier(o1) === this.getParcelaImpostoAPagarIdentifier(o2) : o1 === o2;
  }

  addParcelaImpostoAPagarToCollectionIfMissing<Type extends Pick<IParcelaImpostoAPagar, 'id'>>(
    parcelaImpostoAPagarCollection: Type[],
    ...parcelaImpostoAPagarsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const parcelaImpostoAPagars: Type[] = parcelaImpostoAPagarsToCheck.filter(isPresent);
    if (parcelaImpostoAPagars.length > 0) {
      const parcelaImpostoAPagarCollectionIdentifiers = parcelaImpostoAPagarCollection.map(parcelaImpostoAPagarItem =>
        this.getParcelaImpostoAPagarIdentifier(parcelaImpostoAPagarItem),
      );
      const parcelaImpostoAPagarsToAdd = parcelaImpostoAPagars.filter(parcelaImpostoAPagarItem => {
        const parcelaImpostoAPagarIdentifier = this.getParcelaImpostoAPagarIdentifier(parcelaImpostoAPagarItem);
        if (parcelaImpostoAPagarCollectionIdentifiers.includes(parcelaImpostoAPagarIdentifier)) {
          return false;
        }
        parcelaImpostoAPagarCollectionIdentifiers.push(parcelaImpostoAPagarIdentifier);
        return true;
      });
      return [...parcelaImpostoAPagarsToAdd, ...parcelaImpostoAPagarCollection];
    }
    return parcelaImpostoAPagarCollection;
  }

  protected convertDateFromClient<T extends IParcelaImpostoAPagar | NewParcelaImpostoAPagar | PartialUpdateParcelaImpostoAPagar>(
    parcelaImpostoAPagar: T,
  ): RestOf<T> {
    return {
      ...parcelaImpostoAPagar,
      dataVencimento: parcelaImpostoAPagar.dataVencimento?.toJSON() ?? null,
      dataPagamento: parcelaImpostoAPagar.dataPagamento?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restParcelaImpostoAPagar: RestParcelaImpostoAPagar): IParcelaImpostoAPagar {
    return {
      ...restParcelaImpostoAPagar,
      dataVencimento: restParcelaImpostoAPagar.dataVencimento ? dayjs(restParcelaImpostoAPagar.dataVencimento) : undefined,
      dataPagamento: restParcelaImpostoAPagar.dataPagamento ? dayjs(restParcelaImpostoAPagar.dataPagamento) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestParcelaImpostoAPagar>): HttpResponse<IParcelaImpostoAPagar> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestParcelaImpostoAPagar[]>): HttpResponse<IParcelaImpostoAPagar[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
