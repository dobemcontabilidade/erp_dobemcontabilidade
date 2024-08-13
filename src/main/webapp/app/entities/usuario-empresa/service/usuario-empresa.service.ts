import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IUsuarioEmpresa, NewUsuarioEmpresa } from '../usuario-empresa.model';

export type PartialUpdateUsuarioEmpresa = Partial<IUsuarioEmpresa> & Pick<IUsuarioEmpresa, 'id'>;

type RestOf<T extends IUsuarioEmpresa | NewUsuarioEmpresa> = Omit<T, 'dataHoraAtivacao' | 'dataLimiteAcesso'> & {
  dataHoraAtivacao?: string | null;
  dataLimiteAcesso?: string | null;
};

export type RestUsuarioEmpresa = RestOf<IUsuarioEmpresa>;

export type NewRestUsuarioEmpresa = RestOf<NewUsuarioEmpresa>;

export type PartialUpdateRestUsuarioEmpresa = RestOf<PartialUpdateUsuarioEmpresa>;

export type EntityResponseType = HttpResponse<IUsuarioEmpresa>;
export type EntityArrayResponseType = HttpResponse<IUsuarioEmpresa[]>;

@Injectable({ providedIn: 'root' })
export class UsuarioEmpresaService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/usuario-empresas');

  create(usuarioEmpresa: NewUsuarioEmpresa): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(usuarioEmpresa);
    return this.http
      .post<RestUsuarioEmpresa>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(usuarioEmpresa: IUsuarioEmpresa): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(usuarioEmpresa);
    return this.http
      .put<RestUsuarioEmpresa>(`${this.resourceUrl}/${this.getUsuarioEmpresaIdentifier(usuarioEmpresa)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(usuarioEmpresa: PartialUpdateUsuarioEmpresa): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(usuarioEmpresa);
    return this.http
      .patch<RestUsuarioEmpresa>(`${this.resourceUrl}/${this.getUsuarioEmpresaIdentifier(usuarioEmpresa)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestUsuarioEmpresa>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestUsuarioEmpresa[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getUsuarioEmpresaIdentifier(usuarioEmpresa: Pick<IUsuarioEmpresa, 'id'>): number {
    return usuarioEmpresa.id;
  }

  compareUsuarioEmpresa(o1: Pick<IUsuarioEmpresa, 'id'> | null, o2: Pick<IUsuarioEmpresa, 'id'> | null): boolean {
    return o1 && o2 ? this.getUsuarioEmpresaIdentifier(o1) === this.getUsuarioEmpresaIdentifier(o2) : o1 === o2;
  }

  addUsuarioEmpresaToCollectionIfMissing<Type extends Pick<IUsuarioEmpresa, 'id'>>(
    usuarioEmpresaCollection: Type[],
    ...usuarioEmpresasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const usuarioEmpresas: Type[] = usuarioEmpresasToCheck.filter(isPresent);
    if (usuarioEmpresas.length > 0) {
      const usuarioEmpresaCollectionIdentifiers = usuarioEmpresaCollection.map(usuarioEmpresaItem =>
        this.getUsuarioEmpresaIdentifier(usuarioEmpresaItem),
      );
      const usuarioEmpresasToAdd = usuarioEmpresas.filter(usuarioEmpresaItem => {
        const usuarioEmpresaIdentifier = this.getUsuarioEmpresaIdentifier(usuarioEmpresaItem);
        if (usuarioEmpresaCollectionIdentifiers.includes(usuarioEmpresaIdentifier)) {
          return false;
        }
        usuarioEmpresaCollectionIdentifiers.push(usuarioEmpresaIdentifier);
        return true;
      });
      return [...usuarioEmpresasToAdd, ...usuarioEmpresaCollection];
    }
    return usuarioEmpresaCollection;
  }

  protected convertDateFromClient<T extends IUsuarioEmpresa | NewUsuarioEmpresa | PartialUpdateUsuarioEmpresa>(
    usuarioEmpresa: T,
  ): RestOf<T> {
    return {
      ...usuarioEmpresa,
      dataHoraAtivacao: usuarioEmpresa.dataHoraAtivacao?.toJSON() ?? null,
      dataLimiteAcesso: usuarioEmpresa.dataLimiteAcesso?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restUsuarioEmpresa: RestUsuarioEmpresa): IUsuarioEmpresa {
    return {
      ...restUsuarioEmpresa,
      dataHoraAtivacao: restUsuarioEmpresa.dataHoraAtivacao ? dayjs(restUsuarioEmpresa.dataHoraAtivacao) : undefined,
      dataLimiteAcesso: restUsuarioEmpresa.dataLimiteAcesso ? dayjs(restUsuarioEmpresa.dataLimiteAcesso) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestUsuarioEmpresa>): HttpResponse<IUsuarioEmpresa> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestUsuarioEmpresa[]>): HttpResponse<IUsuarioEmpresa[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
