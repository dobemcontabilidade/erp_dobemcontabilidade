import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITermoAdesaoEmpresa, NewTermoAdesaoEmpresa } from '../termo-adesao-empresa.model';

export type PartialUpdateTermoAdesaoEmpresa = Partial<ITermoAdesaoEmpresa> & Pick<ITermoAdesaoEmpresa, 'id'>;

type RestOf<T extends ITermoAdesaoEmpresa | NewTermoAdesaoEmpresa> = Omit<T, 'dataAdesao'> & {
  dataAdesao?: string | null;
};

export type RestTermoAdesaoEmpresa = RestOf<ITermoAdesaoEmpresa>;

export type NewRestTermoAdesaoEmpresa = RestOf<NewTermoAdesaoEmpresa>;

export type PartialUpdateRestTermoAdesaoEmpresa = RestOf<PartialUpdateTermoAdesaoEmpresa>;

export type EntityResponseType = HttpResponse<ITermoAdesaoEmpresa>;
export type EntityArrayResponseType = HttpResponse<ITermoAdesaoEmpresa[]>;

@Injectable({ providedIn: 'root' })
export class TermoAdesaoEmpresaService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/termo-adesao-empresas');

  create(termoAdesaoEmpresa: NewTermoAdesaoEmpresa): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(termoAdesaoEmpresa);
    return this.http
      .post<RestTermoAdesaoEmpresa>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(termoAdesaoEmpresa: ITermoAdesaoEmpresa): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(termoAdesaoEmpresa);
    return this.http
      .put<RestTermoAdesaoEmpresa>(`${this.resourceUrl}/${this.getTermoAdesaoEmpresaIdentifier(termoAdesaoEmpresa)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(termoAdesaoEmpresa: PartialUpdateTermoAdesaoEmpresa): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(termoAdesaoEmpresa);
    return this.http
      .patch<RestTermoAdesaoEmpresa>(`${this.resourceUrl}/${this.getTermoAdesaoEmpresaIdentifier(termoAdesaoEmpresa)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestTermoAdesaoEmpresa>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestTermoAdesaoEmpresa[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getTermoAdesaoEmpresaIdentifier(termoAdesaoEmpresa: Pick<ITermoAdesaoEmpresa, 'id'>): number {
    return termoAdesaoEmpresa.id;
  }

  compareTermoAdesaoEmpresa(o1: Pick<ITermoAdesaoEmpresa, 'id'> | null, o2: Pick<ITermoAdesaoEmpresa, 'id'> | null): boolean {
    return o1 && o2 ? this.getTermoAdesaoEmpresaIdentifier(o1) === this.getTermoAdesaoEmpresaIdentifier(o2) : o1 === o2;
  }

  addTermoAdesaoEmpresaToCollectionIfMissing<Type extends Pick<ITermoAdesaoEmpresa, 'id'>>(
    termoAdesaoEmpresaCollection: Type[],
    ...termoAdesaoEmpresasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const termoAdesaoEmpresas: Type[] = termoAdesaoEmpresasToCheck.filter(isPresent);
    if (termoAdesaoEmpresas.length > 0) {
      const termoAdesaoEmpresaCollectionIdentifiers = termoAdesaoEmpresaCollection.map(termoAdesaoEmpresaItem =>
        this.getTermoAdesaoEmpresaIdentifier(termoAdesaoEmpresaItem),
      );
      const termoAdesaoEmpresasToAdd = termoAdesaoEmpresas.filter(termoAdesaoEmpresaItem => {
        const termoAdesaoEmpresaIdentifier = this.getTermoAdesaoEmpresaIdentifier(termoAdesaoEmpresaItem);
        if (termoAdesaoEmpresaCollectionIdentifiers.includes(termoAdesaoEmpresaIdentifier)) {
          return false;
        }
        termoAdesaoEmpresaCollectionIdentifiers.push(termoAdesaoEmpresaIdentifier);
        return true;
      });
      return [...termoAdesaoEmpresasToAdd, ...termoAdesaoEmpresaCollection];
    }
    return termoAdesaoEmpresaCollection;
  }

  protected convertDateFromClient<T extends ITermoAdesaoEmpresa | NewTermoAdesaoEmpresa | PartialUpdateTermoAdesaoEmpresa>(
    termoAdesaoEmpresa: T,
  ): RestOf<T> {
    return {
      ...termoAdesaoEmpresa,
      dataAdesao: termoAdesaoEmpresa.dataAdesao?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restTermoAdesaoEmpresa: RestTermoAdesaoEmpresa): ITermoAdesaoEmpresa {
    return {
      ...restTermoAdesaoEmpresa,
      dataAdesao: restTermoAdesaoEmpresa.dataAdesao ? dayjs(restTermoAdesaoEmpresa.dataAdesao) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestTermoAdesaoEmpresa>): HttpResponse<ITermoAdesaoEmpresa> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestTermoAdesaoEmpresa[]>): HttpResponse<ITermoAdesaoEmpresa[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
