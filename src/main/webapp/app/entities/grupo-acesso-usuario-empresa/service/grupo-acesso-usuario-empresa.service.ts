import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IGrupoAcessoUsuarioEmpresa, NewGrupoAcessoUsuarioEmpresa } from '../grupo-acesso-usuario-empresa.model';

export type PartialUpdateGrupoAcessoUsuarioEmpresa = Partial<IGrupoAcessoUsuarioEmpresa> & Pick<IGrupoAcessoUsuarioEmpresa, 'id'>;

type RestOf<T extends IGrupoAcessoUsuarioEmpresa | NewGrupoAcessoUsuarioEmpresa> = Omit<T, 'dataExpiracao'> & {
  dataExpiracao?: string | null;
};

export type RestGrupoAcessoUsuarioEmpresa = RestOf<IGrupoAcessoUsuarioEmpresa>;

export type NewRestGrupoAcessoUsuarioEmpresa = RestOf<NewGrupoAcessoUsuarioEmpresa>;

export type PartialUpdateRestGrupoAcessoUsuarioEmpresa = RestOf<PartialUpdateGrupoAcessoUsuarioEmpresa>;

export type EntityResponseType = HttpResponse<IGrupoAcessoUsuarioEmpresa>;
export type EntityArrayResponseType = HttpResponse<IGrupoAcessoUsuarioEmpresa[]>;

@Injectable({ providedIn: 'root' })
export class GrupoAcessoUsuarioEmpresaService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/grupo-acesso-usuario-empresas');

  create(grupoAcessoUsuarioEmpresa: NewGrupoAcessoUsuarioEmpresa): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(grupoAcessoUsuarioEmpresa);
    return this.http
      .post<RestGrupoAcessoUsuarioEmpresa>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(grupoAcessoUsuarioEmpresa: IGrupoAcessoUsuarioEmpresa): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(grupoAcessoUsuarioEmpresa);
    return this.http
      .put<RestGrupoAcessoUsuarioEmpresa>(
        `${this.resourceUrl}/${this.getGrupoAcessoUsuarioEmpresaIdentifier(grupoAcessoUsuarioEmpresa)}`,
        copy,
        { observe: 'response' },
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(grupoAcessoUsuarioEmpresa: PartialUpdateGrupoAcessoUsuarioEmpresa): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(grupoAcessoUsuarioEmpresa);
    return this.http
      .patch<RestGrupoAcessoUsuarioEmpresa>(
        `${this.resourceUrl}/${this.getGrupoAcessoUsuarioEmpresaIdentifier(grupoAcessoUsuarioEmpresa)}`,
        copy,
        { observe: 'response' },
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestGrupoAcessoUsuarioEmpresa>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestGrupoAcessoUsuarioEmpresa[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getGrupoAcessoUsuarioEmpresaIdentifier(grupoAcessoUsuarioEmpresa: Pick<IGrupoAcessoUsuarioEmpresa, 'id'>): number {
    return grupoAcessoUsuarioEmpresa.id;
  }

  compareGrupoAcessoUsuarioEmpresa(
    o1: Pick<IGrupoAcessoUsuarioEmpresa, 'id'> | null,
    o2: Pick<IGrupoAcessoUsuarioEmpresa, 'id'> | null,
  ): boolean {
    return o1 && o2 ? this.getGrupoAcessoUsuarioEmpresaIdentifier(o1) === this.getGrupoAcessoUsuarioEmpresaIdentifier(o2) : o1 === o2;
  }

  addGrupoAcessoUsuarioEmpresaToCollectionIfMissing<Type extends Pick<IGrupoAcessoUsuarioEmpresa, 'id'>>(
    grupoAcessoUsuarioEmpresaCollection: Type[],
    ...grupoAcessoUsuarioEmpresasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const grupoAcessoUsuarioEmpresas: Type[] = grupoAcessoUsuarioEmpresasToCheck.filter(isPresent);
    if (grupoAcessoUsuarioEmpresas.length > 0) {
      const grupoAcessoUsuarioEmpresaCollectionIdentifiers = grupoAcessoUsuarioEmpresaCollection.map(grupoAcessoUsuarioEmpresaItem =>
        this.getGrupoAcessoUsuarioEmpresaIdentifier(grupoAcessoUsuarioEmpresaItem),
      );
      const grupoAcessoUsuarioEmpresasToAdd = grupoAcessoUsuarioEmpresas.filter(grupoAcessoUsuarioEmpresaItem => {
        const grupoAcessoUsuarioEmpresaIdentifier = this.getGrupoAcessoUsuarioEmpresaIdentifier(grupoAcessoUsuarioEmpresaItem);
        if (grupoAcessoUsuarioEmpresaCollectionIdentifiers.includes(grupoAcessoUsuarioEmpresaIdentifier)) {
          return false;
        }
        grupoAcessoUsuarioEmpresaCollectionIdentifiers.push(grupoAcessoUsuarioEmpresaIdentifier);
        return true;
      });
      return [...grupoAcessoUsuarioEmpresasToAdd, ...grupoAcessoUsuarioEmpresaCollection];
    }
    return grupoAcessoUsuarioEmpresaCollection;
  }

  protected convertDateFromClient<
    T extends IGrupoAcessoUsuarioEmpresa | NewGrupoAcessoUsuarioEmpresa | PartialUpdateGrupoAcessoUsuarioEmpresa,
  >(grupoAcessoUsuarioEmpresa: T): RestOf<T> {
    return {
      ...grupoAcessoUsuarioEmpresa,
      dataExpiracao: grupoAcessoUsuarioEmpresa.dataExpiracao?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restGrupoAcessoUsuarioEmpresa: RestGrupoAcessoUsuarioEmpresa): IGrupoAcessoUsuarioEmpresa {
    return {
      ...restGrupoAcessoUsuarioEmpresa,
      dataExpiracao: restGrupoAcessoUsuarioEmpresa.dataExpiracao ? dayjs(restGrupoAcessoUsuarioEmpresa.dataExpiracao) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestGrupoAcessoUsuarioEmpresa>): HttpResponse<IGrupoAcessoUsuarioEmpresa> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestGrupoAcessoUsuarioEmpresa[]>): HttpResponse<IGrupoAcessoUsuarioEmpresa[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
