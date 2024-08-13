import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IGrupoAcessoUsuarioContador, NewGrupoAcessoUsuarioContador } from '../grupo-acesso-usuario-contador.model';

export type PartialUpdateGrupoAcessoUsuarioContador = Partial<IGrupoAcessoUsuarioContador> & Pick<IGrupoAcessoUsuarioContador, 'id'>;

type RestOf<T extends IGrupoAcessoUsuarioContador | NewGrupoAcessoUsuarioContador> = Omit<T, 'dataExpiracao'> & {
  dataExpiracao?: string | null;
};

export type RestGrupoAcessoUsuarioContador = RestOf<IGrupoAcessoUsuarioContador>;

export type NewRestGrupoAcessoUsuarioContador = RestOf<NewGrupoAcessoUsuarioContador>;

export type PartialUpdateRestGrupoAcessoUsuarioContador = RestOf<PartialUpdateGrupoAcessoUsuarioContador>;

export type EntityResponseType = HttpResponse<IGrupoAcessoUsuarioContador>;
export type EntityArrayResponseType = HttpResponse<IGrupoAcessoUsuarioContador[]>;

@Injectable({ providedIn: 'root' })
export class GrupoAcessoUsuarioContadorService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/grupo-acesso-usuario-contadors');

  create(grupoAcessoUsuarioContador: NewGrupoAcessoUsuarioContador): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(grupoAcessoUsuarioContador);
    return this.http
      .post<RestGrupoAcessoUsuarioContador>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(grupoAcessoUsuarioContador: IGrupoAcessoUsuarioContador): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(grupoAcessoUsuarioContador);
    return this.http
      .put<RestGrupoAcessoUsuarioContador>(
        `${this.resourceUrl}/${this.getGrupoAcessoUsuarioContadorIdentifier(grupoAcessoUsuarioContador)}`,
        copy,
        { observe: 'response' },
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(grupoAcessoUsuarioContador: PartialUpdateGrupoAcessoUsuarioContador): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(grupoAcessoUsuarioContador);
    return this.http
      .patch<RestGrupoAcessoUsuarioContador>(
        `${this.resourceUrl}/${this.getGrupoAcessoUsuarioContadorIdentifier(grupoAcessoUsuarioContador)}`,
        copy,
        { observe: 'response' },
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestGrupoAcessoUsuarioContador>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestGrupoAcessoUsuarioContador[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getGrupoAcessoUsuarioContadorIdentifier(grupoAcessoUsuarioContador: Pick<IGrupoAcessoUsuarioContador, 'id'>): number {
    return grupoAcessoUsuarioContador.id;
  }

  compareGrupoAcessoUsuarioContador(
    o1: Pick<IGrupoAcessoUsuarioContador, 'id'> | null,
    o2: Pick<IGrupoAcessoUsuarioContador, 'id'> | null,
  ): boolean {
    return o1 && o2 ? this.getGrupoAcessoUsuarioContadorIdentifier(o1) === this.getGrupoAcessoUsuarioContadorIdentifier(o2) : o1 === o2;
  }

  addGrupoAcessoUsuarioContadorToCollectionIfMissing<Type extends Pick<IGrupoAcessoUsuarioContador, 'id'>>(
    grupoAcessoUsuarioContadorCollection: Type[],
    ...grupoAcessoUsuarioContadorsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const grupoAcessoUsuarioContadors: Type[] = grupoAcessoUsuarioContadorsToCheck.filter(isPresent);
    if (grupoAcessoUsuarioContadors.length > 0) {
      const grupoAcessoUsuarioContadorCollectionIdentifiers = grupoAcessoUsuarioContadorCollection.map(grupoAcessoUsuarioContadorItem =>
        this.getGrupoAcessoUsuarioContadorIdentifier(grupoAcessoUsuarioContadorItem),
      );
      const grupoAcessoUsuarioContadorsToAdd = grupoAcessoUsuarioContadors.filter(grupoAcessoUsuarioContadorItem => {
        const grupoAcessoUsuarioContadorIdentifier = this.getGrupoAcessoUsuarioContadorIdentifier(grupoAcessoUsuarioContadorItem);
        if (grupoAcessoUsuarioContadorCollectionIdentifiers.includes(grupoAcessoUsuarioContadorIdentifier)) {
          return false;
        }
        grupoAcessoUsuarioContadorCollectionIdentifiers.push(grupoAcessoUsuarioContadorIdentifier);
        return true;
      });
      return [...grupoAcessoUsuarioContadorsToAdd, ...grupoAcessoUsuarioContadorCollection];
    }
    return grupoAcessoUsuarioContadorCollection;
  }

  protected convertDateFromClient<
    T extends IGrupoAcessoUsuarioContador | NewGrupoAcessoUsuarioContador | PartialUpdateGrupoAcessoUsuarioContador,
  >(grupoAcessoUsuarioContador: T): RestOf<T> {
    return {
      ...grupoAcessoUsuarioContador,
      dataExpiracao: grupoAcessoUsuarioContador.dataExpiracao?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restGrupoAcessoUsuarioContador: RestGrupoAcessoUsuarioContador): IGrupoAcessoUsuarioContador {
    return {
      ...restGrupoAcessoUsuarioContador,
      dataExpiracao: restGrupoAcessoUsuarioContador.dataExpiracao ? dayjs(restGrupoAcessoUsuarioContador.dataExpiracao) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestGrupoAcessoUsuarioContador>): HttpResponse<IGrupoAcessoUsuarioContador> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(
    res: HttpResponse<RestGrupoAcessoUsuarioContador[]>,
  ): HttpResponse<IGrupoAcessoUsuarioContador[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
