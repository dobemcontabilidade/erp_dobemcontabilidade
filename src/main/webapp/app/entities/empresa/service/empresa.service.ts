import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEmpresa, NewEmpresa } from '../empresa.model';

export type PartialUpdateEmpresa = Partial<IEmpresa> & Pick<IEmpresa, 'id'>;

type RestOf<T extends IEmpresa | NewEmpresa> = Omit<T, 'dataAbertura'> & {
  dataAbertura?: string | null;
};

export type RestEmpresa = RestOf<IEmpresa>;

export type NewRestEmpresa = RestOf<NewEmpresa>;

export type PartialUpdateRestEmpresa = RestOf<PartialUpdateEmpresa>;

export type EntityResponseType = HttpResponse<IEmpresa>;
export type EntityArrayResponseType = HttpResponse<IEmpresa[]>;

@Injectable({ providedIn: 'root' })
export class EmpresaService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/empresas');

  create(empresa: NewEmpresa): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(empresa);
    return this.http
      .post<RestEmpresa>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(empresa: IEmpresa): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(empresa);
    return this.http
      .put<RestEmpresa>(`${this.resourceUrl}/${this.getEmpresaIdentifier(empresa)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(empresa: PartialUpdateEmpresa): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(empresa);
    return this.http
      .patch<RestEmpresa>(`${this.resourceUrl}/${this.getEmpresaIdentifier(empresa)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestEmpresa>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestEmpresa[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getEmpresaIdentifier(empresa: Pick<IEmpresa, 'id'>): number {
    return empresa.id;
  }

  compareEmpresa(o1: Pick<IEmpresa, 'id'> | null, o2: Pick<IEmpresa, 'id'> | null): boolean {
    return o1 && o2 ? this.getEmpresaIdentifier(o1) === this.getEmpresaIdentifier(o2) : o1 === o2;
  }

  addEmpresaToCollectionIfMissing<Type extends Pick<IEmpresa, 'id'>>(
    empresaCollection: Type[],
    ...empresasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const empresas: Type[] = empresasToCheck.filter(isPresent);
    if (empresas.length > 0) {
      const empresaCollectionIdentifiers = empresaCollection.map(empresaItem => this.getEmpresaIdentifier(empresaItem));
      const empresasToAdd = empresas.filter(empresaItem => {
        const empresaIdentifier = this.getEmpresaIdentifier(empresaItem);
        if (empresaCollectionIdentifiers.includes(empresaIdentifier)) {
          return false;
        }
        empresaCollectionIdentifiers.push(empresaIdentifier);
        return true;
      });
      return [...empresasToAdd, ...empresaCollection];
    }
    return empresaCollection;
  }

  protected convertDateFromClient<T extends IEmpresa | NewEmpresa | PartialUpdateEmpresa>(empresa: T): RestOf<T> {
    return {
      ...empresa,
      dataAbertura: empresa.dataAbertura?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restEmpresa: RestEmpresa): IEmpresa {
    return {
      ...restEmpresa,
      dataAbertura: restEmpresa.dataAbertura ? dayjs(restEmpresa.dataAbertura) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestEmpresa>): HttpResponse<IEmpresa> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestEmpresa[]>): HttpResponse<IEmpresa[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
