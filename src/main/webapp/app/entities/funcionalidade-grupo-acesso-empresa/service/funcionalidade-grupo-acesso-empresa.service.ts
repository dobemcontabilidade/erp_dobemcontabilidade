import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IFuncionalidadeGrupoAcessoEmpresa, NewFuncionalidadeGrupoAcessoEmpresa } from '../funcionalidade-grupo-acesso-empresa.model';

export type PartialUpdateFuncionalidadeGrupoAcessoEmpresa = Partial<IFuncionalidadeGrupoAcessoEmpresa> &
  Pick<IFuncionalidadeGrupoAcessoEmpresa, 'id'>;

type RestOf<T extends IFuncionalidadeGrupoAcessoEmpresa | NewFuncionalidadeGrupoAcessoEmpresa> = Omit<T, 'dataExpiracao'> & {
  dataExpiracao?: string | null;
};

export type RestFuncionalidadeGrupoAcessoEmpresa = RestOf<IFuncionalidadeGrupoAcessoEmpresa>;

export type NewRestFuncionalidadeGrupoAcessoEmpresa = RestOf<NewFuncionalidadeGrupoAcessoEmpresa>;

export type PartialUpdateRestFuncionalidadeGrupoAcessoEmpresa = RestOf<PartialUpdateFuncionalidadeGrupoAcessoEmpresa>;

export type EntityResponseType = HttpResponse<IFuncionalidadeGrupoAcessoEmpresa>;
export type EntityArrayResponseType = HttpResponse<IFuncionalidadeGrupoAcessoEmpresa[]>;

@Injectable({ providedIn: 'root' })
export class FuncionalidadeGrupoAcessoEmpresaService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/funcionalidade-grupo-acesso-empresas');

  create(funcionalidadeGrupoAcessoEmpresa: NewFuncionalidadeGrupoAcessoEmpresa): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(funcionalidadeGrupoAcessoEmpresa);
    return this.http
      .post<RestFuncionalidadeGrupoAcessoEmpresa>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(funcionalidadeGrupoAcessoEmpresa: IFuncionalidadeGrupoAcessoEmpresa): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(funcionalidadeGrupoAcessoEmpresa);
    return this.http
      .put<RestFuncionalidadeGrupoAcessoEmpresa>(
        `${this.resourceUrl}/${this.getFuncionalidadeGrupoAcessoEmpresaIdentifier(funcionalidadeGrupoAcessoEmpresa)}`,
        copy,
        { observe: 'response' },
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(funcionalidadeGrupoAcessoEmpresa: PartialUpdateFuncionalidadeGrupoAcessoEmpresa): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(funcionalidadeGrupoAcessoEmpresa);
    return this.http
      .patch<RestFuncionalidadeGrupoAcessoEmpresa>(
        `${this.resourceUrl}/${this.getFuncionalidadeGrupoAcessoEmpresaIdentifier(funcionalidadeGrupoAcessoEmpresa)}`,
        copy,
        { observe: 'response' },
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestFuncionalidadeGrupoAcessoEmpresa>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestFuncionalidadeGrupoAcessoEmpresa[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getFuncionalidadeGrupoAcessoEmpresaIdentifier(funcionalidadeGrupoAcessoEmpresa: Pick<IFuncionalidadeGrupoAcessoEmpresa, 'id'>): number {
    return funcionalidadeGrupoAcessoEmpresa.id;
  }

  compareFuncionalidadeGrupoAcessoEmpresa(
    o1: Pick<IFuncionalidadeGrupoAcessoEmpresa, 'id'> | null,
    o2: Pick<IFuncionalidadeGrupoAcessoEmpresa, 'id'> | null,
  ): boolean {
    return o1 && o2
      ? this.getFuncionalidadeGrupoAcessoEmpresaIdentifier(o1) === this.getFuncionalidadeGrupoAcessoEmpresaIdentifier(o2)
      : o1 === o2;
  }

  addFuncionalidadeGrupoAcessoEmpresaToCollectionIfMissing<Type extends Pick<IFuncionalidadeGrupoAcessoEmpresa, 'id'>>(
    funcionalidadeGrupoAcessoEmpresaCollection: Type[],
    ...funcionalidadeGrupoAcessoEmpresasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const funcionalidadeGrupoAcessoEmpresas: Type[] = funcionalidadeGrupoAcessoEmpresasToCheck.filter(isPresent);
    if (funcionalidadeGrupoAcessoEmpresas.length > 0) {
      const funcionalidadeGrupoAcessoEmpresaCollectionIdentifiers = funcionalidadeGrupoAcessoEmpresaCollection.map(
        funcionalidadeGrupoAcessoEmpresaItem => this.getFuncionalidadeGrupoAcessoEmpresaIdentifier(funcionalidadeGrupoAcessoEmpresaItem),
      );
      const funcionalidadeGrupoAcessoEmpresasToAdd = funcionalidadeGrupoAcessoEmpresas.filter(funcionalidadeGrupoAcessoEmpresaItem => {
        const funcionalidadeGrupoAcessoEmpresaIdentifier = this.getFuncionalidadeGrupoAcessoEmpresaIdentifier(
          funcionalidadeGrupoAcessoEmpresaItem,
        );
        if (funcionalidadeGrupoAcessoEmpresaCollectionIdentifiers.includes(funcionalidadeGrupoAcessoEmpresaIdentifier)) {
          return false;
        }
        funcionalidadeGrupoAcessoEmpresaCollectionIdentifiers.push(funcionalidadeGrupoAcessoEmpresaIdentifier);
        return true;
      });
      return [...funcionalidadeGrupoAcessoEmpresasToAdd, ...funcionalidadeGrupoAcessoEmpresaCollection];
    }
    return funcionalidadeGrupoAcessoEmpresaCollection;
  }

  protected convertDateFromClient<
    T extends IFuncionalidadeGrupoAcessoEmpresa | NewFuncionalidadeGrupoAcessoEmpresa | PartialUpdateFuncionalidadeGrupoAcessoEmpresa,
  >(funcionalidadeGrupoAcessoEmpresa: T): RestOf<T> {
    return {
      ...funcionalidadeGrupoAcessoEmpresa,
      dataExpiracao: funcionalidadeGrupoAcessoEmpresa.dataExpiracao?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(
    restFuncionalidadeGrupoAcessoEmpresa: RestFuncionalidadeGrupoAcessoEmpresa,
  ): IFuncionalidadeGrupoAcessoEmpresa {
    return {
      ...restFuncionalidadeGrupoAcessoEmpresa,
      dataExpiracao: restFuncionalidadeGrupoAcessoEmpresa.dataExpiracao
        ? dayjs(restFuncionalidadeGrupoAcessoEmpresa.dataExpiracao)
        : undefined,
    };
  }

  protected convertResponseFromServer(
    res: HttpResponse<RestFuncionalidadeGrupoAcessoEmpresa>,
  ): HttpResponse<IFuncionalidadeGrupoAcessoEmpresa> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(
    res: HttpResponse<RestFuncionalidadeGrupoAcessoEmpresa[]>,
  ): HttpResponse<IFuncionalidadeGrupoAcessoEmpresa[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
