import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITermoContratoAssinaturaEmpresa, NewTermoContratoAssinaturaEmpresa } from '../termo-contrato-assinatura-empresa.model';

export type PartialUpdateTermoContratoAssinaturaEmpresa = Partial<ITermoContratoAssinaturaEmpresa> &
  Pick<ITermoContratoAssinaturaEmpresa, 'id'>;

type RestOf<T extends ITermoContratoAssinaturaEmpresa | NewTermoContratoAssinaturaEmpresa> = Omit<
  T,
  'dataAssinatura' | 'dataEnvioEmail'
> & {
  dataAssinatura?: string | null;
  dataEnvioEmail?: string | null;
};

export type RestTermoContratoAssinaturaEmpresa = RestOf<ITermoContratoAssinaturaEmpresa>;

export type NewRestTermoContratoAssinaturaEmpresa = RestOf<NewTermoContratoAssinaturaEmpresa>;

export type PartialUpdateRestTermoContratoAssinaturaEmpresa = RestOf<PartialUpdateTermoContratoAssinaturaEmpresa>;

export type EntityResponseType = HttpResponse<ITermoContratoAssinaturaEmpresa>;
export type EntityArrayResponseType = HttpResponse<ITermoContratoAssinaturaEmpresa[]>;

@Injectable({ providedIn: 'root' })
export class TermoContratoAssinaturaEmpresaService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/termo-contrato-assinatura-empresas');

  create(termoContratoAssinaturaEmpresa: NewTermoContratoAssinaturaEmpresa): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(termoContratoAssinaturaEmpresa);
    return this.http
      .post<RestTermoContratoAssinaturaEmpresa>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(termoContratoAssinaturaEmpresa: ITermoContratoAssinaturaEmpresa): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(termoContratoAssinaturaEmpresa);
    return this.http
      .put<RestTermoContratoAssinaturaEmpresa>(
        `${this.resourceUrl}/${this.getTermoContratoAssinaturaEmpresaIdentifier(termoContratoAssinaturaEmpresa)}`,
        copy,
        { observe: 'response' },
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(termoContratoAssinaturaEmpresa: PartialUpdateTermoContratoAssinaturaEmpresa): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(termoContratoAssinaturaEmpresa);
    return this.http
      .patch<RestTermoContratoAssinaturaEmpresa>(
        `${this.resourceUrl}/${this.getTermoContratoAssinaturaEmpresaIdentifier(termoContratoAssinaturaEmpresa)}`,
        copy,
        { observe: 'response' },
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestTermoContratoAssinaturaEmpresa>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestTermoContratoAssinaturaEmpresa[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getTermoContratoAssinaturaEmpresaIdentifier(termoContratoAssinaturaEmpresa: Pick<ITermoContratoAssinaturaEmpresa, 'id'>): number {
    return termoContratoAssinaturaEmpresa.id;
  }

  compareTermoContratoAssinaturaEmpresa(
    o1: Pick<ITermoContratoAssinaturaEmpresa, 'id'> | null,
    o2: Pick<ITermoContratoAssinaturaEmpresa, 'id'> | null,
  ): boolean {
    return o1 && o2
      ? this.getTermoContratoAssinaturaEmpresaIdentifier(o1) === this.getTermoContratoAssinaturaEmpresaIdentifier(o2)
      : o1 === o2;
  }

  addTermoContratoAssinaturaEmpresaToCollectionIfMissing<Type extends Pick<ITermoContratoAssinaturaEmpresa, 'id'>>(
    termoContratoAssinaturaEmpresaCollection: Type[],
    ...termoContratoAssinaturaEmpresasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const termoContratoAssinaturaEmpresas: Type[] = termoContratoAssinaturaEmpresasToCheck.filter(isPresent);
    if (termoContratoAssinaturaEmpresas.length > 0) {
      const termoContratoAssinaturaEmpresaCollectionIdentifiers = termoContratoAssinaturaEmpresaCollection.map(
        termoContratoAssinaturaEmpresaItem => this.getTermoContratoAssinaturaEmpresaIdentifier(termoContratoAssinaturaEmpresaItem),
      );
      const termoContratoAssinaturaEmpresasToAdd = termoContratoAssinaturaEmpresas.filter(termoContratoAssinaturaEmpresaItem => {
        const termoContratoAssinaturaEmpresaIdentifier =
          this.getTermoContratoAssinaturaEmpresaIdentifier(termoContratoAssinaturaEmpresaItem);
        if (termoContratoAssinaturaEmpresaCollectionIdentifiers.includes(termoContratoAssinaturaEmpresaIdentifier)) {
          return false;
        }
        termoContratoAssinaturaEmpresaCollectionIdentifiers.push(termoContratoAssinaturaEmpresaIdentifier);
        return true;
      });
      return [...termoContratoAssinaturaEmpresasToAdd, ...termoContratoAssinaturaEmpresaCollection];
    }
    return termoContratoAssinaturaEmpresaCollection;
  }

  protected convertDateFromClient<
    T extends ITermoContratoAssinaturaEmpresa | NewTermoContratoAssinaturaEmpresa | PartialUpdateTermoContratoAssinaturaEmpresa,
  >(termoContratoAssinaturaEmpresa: T): RestOf<T> {
    return {
      ...termoContratoAssinaturaEmpresa,
      dataAssinatura: termoContratoAssinaturaEmpresa.dataAssinatura?.toJSON() ?? null,
      dataEnvioEmail: termoContratoAssinaturaEmpresa.dataEnvioEmail?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restTermoContratoAssinaturaEmpresa: RestTermoContratoAssinaturaEmpresa): ITermoContratoAssinaturaEmpresa {
    return {
      ...restTermoContratoAssinaturaEmpresa,
      dataAssinatura: restTermoContratoAssinaturaEmpresa.dataAssinatura
        ? dayjs(restTermoContratoAssinaturaEmpresa.dataAssinatura)
        : undefined,
      dataEnvioEmail: restTermoContratoAssinaturaEmpresa.dataEnvioEmail
        ? dayjs(restTermoContratoAssinaturaEmpresa.dataEnvioEmail)
        : undefined,
    };
  }

  protected convertResponseFromServer(
    res: HttpResponse<RestTermoContratoAssinaturaEmpresa>,
  ): HttpResponse<ITermoContratoAssinaturaEmpresa> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(
    res: HttpResponse<RestTermoContratoAssinaturaEmpresa[]>,
  ): HttpResponse<ITermoContratoAssinaturaEmpresa[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
