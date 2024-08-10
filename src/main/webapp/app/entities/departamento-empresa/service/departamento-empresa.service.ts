import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDepartamentoEmpresa, NewDepartamentoEmpresa } from '../departamento-empresa.model';

export type PartialUpdateDepartamentoEmpresa = Partial<IDepartamentoEmpresa> & Pick<IDepartamentoEmpresa, 'id'>;

export type EntityResponseType = HttpResponse<IDepartamentoEmpresa>;
export type EntityArrayResponseType = HttpResponse<IDepartamentoEmpresa[]>;

@Injectable({ providedIn: 'root' })
export class DepartamentoEmpresaService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/departamento-empresas');

  create(departamentoEmpresa: NewDepartamentoEmpresa): Observable<EntityResponseType> {
    return this.http.post<IDepartamentoEmpresa>(this.resourceUrl, departamentoEmpresa, { observe: 'response' });
  }

  update(departamentoEmpresa: IDepartamentoEmpresa): Observable<EntityResponseType> {
    return this.http.put<IDepartamentoEmpresa>(
      `${this.resourceUrl}/${this.getDepartamentoEmpresaIdentifier(departamentoEmpresa)}`,
      departamentoEmpresa,
      { observe: 'response' },
    );
  }

  partialUpdate(departamentoEmpresa: PartialUpdateDepartamentoEmpresa): Observable<EntityResponseType> {
    return this.http.patch<IDepartamentoEmpresa>(
      `${this.resourceUrl}/${this.getDepartamentoEmpresaIdentifier(departamentoEmpresa)}`,
      departamentoEmpresa,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDepartamentoEmpresa>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDepartamentoEmpresa[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getDepartamentoEmpresaIdentifier(departamentoEmpresa: Pick<IDepartamentoEmpresa, 'id'>): number {
    return departamentoEmpresa.id;
  }

  compareDepartamentoEmpresa(o1: Pick<IDepartamentoEmpresa, 'id'> | null, o2: Pick<IDepartamentoEmpresa, 'id'> | null): boolean {
    return o1 && o2 ? this.getDepartamentoEmpresaIdentifier(o1) === this.getDepartamentoEmpresaIdentifier(o2) : o1 === o2;
  }

  addDepartamentoEmpresaToCollectionIfMissing<Type extends Pick<IDepartamentoEmpresa, 'id'>>(
    departamentoEmpresaCollection: Type[],
    ...departamentoEmpresasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const departamentoEmpresas: Type[] = departamentoEmpresasToCheck.filter(isPresent);
    if (departamentoEmpresas.length > 0) {
      const departamentoEmpresaCollectionIdentifiers = departamentoEmpresaCollection.map(departamentoEmpresaItem =>
        this.getDepartamentoEmpresaIdentifier(departamentoEmpresaItem),
      );
      const departamentoEmpresasToAdd = departamentoEmpresas.filter(departamentoEmpresaItem => {
        const departamentoEmpresaIdentifier = this.getDepartamentoEmpresaIdentifier(departamentoEmpresaItem);
        if (departamentoEmpresaCollectionIdentifiers.includes(departamentoEmpresaIdentifier)) {
          return false;
        }
        departamentoEmpresaCollectionIdentifiers.push(departamentoEmpresaIdentifier);
        return true;
      });
      return [...departamentoEmpresasToAdd, ...departamentoEmpresaCollection];
    }
    return departamentoEmpresaCollection;
  }
}
