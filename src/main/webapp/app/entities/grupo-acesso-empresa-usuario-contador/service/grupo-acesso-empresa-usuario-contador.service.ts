import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IGrupoAcessoEmpresaUsuarioContador, NewGrupoAcessoEmpresaUsuarioContador } from '../grupo-acesso-empresa-usuario-contador.model';

export type PartialUpdateGrupoAcessoEmpresaUsuarioContador = Partial<IGrupoAcessoEmpresaUsuarioContador> &
  Pick<IGrupoAcessoEmpresaUsuarioContador, 'id'>;

type RestOf<T extends IGrupoAcessoEmpresaUsuarioContador | NewGrupoAcessoEmpresaUsuarioContador> = Omit<T, 'dataExpiracao'> & {
  dataExpiracao?: string | null;
};

export type RestGrupoAcessoEmpresaUsuarioContador = RestOf<IGrupoAcessoEmpresaUsuarioContador>;

export type NewRestGrupoAcessoEmpresaUsuarioContador = RestOf<NewGrupoAcessoEmpresaUsuarioContador>;

export type PartialUpdateRestGrupoAcessoEmpresaUsuarioContador = RestOf<PartialUpdateGrupoAcessoEmpresaUsuarioContador>;

export type EntityResponseType = HttpResponse<IGrupoAcessoEmpresaUsuarioContador>;
export type EntityArrayResponseType = HttpResponse<IGrupoAcessoEmpresaUsuarioContador[]>;

@Injectable({ providedIn: 'root' })
export class GrupoAcessoEmpresaUsuarioContadorService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/grupo-acesso-empresa-usuario-contadors');

  create(grupoAcessoEmpresaUsuarioContador: NewGrupoAcessoEmpresaUsuarioContador): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(grupoAcessoEmpresaUsuarioContador);
    return this.http
      .post<RestGrupoAcessoEmpresaUsuarioContador>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(grupoAcessoEmpresaUsuarioContador: IGrupoAcessoEmpresaUsuarioContador): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(grupoAcessoEmpresaUsuarioContador);
    return this.http
      .put<RestGrupoAcessoEmpresaUsuarioContador>(
        `${this.resourceUrl}/${this.getGrupoAcessoEmpresaUsuarioContadorIdentifier(grupoAcessoEmpresaUsuarioContador)}`,
        copy,
        { observe: 'response' },
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(grupoAcessoEmpresaUsuarioContador: PartialUpdateGrupoAcessoEmpresaUsuarioContador): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(grupoAcessoEmpresaUsuarioContador);
    return this.http
      .patch<RestGrupoAcessoEmpresaUsuarioContador>(
        `${this.resourceUrl}/${this.getGrupoAcessoEmpresaUsuarioContadorIdentifier(grupoAcessoEmpresaUsuarioContador)}`,
        copy,
        { observe: 'response' },
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestGrupoAcessoEmpresaUsuarioContador>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestGrupoAcessoEmpresaUsuarioContador[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getGrupoAcessoEmpresaUsuarioContadorIdentifier(
    grupoAcessoEmpresaUsuarioContador: Pick<IGrupoAcessoEmpresaUsuarioContador, 'id'>,
  ): number {
    return grupoAcessoEmpresaUsuarioContador.id;
  }

  compareGrupoAcessoEmpresaUsuarioContador(
    o1: Pick<IGrupoAcessoEmpresaUsuarioContador, 'id'> | null,
    o2: Pick<IGrupoAcessoEmpresaUsuarioContador, 'id'> | null,
  ): boolean {
    return o1 && o2
      ? this.getGrupoAcessoEmpresaUsuarioContadorIdentifier(o1) === this.getGrupoAcessoEmpresaUsuarioContadorIdentifier(o2)
      : o1 === o2;
  }

  addGrupoAcessoEmpresaUsuarioContadorToCollectionIfMissing<Type extends Pick<IGrupoAcessoEmpresaUsuarioContador, 'id'>>(
    grupoAcessoEmpresaUsuarioContadorCollection: Type[],
    ...grupoAcessoEmpresaUsuarioContadorsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const grupoAcessoEmpresaUsuarioContadors: Type[] = grupoAcessoEmpresaUsuarioContadorsToCheck.filter(isPresent);
    if (grupoAcessoEmpresaUsuarioContadors.length > 0) {
      const grupoAcessoEmpresaUsuarioContadorCollectionIdentifiers = grupoAcessoEmpresaUsuarioContadorCollection.map(
        grupoAcessoEmpresaUsuarioContadorItem => this.getGrupoAcessoEmpresaUsuarioContadorIdentifier(grupoAcessoEmpresaUsuarioContadorItem),
      );
      const grupoAcessoEmpresaUsuarioContadorsToAdd = grupoAcessoEmpresaUsuarioContadors.filter(grupoAcessoEmpresaUsuarioContadorItem => {
        const grupoAcessoEmpresaUsuarioContadorIdentifier = this.getGrupoAcessoEmpresaUsuarioContadorIdentifier(
          grupoAcessoEmpresaUsuarioContadorItem,
        );
        if (grupoAcessoEmpresaUsuarioContadorCollectionIdentifiers.includes(grupoAcessoEmpresaUsuarioContadorIdentifier)) {
          return false;
        }
        grupoAcessoEmpresaUsuarioContadorCollectionIdentifiers.push(grupoAcessoEmpresaUsuarioContadorIdentifier);
        return true;
      });
      return [...grupoAcessoEmpresaUsuarioContadorsToAdd, ...grupoAcessoEmpresaUsuarioContadorCollection];
    }
    return grupoAcessoEmpresaUsuarioContadorCollection;
  }

  protected convertDateFromClient<
    T extends IGrupoAcessoEmpresaUsuarioContador | NewGrupoAcessoEmpresaUsuarioContador | PartialUpdateGrupoAcessoEmpresaUsuarioContador,
  >(grupoAcessoEmpresaUsuarioContador: T): RestOf<T> {
    return {
      ...grupoAcessoEmpresaUsuarioContador,
      dataExpiracao: grupoAcessoEmpresaUsuarioContador.dataExpiracao?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(
    restGrupoAcessoEmpresaUsuarioContador: RestGrupoAcessoEmpresaUsuarioContador,
  ): IGrupoAcessoEmpresaUsuarioContador {
    return {
      ...restGrupoAcessoEmpresaUsuarioContador,
      dataExpiracao: restGrupoAcessoEmpresaUsuarioContador.dataExpiracao
        ? dayjs(restGrupoAcessoEmpresaUsuarioContador.dataExpiracao)
        : undefined,
    };
  }

  protected convertResponseFromServer(
    res: HttpResponse<RestGrupoAcessoEmpresaUsuarioContador>,
  ): HttpResponse<IGrupoAcessoEmpresaUsuarioContador> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(
    res: HttpResponse<RestGrupoAcessoEmpresaUsuarioContador[]>,
  ): HttpResponse<IGrupoAcessoEmpresaUsuarioContador[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
