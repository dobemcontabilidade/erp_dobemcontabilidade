import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDocsEmpresa, NewDocsEmpresa } from '../docs-empresa.model';

export type PartialUpdateDocsEmpresa = Partial<IDocsEmpresa> & Pick<IDocsEmpresa, 'id'>;

type RestOf<T extends IDocsEmpresa | NewDocsEmpresa> = Omit<T, 'dataEmissao' | 'dataEncerramento'> & {
  dataEmissao?: string | null;
  dataEncerramento?: string | null;
};

export type RestDocsEmpresa = RestOf<IDocsEmpresa>;

export type NewRestDocsEmpresa = RestOf<NewDocsEmpresa>;

export type PartialUpdateRestDocsEmpresa = RestOf<PartialUpdateDocsEmpresa>;

export type EntityResponseType = HttpResponse<IDocsEmpresa>;
export type EntityArrayResponseType = HttpResponse<IDocsEmpresa[]>;

@Injectable({ providedIn: 'root' })
export class DocsEmpresaService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/docs-empresas');

  create(docsEmpresa: NewDocsEmpresa): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(docsEmpresa);
    return this.http
      .post<RestDocsEmpresa>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(docsEmpresa: IDocsEmpresa): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(docsEmpresa);
    return this.http
      .put<RestDocsEmpresa>(`${this.resourceUrl}/${this.getDocsEmpresaIdentifier(docsEmpresa)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(docsEmpresa: PartialUpdateDocsEmpresa): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(docsEmpresa);
    return this.http
      .patch<RestDocsEmpresa>(`${this.resourceUrl}/${this.getDocsEmpresaIdentifier(docsEmpresa)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestDocsEmpresa>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestDocsEmpresa[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getDocsEmpresaIdentifier(docsEmpresa: Pick<IDocsEmpresa, 'id'>): number {
    return docsEmpresa.id;
  }

  compareDocsEmpresa(o1: Pick<IDocsEmpresa, 'id'> | null, o2: Pick<IDocsEmpresa, 'id'> | null): boolean {
    return o1 && o2 ? this.getDocsEmpresaIdentifier(o1) === this.getDocsEmpresaIdentifier(o2) : o1 === o2;
  }

  addDocsEmpresaToCollectionIfMissing<Type extends Pick<IDocsEmpresa, 'id'>>(
    docsEmpresaCollection: Type[],
    ...docsEmpresasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const docsEmpresas: Type[] = docsEmpresasToCheck.filter(isPresent);
    if (docsEmpresas.length > 0) {
      const docsEmpresaCollectionIdentifiers = docsEmpresaCollection.map(docsEmpresaItem => this.getDocsEmpresaIdentifier(docsEmpresaItem));
      const docsEmpresasToAdd = docsEmpresas.filter(docsEmpresaItem => {
        const docsEmpresaIdentifier = this.getDocsEmpresaIdentifier(docsEmpresaItem);
        if (docsEmpresaCollectionIdentifiers.includes(docsEmpresaIdentifier)) {
          return false;
        }
        docsEmpresaCollectionIdentifiers.push(docsEmpresaIdentifier);
        return true;
      });
      return [...docsEmpresasToAdd, ...docsEmpresaCollection];
    }
    return docsEmpresaCollection;
  }

  protected convertDateFromClient<T extends IDocsEmpresa | NewDocsEmpresa | PartialUpdateDocsEmpresa>(docsEmpresa: T): RestOf<T> {
    return {
      ...docsEmpresa,
      dataEmissao: docsEmpresa.dataEmissao?.toJSON() ?? null,
      dataEncerramento: docsEmpresa.dataEncerramento?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restDocsEmpresa: RestDocsEmpresa): IDocsEmpresa {
    return {
      ...restDocsEmpresa,
      dataEmissao: restDocsEmpresa.dataEmissao ? dayjs(restDocsEmpresa.dataEmissao) : undefined,
      dataEncerramento: restDocsEmpresa.dataEncerramento ? dayjs(restDocsEmpresa.dataEncerramento) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestDocsEmpresa>): HttpResponse<IDocsEmpresa> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestDocsEmpresa[]>): HttpResponse<IDocsEmpresa[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
