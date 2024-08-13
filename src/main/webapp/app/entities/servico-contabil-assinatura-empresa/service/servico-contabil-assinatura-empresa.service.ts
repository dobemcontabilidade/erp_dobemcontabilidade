import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IServicoContabilAssinaturaEmpresa, NewServicoContabilAssinaturaEmpresa } from '../servico-contabil-assinatura-empresa.model';

export type PartialUpdateServicoContabilAssinaturaEmpresa = Partial<IServicoContabilAssinaturaEmpresa> &
  Pick<IServicoContabilAssinaturaEmpresa, 'id'>;

type RestOf<T extends IServicoContabilAssinaturaEmpresa | NewServicoContabilAssinaturaEmpresa> = Omit<T, 'dataLegal' | 'dataAdmin'> & {
  dataLegal?: string | null;
  dataAdmin?: string | null;
};

export type RestServicoContabilAssinaturaEmpresa = RestOf<IServicoContabilAssinaturaEmpresa>;

export type NewRestServicoContabilAssinaturaEmpresa = RestOf<NewServicoContabilAssinaturaEmpresa>;

export type PartialUpdateRestServicoContabilAssinaturaEmpresa = RestOf<PartialUpdateServicoContabilAssinaturaEmpresa>;

export type EntityResponseType = HttpResponse<IServicoContabilAssinaturaEmpresa>;
export type EntityArrayResponseType = HttpResponse<IServicoContabilAssinaturaEmpresa[]>;

@Injectable({ providedIn: 'root' })
export class ServicoContabilAssinaturaEmpresaService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/servico-contabil-assinatura-empresas');

  create(servicoContabilAssinaturaEmpresa: NewServicoContabilAssinaturaEmpresa): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(servicoContabilAssinaturaEmpresa);
    return this.http
      .post<RestServicoContabilAssinaturaEmpresa>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(servicoContabilAssinaturaEmpresa: IServicoContabilAssinaturaEmpresa): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(servicoContabilAssinaturaEmpresa);
    return this.http
      .put<RestServicoContabilAssinaturaEmpresa>(
        `${this.resourceUrl}/${this.getServicoContabilAssinaturaEmpresaIdentifier(servicoContabilAssinaturaEmpresa)}`,
        copy,
        { observe: 'response' },
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(servicoContabilAssinaturaEmpresa: PartialUpdateServicoContabilAssinaturaEmpresa): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(servicoContabilAssinaturaEmpresa);
    return this.http
      .patch<RestServicoContabilAssinaturaEmpresa>(
        `${this.resourceUrl}/${this.getServicoContabilAssinaturaEmpresaIdentifier(servicoContabilAssinaturaEmpresa)}`,
        copy,
        { observe: 'response' },
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestServicoContabilAssinaturaEmpresa>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestServicoContabilAssinaturaEmpresa[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getServicoContabilAssinaturaEmpresaIdentifier(servicoContabilAssinaturaEmpresa: Pick<IServicoContabilAssinaturaEmpresa, 'id'>): number {
    return servicoContabilAssinaturaEmpresa.id;
  }

  compareServicoContabilAssinaturaEmpresa(
    o1: Pick<IServicoContabilAssinaturaEmpresa, 'id'> | null,
    o2: Pick<IServicoContabilAssinaturaEmpresa, 'id'> | null,
  ): boolean {
    return o1 && o2
      ? this.getServicoContabilAssinaturaEmpresaIdentifier(o1) === this.getServicoContabilAssinaturaEmpresaIdentifier(o2)
      : o1 === o2;
  }

  addServicoContabilAssinaturaEmpresaToCollectionIfMissing<Type extends Pick<IServicoContabilAssinaturaEmpresa, 'id'>>(
    servicoContabilAssinaturaEmpresaCollection: Type[],
    ...servicoContabilAssinaturaEmpresasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const servicoContabilAssinaturaEmpresas: Type[] = servicoContabilAssinaturaEmpresasToCheck.filter(isPresent);
    if (servicoContabilAssinaturaEmpresas.length > 0) {
      const servicoContabilAssinaturaEmpresaCollectionIdentifiers = servicoContabilAssinaturaEmpresaCollection.map(
        servicoContabilAssinaturaEmpresaItem => this.getServicoContabilAssinaturaEmpresaIdentifier(servicoContabilAssinaturaEmpresaItem),
      );
      const servicoContabilAssinaturaEmpresasToAdd = servicoContabilAssinaturaEmpresas.filter(servicoContabilAssinaturaEmpresaItem => {
        const servicoContabilAssinaturaEmpresaIdentifier = this.getServicoContabilAssinaturaEmpresaIdentifier(
          servicoContabilAssinaturaEmpresaItem,
        );
        if (servicoContabilAssinaturaEmpresaCollectionIdentifiers.includes(servicoContabilAssinaturaEmpresaIdentifier)) {
          return false;
        }
        servicoContabilAssinaturaEmpresaCollectionIdentifiers.push(servicoContabilAssinaturaEmpresaIdentifier);
        return true;
      });
      return [...servicoContabilAssinaturaEmpresasToAdd, ...servicoContabilAssinaturaEmpresaCollection];
    }
    return servicoContabilAssinaturaEmpresaCollection;
  }

  protected convertDateFromClient<
    T extends IServicoContabilAssinaturaEmpresa | NewServicoContabilAssinaturaEmpresa | PartialUpdateServicoContabilAssinaturaEmpresa,
  >(servicoContabilAssinaturaEmpresa: T): RestOf<T> {
    return {
      ...servicoContabilAssinaturaEmpresa,
      dataLegal: servicoContabilAssinaturaEmpresa.dataLegal?.toJSON() ?? null,
      dataAdmin: servicoContabilAssinaturaEmpresa.dataAdmin?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(
    restServicoContabilAssinaturaEmpresa: RestServicoContabilAssinaturaEmpresa,
  ): IServicoContabilAssinaturaEmpresa {
    return {
      ...restServicoContabilAssinaturaEmpresa,
      dataLegal: restServicoContabilAssinaturaEmpresa.dataLegal ? dayjs(restServicoContabilAssinaturaEmpresa.dataLegal) : undefined,
      dataAdmin: restServicoContabilAssinaturaEmpresa.dataAdmin ? dayjs(restServicoContabilAssinaturaEmpresa.dataAdmin) : undefined,
    };
  }

  protected convertResponseFromServer(
    res: HttpResponse<RestServicoContabilAssinaturaEmpresa>,
  ): HttpResponse<IServicoContabilAssinaturaEmpresa> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(
    res: HttpResponse<RestServicoContabilAssinaturaEmpresa[]>,
  ): HttpResponse<IServicoContabilAssinaturaEmpresa[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
