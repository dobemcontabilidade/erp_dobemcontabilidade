import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICobrancaEmpresa, NewCobrancaEmpresa } from '../cobranca-empresa.model';

export type PartialUpdateCobrancaEmpresa = Partial<ICobrancaEmpresa> & Pick<ICobrancaEmpresa, 'id'>;

type RestOf<T extends ICobrancaEmpresa | NewCobrancaEmpresa> = Omit<T, 'dataCobranca'> & {
  dataCobranca?: string | null;
};

export type RestCobrancaEmpresa = RestOf<ICobrancaEmpresa>;

export type NewRestCobrancaEmpresa = RestOf<NewCobrancaEmpresa>;

export type PartialUpdateRestCobrancaEmpresa = RestOf<PartialUpdateCobrancaEmpresa>;

export type EntityResponseType = HttpResponse<ICobrancaEmpresa>;
export type EntityArrayResponseType = HttpResponse<ICobrancaEmpresa[]>;

@Injectable({ providedIn: 'root' })
export class CobrancaEmpresaService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/cobranca-empresas');

  create(cobrancaEmpresa: NewCobrancaEmpresa): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(cobrancaEmpresa);
    return this.http
      .post<RestCobrancaEmpresa>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(cobrancaEmpresa: ICobrancaEmpresa): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(cobrancaEmpresa);
    return this.http
      .put<RestCobrancaEmpresa>(`${this.resourceUrl}/${this.getCobrancaEmpresaIdentifier(cobrancaEmpresa)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(cobrancaEmpresa: PartialUpdateCobrancaEmpresa): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(cobrancaEmpresa);
    return this.http
      .patch<RestCobrancaEmpresa>(`${this.resourceUrl}/${this.getCobrancaEmpresaIdentifier(cobrancaEmpresa)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestCobrancaEmpresa>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestCobrancaEmpresa[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCobrancaEmpresaIdentifier(cobrancaEmpresa: Pick<ICobrancaEmpresa, 'id'>): number {
    return cobrancaEmpresa.id;
  }

  compareCobrancaEmpresa(o1: Pick<ICobrancaEmpresa, 'id'> | null, o2: Pick<ICobrancaEmpresa, 'id'> | null): boolean {
    return o1 && o2 ? this.getCobrancaEmpresaIdentifier(o1) === this.getCobrancaEmpresaIdentifier(o2) : o1 === o2;
  }

  addCobrancaEmpresaToCollectionIfMissing<Type extends Pick<ICobrancaEmpresa, 'id'>>(
    cobrancaEmpresaCollection: Type[],
    ...cobrancaEmpresasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const cobrancaEmpresas: Type[] = cobrancaEmpresasToCheck.filter(isPresent);
    if (cobrancaEmpresas.length > 0) {
      const cobrancaEmpresaCollectionIdentifiers = cobrancaEmpresaCollection.map(cobrancaEmpresaItem =>
        this.getCobrancaEmpresaIdentifier(cobrancaEmpresaItem),
      );
      const cobrancaEmpresasToAdd = cobrancaEmpresas.filter(cobrancaEmpresaItem => {
        const cobrancaEmpresaIdentifier = this.getCobrancaEmpresaIdentifier(cobrancaEmpresaItem);
        if (cobrancaEmpresaCollectionIdentifiers.includes(cobrancaEmpresaIdentifier)) {
          return false;
        }
        cobrancaEmpresaCollectionIdentifiers.push(cobrancaEmpresaIdentifier);
        return true;
      });
      return [...cobrancaEmpresasToAdd, ...cobrancaEmpresaCollection];
    }
    return cobrancaEmpresaCollection;
  }

  protected convertDateFromClient<T extends ICobrancaEmpresa | NewCobrancaEmpresa | PartialUpdateCobrancaEmpresa>(
    cobrancaEmpresa: T,
  ): RestOf<T> {
    return {
      ...cobrancaEmpresa,
      dataCobranca: cobrancaEmpresa.dataCobranca?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restCobrancaEmpresa: RestCobrancaEmpresa): ICobrancaEmpresa {
    return {
      ...restCobrancaEmpresa,
      dataCobranca: restCobrancaEmpresa.dataCobranca ? dayjs(restCobrancaEmpresa.dataCobranca) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestCobrancaEmpresa>): HttpResponse<ICobrancaEmpresa> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestCobrancaEmpresa[]>): HttpResponse<ICobrancaEmpresa[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
