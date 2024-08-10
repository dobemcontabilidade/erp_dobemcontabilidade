import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IUsuarioContador, NewUsuarioContador } from '../usuario-contador.model';

export type PartialUpdateUsuarioContador = Partial<IUsuarioContador> & Pick<IUsuarioContador, 'id'>;

type RestOf<T extends IUsuarioContador | NewUsuarioContador> = Omit<T, 'dataHoraAtivacao' | 'dataLimiteAcesso'> & {
  dataHoraAtivacao?: string | null;
  dataLimiteAcesso?: string | null;
};

export type RestUsuarioContador = RestOf<IUsuarioContador>;

export type NewRestUsuarioContador = RestOf<NewUsuarioContador>;

export type PartialUpdateRestUsuarioContador = RestOf<PartialUpdateUsuarioContador>;

export type EntityResponseType = HttpResponse<IUsuarioContador>;
export type EntityArrayResponseType = HttpResponse<IUsuarioContador[]>;

@Injectable({ providedIn: 'root' })
export class UsuarioContadorService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/usuario-contadors');

  create(usuarioContador: NewUsuarioContador): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(usuarioContador);
    return this.http
      .post<RestUsuarioContador>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(usuarioContador: IUsuarioContador): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(usuarioContador);
    return this.http
      .put<RestUsuarioContador>(`${this.resourceUrl}/${this.getUsuarioContadorIdentifier(usuarioContador)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(usuarioContador: PartialUpdateUsuarioContador): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(usuarioContador);
    return this.http
      .patch<RestUsuarioContador>(`${this.resourceUrl}/${this.getUsuarioContadorIdentifier(usuarioContador)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestUsuarioContador>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestUsuarioContador[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getUsuarioContadorIdentifier(usuarioContador: Pick<IUsuarioContador, 'id'>): number {
    return usuarioContador.id;
  }

  compareUsuarioContador(o1: Pick<IUsuarioContador, 'id'> | null, o2: Pick<IUsuarioContador, 'id'> | null): boolean {
    return o1 && o2 ? this.getUsuarioContadorIdentifier(o1) === this.getUsuarioContadorIdentifier(o2) : o1 === o2;
  }

  addUsuarioContadorToCollectionIfMissing<Type extends Pick<IUsuarioContador, 'id'>>(
    usuarioContadorCollection: Type[],
    ...usuarioContadorsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const usuarioContadors: Type[] = usuarioContadorsToCheck.filter(isPresent);
    if (usuarioContadors.length > 0) {
      const usuarioContadorCollectionIdentifiers = usuarioContadorCollection.map(usuarioContadorItem =>
        this.getUsuarioContadorIdentifier(usuarioContadorItem),
      );
      const usuarioContadorsToAdd = usuarioContadors.filter(usuarioContadorItem => {
        const usuarioContadorIdentifier = this.getUsuarioContadorIdentifier(usuarioContadorItem);
        if (usuarioContadorCollectionIdentifiers.includes(usuarioContadorIdentifier)) {
          return false;
        }
        usuarioContadorCollectionIdentifiers.push(usuarioContadorIdentifier);
        return true;
      });
      return [...usuarioContadorsToAdd, ...usuarioContadorCollection];
    }
    return usuarioContadorCollection;
  }

  protected convertDateFromClient<T extends IUsuarioContador | NewUsuarioContador | PartialUpdateUsuarioContador>(
    usuarioContador: T,
  ): RestOf<T> {
    return {
      ...usuarioContador,
      dataHoraAtivacao: usuarioContador.dataHoraAtivacao?.toJSON() ?? null,
      dataLimiteAcesso: usuarioContador.dataLimiteAcesso?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restUsuarioContador: RestUsuarioContador): IUsuarioContador {
    return {
      ...restUsuarioContador,
      dataHoraAtivacao: restUsuarioContador.dataHoraAtivacao ? dayjs(restUsuarioContador.dataHoraAtivacao) : undefined,
      dataLimiteAcesso: restUsuarioContador.dataLimiteAcesso ? dayjs(restUsuarioContador.dataLimiteAcesso) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestUsuarioContador>): HttpResponse<IUsuarioContador> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestUsuarioContador[]>): HttpResponse<IUsuarioContador[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
