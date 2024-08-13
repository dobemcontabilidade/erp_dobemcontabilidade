import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPerfilAcessoUsuario, NewPerfilAcessoUsuario } from '../perfil-acesso-usuario.model';

export type PartialUpdatePerfilAcessoUsuario = Partial<IPerfilAcessoUsuario> & Pick<IPerfilAcessoUsuario, 'id'>;

type RestOf<T extends IPerfilAcessoUsuario | NewPerfilAcessoUsuario> = Omit<T, 'dataExpiracao'> & {
  dataExpiracao?: string | null;
};

export type RestPerfilAcessoUsuario = RestOf<IPerfilAcessoUsuario>;

export type NewRestPerfilAcessoUsuario = RestOf<NewPerfilAcessoUsuario>;

export type PartialUpdateRestPerfilAcessoUsuario = RestOf<PartialUpdatePerfilAcessoUsuario>;

export type EntityResponseType = HttpResponse<IPerfilAcessoUsuario>;
export type EntityArrayResponseType = HttpResponse<IPerfilAcessoUsuario[]>;

@Injectable({ providedIn: 'root' })
export class PerfilAcessoUsuarioService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/perfil-acesso-usuarios');

  create(perfilAcessoUsuario: NewPerfilAcessoUsuario): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(perfilAcessoUsuario);
    return this.http
      .post<RestPerfilAcessoUsuario>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(perfilAcessoUsuario: IPerfilAcessoUsuario): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(perfilAcessoUsuario);
    return this.http
      .put<RestPerfilAcessoUsuario>(`${this.resourceUrl}/${this.getPerfilAcessoUsuarioIdentifier(perfilAcessoUsuario)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(perfilAcessoUsuario: PartialUpdatePerfilAcessoUsuario): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(perfilAcessoUsuario);
    return this.http
      .patch<RestPerfilAcessoUsuario>(`${this.resourceUrl}/${this.getPerfilAcessoUsuarioIdentifier(perfilAcessoUsuario)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestPerfilAcessoUsuario>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestPerfilAcessoUsuario[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPerfilAcessoUsuarioIdentifier(perfilAcessoUsuario: Pick<IPerfilAcessoUsuario, 'id'>): number {
    return perfilAcessoUsuario.id;
  }

  comparePerfilAcessoUsuario(o1: Pick<IPerfilAcessoUsuario, 'id'> | null, o2: Pick<IPerfilAcessoUsuario, 'id'> | null): boolean {
    return o1 && o2 ? this.getPerfilAcessoUsuarioIdentifier(o1) === this.getPerfilAcessoUsuarioIdentifier(o2) : o1 === o2;
  }

  addPerfilAcessoUsuarioToCollectionIfMissing<Type extends Pick<IPerfilAcessoUsuario, 'id'>>(
    perfilAcessoUsuarioCollection: Type[],
    ...perfilAcessoUsuariosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const perfilAcessoUsuarios: Type[] = perfilAcessoUsuariosToCheck.filter(isPresent);
    if (perfilAcessoUsuarios.length > 0) {
      const perfilAcessoUsuarioCollectionIdentifiers = perfilAcessoUsuarioCollection.map(perfilAcessoUsuarioItem =>
        this.getPerfilAcessoUsuarioIdentifier(perfilAcessoUsuarioItem),
      );
      const perfilAcessoUsuariosToAdd = perfilAcessoUsuarios.filter(perfilAcessoUsuarioItem => {
        const perfilAcessoUsuarioIdentifier = this.getPerfilAcessoUsuarioIdentifier(perfilAcessoUsuarioItem);
        if (perfilAcessoUsuarioCollectionIdentifiers.includes(perfilAcessoUsuarioIdentifier)) {
          return false;
        }
        perfilAcessoUsuarioCollectionIdentifiers.push(perfilAcessoUsuarioIdentifier);
        return true;
      });
      return [...perfilAcessoUsuariosToAdd, ...perfilAcessoUsuarioCollection];
    }
    return perfilAcessoUsuarioCollection;
  }

  protected convertDateFromClient<T extends IPerfilAcessoUsuario | NewPerfilAcessoUsuario | PartialUpdatePerfilAcessoUsuario>(
    perfilAcessoUsuario: T,
  ): RestOf<T> {
    return {
      ...perfilAcessoUsuario,
      dataExpiracao: perfilAcessoUsuario.dataExpiracao?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restPerfilAcessoUsuario: RestPerfilAcessoUsuario): IPerfilAcessoUsuario {
    return {
      ...restPerfilAcessoUsuario,
      dataExpiracao: restPerfilAcessoUsuario.dataExpiracao ? dayjs(restPerfilAcessoUsuario.dataExpiracao) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestPerfilAcessoUsuario>): HttpResponse<IPerfilAcessoUsuario> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestPerfilAcessoUsuario[]>): HttpResponse<IPerfilAcessoUsuario[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
