import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IUsuarioGestao, NewUsuarioGestao } from '../usuario-gestao.model';

export type PartialUpdateUsuarioGestao = Partial<IUsuarioGestao> & Pick<IUsuarioGestao, 'id'>;

type RestOf<T extends IUsuarioGestao | NewUsuarioGestao> = Omit<T, 'dataHoraAtivacao' | 'dataLimiteAcesso'> & {
  dataHoraAtivacao?: string | null;
  dataLimiteAcesso?: string | null;
};

export type RestUsuarioGestao = RestOf<IUsuarioGestao>;

export type NewRestUsuarioGestao = RestOf<NewUsuarioGestao>;

export type PartialUpdateRestUsuarioGestao = RestOf<PartialUpdateUsuarioGestao>;

export type EntityResponseType = HttpResponse<IUsuarioGestao>;
export type EntityArrayResponseType = HttpResponse<IUsuarioGestao[]>;

@Injectable({ providedIn: 'root' })
export class UsuarioGestaoService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/usuario-gestaos');

  create(usuarioGestao: NewUsuarioGestao): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(usuarioGestao);
    return this.http
      .post<RestUsuarioGestao>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(usuarioGestao: IUsuarioGestao): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(usuarioGestao);
    return this.http
      .put<RestUsuarioGestao>(`${this.resourceUrl}/${this.getUsuarioGestaoIdentifier(usuarioGestao)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(usuarioGestao: PartialUpdateUsuarioGestao): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(usuarioGestao);
    return this.http
      .patch<RestUsuarioGestao>(`${this.resourceUrl}/${this.getUsuarioGestaoIdentifier(usuarioGestao)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestUsuarioGestao>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestUsuarioGestao[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getUsuarioGestaoIdentifier(usuarioGestao: Pick<IUsuarioGestao, 'id'>): number {
    return usuarioGestao.id;
  }

  compareUsuarioGestao(o1: Pick<IUsuarioGestao, 'id'> | null, o2: Pick<IUsuarioGestao, 'id'> | null): boolean {
    return o1 && o2 ? this.getUsuarioGestaoIdentifier(o1) === this.getUsuarioGestaoIdentifier(o2) : o1 === o2;
  }

  addUsuarioGestaoToCollectionIfMissing<Type extends Pick<IUsuarioGestao, 'id'>>(
    usuarioGestaoCollection: Type[],
    ...usuarioGestaosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const usuarioGestaos: Type[] = usuarioGestaosToCheck.filter(isPresent);
    if (usuarioGestaos.length > 0) {
      const usuarioGestaoCollectionIdentifiers = usuarioGestaoCollection.map(usuarioGestaoItem =>
        this.getUsuarioGestaoIdentifier(usuarioGestaoItem),
      );
      const usuarioGestaosToAdd = usuarioGestaos.filter(usuarioGestaoItem => {
        const usuarioGestaoIdentifier = this.getUsuarioGestaoIdentifier(usuarioGestaoItem);
        if (usuarioGestaoCollectionIdentifiers.includes(usuarioGestaoIdentifier)) {
          return false;
        }
        usuarioGestaoCollectionIdentifiers.push(usuarioGestaoIdentifier);
        return true;
      });
      return [...usuarioGestaosToAdd, ...usuarioGestaoCollection];
    }
    return usuarioGestaoCollection;
  }

  protected convertDateFromClient<T extends IUsuarioGestao | NewUsuarioGestao | PartialUpdateUsuarioGestao>(usuarioGestao: T): RestOf<T> {
    return {
      ...usuarioGestao,
      dataHoraAtivacao: usuarioGestao.dataHoraAtivacao?.toJSON() ?? null,
      dataLimiteAcesso: usuarioGestao.dataLimiteAcesso?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restUsuarioGestao: RestUsuarioGestao): IUsuarioGestao {
    return {
      ...restUsuarioGestao,
      dataHoraAtivacao: restUsuarioGestao.dataHoraAtivacao ? dayjs(restUsuarioGestao.dataHoraAtivacao) : undefined,
      dataLimiteAcesso: restUsuarioGestao.dataLimiteAcesso ? dayjs(restUsuarioGestao.dataLimiteAcesso) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestUsuarioGestao>): HttpResponse<IUsuarioGestao> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestUsuarioGestao[]>): HttpResponse<IUsuarioGestao[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
