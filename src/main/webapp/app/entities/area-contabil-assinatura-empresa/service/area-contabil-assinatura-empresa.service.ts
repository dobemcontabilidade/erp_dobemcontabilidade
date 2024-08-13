import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAreaContabilAssinaturaEmpresa, NewAreaContabilAssinaturaEmpresa } from '../area-contabil-assinatura-empresa.model';

export type PartialUpdateAreaContabilAssinaturaEmpresa = Partial<IAreaContabilAssinaturaEmpresa> &
  Pick<IAreaContabilAssinaturaEmpresa, 'id'>;

type RestOf<T extends IAreaContabilAssinaturaEmpresa | NewAreaContabilAssinaturaEmpresa> = Omit<T, 'dataAtribuicao' | 'dataRevogacao'> & {
  dataAtribuicao?: string | null;
  dataRevogacao?: string | null;
};

export type RestAreaContabilAssinaturaEmpresa = RestOf<IAreaContabilAssinaturaEmpresa>;

export type NewRestAreaContabilAssinaturaEmpresa = RestOf<NewAreaContabilAssinaturaEmpresa>;

export type PartialUpdateRestAreaContabilAssinaturaEmpresa = RestOf<PartialUpdateAreaContabilAssinaturaEmpresa>;

export type EntityResponseType = HttpResponse<IAreaContabilAssinaturaEmpresa>;
export type EntityArrayResponseType = HttpResponse<IAreaContabilAssinaturaEmpresa[]>;

@Injectable({ providedIn: 'root' })
export class AreaContabilAssinaturaEmpresaService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/area-contabil-assinatura-empresas');

  create(areaContabilAssinaturaEmpresa: NewAreaContabilAssinaturaEmpresa): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(areaContabilAssinaturaEmpresa);
    return this.http
      .post<RestAreaContabilAssinaturaEmpresa>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(areaContabilAssinaturaEmpresa: IAreaContabilAssinaturaEmpresa): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(areaContabilAssinaturaEmpresa);
    return this.http
      .put<RestAreaContabilAssinaturaEmpresa>(
        `${this.resourceUrl}/${this.getAreaContabilAssinaturaEmpresaIdentifier(areaContabilAssinaturaEmpresa)}`,
        copy,
        { observe: 'response' },
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(areaContabilAssinaturaEmpresa: PartialUpdateAreaContabilAssinaturaEmpresa): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(areaContabilAssinaturaEmpresa);
    return this.http
      .patch<RestAreaContabilAssinaturaEmpresa>(
        `${this.resourceUrl}/${this.getAreaContabilAssinaturaEmpresaIdentifier(areaContabilAssinaturaEmpresa)}`,
        copy,
        { observe: 'response' },
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestAreaContabilAssinaturaEmpresa>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestAreaContabilAssinaturaEmpresa[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAreaContabilAssinaturaEmpresaIdentifier(areaContabilAssinaturaEmpresa: Pick<IAreaContabilAssinaturaEmpresa, 'id'>): number {
    return areaContabilAssinaturaEmpresa.id;
  }

  compareAreaContabilAssinaturaEmpresa(
    o1: Pick<IAreaContabilAssinaturaEmpresa, 'id'> | null,
    o2: Pick<IAreaContabilAssinaturaEmpresa, 'id'> | null,
  ): boolean {
    return o1 && o2
      ? this.getAreaContabilAssinaturaEmpresaIdentifier(o1) === this.getAreaContabilAssinaturaEmpresaIdentifier(o2)
      : o1 === o2;
  }

  addAreaContabilAssinaturaEmpresaToCollectionIfMissing<Type extends Pick<IAreaContabilAssinaturaEmpresa, 'id'>>(
    areaContabilAssinaturaEmpresaCollection: Type[],
    ...areaContabilAssinaturaEmpresasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const areaContabilAssinaturaEmpresas: Type[] = areaContabilAssinaturaEmpresasToCheck.filter(isPresent);
    if (areaContabilAssinaturaEmpresas.length > 0) {
      const areaContabilAssinaturaEmpresaCollectionIdentifiers = areaContabilAssinaturaEmpresaCollection.map(
        areaContabilAssinaturaEmpresaItem => this.getAreaContabilAssinaturaEmpresaIdentifier(areaContabilAssinaturaEmpresaItem),
      );
      const areaContabilAssinaturaEmpresasToAdd = areaContabilAssinaturaEmpresas.filter(areaContabilAssinaturaEmpresaItem => {
        const areaContabilAssinaturaEmpresaIdentifier = this.getAreaContabilAssinaturaEmpresaIdentifier(areaContabilAssinaturaEmpresaItem);
        if (areaContabilAssinaturaEmpresaCollectionIdentifiers.includes(areaContabilAssinaturaEmpresaIdentifier)) {
          return false;
        }
        areaContabilAssinaturaEmpresaCollectionIdentifiers.push(areaContabilAssinaturaEmpresaIdentifier);
        return true;
      });
      return [...areaContabilAssinaturaEmpresasToAdd, ...areaContabilAssinaturaEmpresaCollection];
    }
    return areaContabilAssinaturaEmpresaCollection;
  }

  protected convertDateFromClient<
    T extends IAreaContabilAssinaturaEmpresa | NewAreaContabilAssinaturaEmpresa | PartialUpdateAreaContabilAssinaturaEmpresa,
  >(areaContabilAssinaturaEmpresa: T): RestOf<T> {
    return {
      ...areaContabilAssinaturaEmpresa,
      dataAtribuicao: areaContabilAssinaturaEmpresa.dataAtribuicao?.toJSON() ?? null,
      dataRevogacao: areaContabilAssinaturaEmpresa.dataRevogacao?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restAreaContabilAssinaturaEmpresa: RestAreaContabilAssinaturaEmpresa): IAreaContabilAssinaturaEmpresa {
    return {
      ...restAreaContabilAssinaturaEmpresa,
      dataAtribuicao: restAreaContabilAssinaturaEmpresa.dataAtribuicao
        ? dayjs(restAreaContabilAssinaturaEmpresa.dataAtribuicao)
        : undefined,
      dataRevogacao: restAreaContabilAssinaturaEmpresa.dataRevogacao ? dayjs(restAreaContabilAssinaturaEmpresa.dataRevogacao) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestAreaContabilAssinaturaEmpresa>): HttpResponse<IAreaContabilAssinaturaEmpresa> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(
    res: HttpResponse<RestAreaContabilAssinaturaEmpresa[]>,
  ): HttpResponse<IAreaContabilAssinaturaEmpresa[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
