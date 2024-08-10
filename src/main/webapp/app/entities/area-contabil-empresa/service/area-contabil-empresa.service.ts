import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAreaContabilEmpresa, NewAreaContabilEmpresa } from '../area-contabil-empresa.model';

export type PartialUpdateAreaContabilEmpresa = Partial<IAreaContabilEmpresa> & Pick<IAreaContabilEmpresa, 'id'>;

export type EntityResponseType = HttpResponse<IAreaContabilEmpresa>;
export type EntityArrayResponseType = HttpResponse<IAreaContabilEmpresa[]>;

@Injectable({ providedIn: 'root' })
export class AreaContabilEmpresaService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/area-contabil-empresas');

  create(areaContabilEmpresa: NewAreaContabilEmpresa): Observable<EntityResponseType> {
    return this.http.post<IAreaContabilEmpresa>(this.resourceUrl, areaContabilEmpresa, { observe: 'response' });
  }

  update(areaContabilEmpresa: IAreaContabilEmpresa): Observable<EntityResponseType> {
    return this.http.put<IAreaContabilEmpresa>(
      `${this.resourceUrl}/${this.getAreaContabilEmpresaIdentifier(areaContabilEmpresa)}`,
      areaContabilEmpresa,
      { observe: 'response' },
    );
  }

  partialUpdate(areaContabilEmpresa: PartialUpdateAreaContabilEmpresa): Observable<EntityResponseType> {
    return this.http.patch<IAreaContabilEmpresa>(
      `${this.resourceUrl}/${this.getAreaContabilEmpresaIdentifier(areaContabilEmpresa)}`,
      areaContabilEmpresa,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAreaContabilEmpresa>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAreaContabilEmpresa[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAreaContabilEmpresaIdentifier(areaContabilEmpresa: Pick<IAreaContabilEmpresa, 'id'>): number {
    return areaContabilEmpresa.id;
  }

  compareAreaContabilEmpresa(o1: Pick<IAreaContabilEmpresa, 'id'> | null, o2: Pick<IAreaContabilEmpresa, 'id'> | null): boolean {
    return o1 && o2 ? this.getAreaContabilEmpresaIdentifier(o1) === this.getAreaContabilEmpresaIdentifier(o2) : o1 === o2;
  }

  addAreaContabilEmpresaToCollectionIfMissing<Type extends Pick<IAreaContabilEmpresa, 'id'>>(
    areaContabilEmpresaCollection: Type[],
    ...areaContabilEmpresasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const areaContabilEmpresas: Type[] = areaContabilEmpresasToCheck.filter(isPresent);
    if (areaContabilEmpresas.length > 0) {
      const areaContabilEmpresaCollectionIdentifiers = areaContabilEmpresaCollection.map(areaContabilEmpresaItem =>
        this.getAreaContabilEmpresaIdentifier(areaContabilEmpresaItem),
      );
      const areaContabilEmpresasToAdd = areaContabilEmpresas.filter(areaContabilEmpresaItem => {
        const areaContabilEmpresaIdentifier = this.getAreaContabilEmpresaIdentifier(areaContabilEmpresaItem);
        if (areaContabilEmpresaCollectionIdentifiers.includes(areaContabilEmpresaIdentifier)) {
          return false;
        }
        areaContabilEmpresaCollectionIdentifiers.push(areaContabilEmpresaIdentifier);
        return true;
      });
      return [...areaContabilEmpresasToAdd, ...areaContabilEmpresaCollection];
    }
    return areaContabilEmpresaCollection;
  }
}
