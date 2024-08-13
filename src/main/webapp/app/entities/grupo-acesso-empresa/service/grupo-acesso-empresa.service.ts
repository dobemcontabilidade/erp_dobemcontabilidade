import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IGrupoAcessoEmpresa, NewGrupoAcessoEmpresa } from '../grupo-acesso-empresa.model';

export type PartialUpdateGrupoAcessoEmpresa = Partial<IGrupoAcessoEmpresa> & Pick<IGrupoAcessoEmpresa, 'id'>;

export type EntityResponseType = HttpResponse<IGrupoAcessoEmpresa>;
export type EntityArrayResponseType = HttpResponse<IGrupoAcessoEmpresa[]>;

@Injectable({ providedIn: 'root' })
export class GrupoAcessoEmpresaService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/grupo-acesso-empresas');

  create(grupoAcessoEmpresa: NewGrupoAcessoEmpresa): Observable<EntityResponseType> {
    return this.http.post<IGrupoAcessoEmpresa>(this.resourceUrl, grupoAcessoEmpresa, { observe: 'response' });
  }

  update(grupoAcessoEmpresa: IGrupoAcessoEmpresa): Observable<EntityResponseType> {
    return this.http.put<IGrupoAcessoEmpresa>(
      `${this.resourceUrl}/${this.getGrupoAcessoEmpresaIdentifier(grupoAcessoEmpresa)}`,
      grupoAcessoEmpresa,
      { observe: 'response' },
    );
  }

  partialUpdate(grupoAcessoEmpresa: PartialUpdateGrupoAcessoEmpresa): Observable<EntityResponseType> {
    return this.http.patch<IGrupoAcessoEmpresa>(
      `${this.resourceUrl}/${this.getGrupoAcessoEmpresaIdentifier(grupoAcessoEmpresa)}`,
      grupoAcessoEmpresa,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IGrupoAcessoEmpresa>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IGrupoAcessoEmpresa[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getGrupoAcessoEmpresaIdentifier(grupoAcessoEmpresa: Pick<IGrupoAcessoEmpresa, 'id'>): number {
    return grupoAcessoEmpresa.id;
  }

  compareGrupoAcessoEmpresa(o1: Pick<IGrupoAcessoEmpresa, 'id'> | null, o2: Pick<IGrupoAcessoEmpresa, 'id'> | null): boolean {
    return o1 && o2 ? this.getGrupoAcessoEmpresaIdentifier(o1) === this.getGrupoAcessoEmpresaIdentifier(o2) : o1 === o2;
  }

  addGrupoAcessoEmpresaToCollectionIfMissing<Type extends Pick<IGrupoAcessoEmpresa, 'id'>>(
    grupoAcessoEmpresaCollection: Type[],
    ...grupoAcessoEmpresasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const grupoAcessoEmpresas: Type[] = grupoAcessoEmpresasToCheck.filter(isPresent);
    if (grupoAcessoEmpresas.length > 0) {
      const grupoAcessoEmpresaCollectionIdentifiers = grupoAcessoEmpresaCollection.map(grupoAcessoEmpresaItem =>
        this.getGrupoAcessoEmpresaIdentifier(grupoAcessoEmpresaItem),
      );
      const grupoAcessoEmpresasToAdd = grupoAcessoEmpresas.filter(grupoAcessoEmpresaItem => {
        const grupoAcessoEmpresaIdentifier = this.getGrupoAcessoEmpresaIdentifier(grupoAcessoEmpresaItem);
        if (grupoAcessoEmpresaCollectionIdentifiers.includes(grupoAcessoEmpresaIdentifier)) {
          return false;
        }
        grupoAcessoEmpresaCollectionIdentifiers.push(grupoAcessoEmpresaIdentifier);
        return true;
      });
      return [...grupoAcessoEmpresasToAdd, ...grupoAcessoEmpresaCollection];
    }
    return grupoAcessoEmpresaCollection;
  }
}
