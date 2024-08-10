import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAssinaturaEmpresa, NewAssinaturaEmpresa } from '../assinatura-empresa.model';

export type PartialUpdateAssinaturaEmpresa = Partial<IAssinaturaEmpresa> & Pick<IAssinaturaEmpresa, 'id'>;

type RestOf<T extends IAssinaturaEmpresa | NewAssinaturaEmpresa> = Omit<T, 'dataContratacao' | 'dataEncerramento'> & {
  dataContratacao?: string | null;
  dataEncerramento?: string | null;
};

export type RestAssinaturaEmpresa = RestOf<IAssinaturaEmpresa>;

export type NewRestAssinaturaEmpresa = RestOf<NewAssinaturaEmpresa>;

export type PartialUpdateRestAssinaturaEmpresa = RestOf<PartialUpdateAssinaturaEmpresa>;

export type EntityResponseType = HttpResponse<IAssinaturaEmpresa>;
export type EntityArrayResponseType = HttpResponse<IAssinaturaEmpresa[]>;

@Injectable({ providedIn: 'root' })
export class AssinaturaEmpresaService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/assinatura-empresas');

  create(assinaturaEmpresa: NewAssinaturaEmpresa): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(assinaturaEmpresa);
    return this.http
      .post<RestAssinaturaEmpresa>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(assinaturaEmpresa: IAssinaturaEmpresa): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(assinaturaEmpresa);
    return this.http
      .put<RestAssinaturaEmpresa>(`${this.resourceUrl}/${this.getAssinaturaEmpresaIdentifier(assinaturaEmpresa)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(assinaturaEmpresa: PartialUpdateAssinaturaEmpresa): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(assinaturaEmpresa);
    return this.http
      .patch<RestAssinaturaEmpresa>(`${this.resourceUrl}/${this.getAssinaturaEmpresaIdentifier(assinaturaEmpresa)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestAssinaturaEmpresa>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestAssinaturaEmpresa[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAssinaturaEmpresaIdentifier(assinaturaEmpresa: Pick<IAssinaturaEmpresa, 'id'>): number {
    return assinaturaEmpresa.id;
  }

  compareAssinaturaEmpresa(o1: Pick<IAssinaturaEmpresa, 'id'> | null, o2: Pick<IAssinaturaEmpresa, 'id'> | null): boolean {
    return o1 && o2 ? this.getAssinaturaEmpresaIdentifier(o1) === this.getAssinaturaEmpresaIdentifier(o2) : o1 === o2;
  }

  addAssinaturaEmpresaToCollectionIfMissing<Type extends Pick<IAssinaturaEmpresa, 'id'>>(
    assinaturaEmpresaCollection: Type[],
    ...assinaturaEmpresasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const assinaturaEmpresas: Type[] = assinaturaEmpresasToCheck.filter(isPresent);
    if (assinaturaEmpresas.length > 0) {
      const assinaturaEmpresaCollectionIdentifiers = assinaturaEmpresaCollection.map(assinaturaEmpresaItem =>
        this.getAssinaturaEmpresaIdentifier(assinaturaEmpresaItem),
      );
      const assinaturaEmpresasToAdd = assinaturaEmpresas.filter(assinaturaEmpresaItem => {
        const assinaturaEmpresaIdentifier = this.getAssinaturaEmpresaIdentifier(assinaturaEmpresaItem);
        if (assinaturaEmpresaCollectionIdentifiers.includes(assinaturaEmpresaIdentifier)) {
          return false;
        }
        assinaturaEmpresaCollectionIdentifiers.push(assinaturaEmpresaIdentifier);
        return true;
      });
      return [...assinaturaEmpresasToAdd, ...assinaturaEmpresaCollection];
    }
    return assinaturaEmpresaCollection;
  }

  protected convertDateFromClient<T extends IAssinaturaEmpresa | NewAssinaturaEmpresa | PartialUpdateAssinaturaEmpresa>(
    assinaturaEmpresa: T,
  ): RestOf<T> {
    return {
      ...assinaturaEmpresa,
      dataContratacao: assinaturaEmpresa.dataContratacao?.toJSON() ?? null,
      dataEncerramento: assinaturaEmpresa.dataEncerramento?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restAssinaturaEmpresa: RestAssinaturaEmpresa): IAssinaturaEmpresa {
    return {
      ...restAssinaturaEmpresa,
      dataContratacao: restAssinaturaEmpresa.dataContratacao ? dayjs(restAssinaturaEmpresa.dataContratacao) : undefined,
      dataEncerramento: restAssinaturaEmpresa.dataEncerramento ? dayjs(restAssinaturaEmpresa.dataEncerramento) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestAssinaturaEmpresa>): HttpResponse<IAssinaturaEmpresa> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestAssinaturaEmpresa[]>): HttpResponse<IAssinaturaEmpresa[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
