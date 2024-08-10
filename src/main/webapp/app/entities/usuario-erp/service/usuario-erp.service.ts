import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IUsuarioErp, NewUsuarioErp } from '../usuario-erp.model';

export type PartialUpdateUsuarioErp = Partial<IUsuarioErp> & Pick<IUsuarioErp, 'id'>;

type RestOf<T extends IUsuarioErp | NewUsuarioErp> = Omit<T, 'dataHoraAtivacao' | 'dataLimiteAcesso'> & {
  dataHoraAtivacao?: string | null;
  dataLimiteAcesso?: string | null;
};

export type RestUsuarioErp = RestOf<IUsuarioErp>;

export type NewRestUsuarioErp = RestOf<NewUsuarioErp>;

export type PartialUpdateRestUsuarioErp = RestOf<PartialUpdateUsuarioErp>;

export type EntityResponseType = HttpResponse<IUsuarioErp>;
export type EntityArrayResponseType = HttpResponse<IUsuarioErp[]>;

@Injectable({ providedIn: 'root' })
export class UsuarioErpService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/usuario-erps');

  create(usuarioErp: NewUsuarioErp): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(usuarioErp);
    return this.http
      .post<RestUsuarioErp>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(usuarioErp: IUsuarioErp): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(usuarioErp);
    return this.http
      .put<RestUsuarioErp>(`${this.resourceUrl}/${this.getUsuarioErpIdentifier(usuarioErp)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(usuarioErp: PartialUpdateUsuarioErp): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(usuarioErp);
    return this.http
      .patch<RestUsuarioErp>(`${this.resourceUrl}/${this.getUsuarioErpIdentifier(usuarioErp)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestUsuarioErp>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestUsuarioErp[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getUsuarioErpIdentifier(usuarioErp: Pick<IUsuarioErp, 'id'>): number {
    return usuarioErp.id;
  }

  compareUsuarioErp(o1: Pick<IUsuarioErp, 'id'> | null, o2: Pick<IUsuarioErp, 'id'> | null): boolean {
    return o1 && o2 ? this.getUsuarioErpIdentifier(o1) === this.getUsuarioErpIdentifier(o2) : o1 === o2;
  }

  addUsuarioErpToCollectionIfMissing<Type extends Pick<IUsuarioErp, 'id'>>(
    usuarioErpCollection: Type[],
    ...usuarioErpsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const usuarioErps: Type[] = usuarioErpsToCheck.filter(isPresent);
    if (usuarioErps.length > 0) {
      const usuarioErpCollectionIdentifiers = usuarioErpCollection.map(usuarioErpItem => this.getUsuarioErpIdentifier(usuarioErpItem));
      const usuarioErpsToAdd = usuarioErps.filter(usuarioErpItem => {
        const usuarioErpIdentifier = this.getUsuarioErpIdentifier(usuarioErpItem);
        if (usuarioErpCollectionIdentifiers.includes(usuarioErpIdentifier)) {
          return false;
        }
        usuarioErpCollectionIdentifiers.push(usuarioErpIdentifier);
        return true;
      });
      return [...usuarioErpsToAdd, ...usuarioErpCollection];
    }
    return usuarioErpCollection;
  }

  protected convertDateFromClient<T extends IUsuarioErp | NewUsuarioErp | PartialUpdateUsuarioErp>(usuarioErp: T): RestOf<T> {
    return {
      ...usuarioErp,
      dataHoraAtivacao: usuarioErp.dataHoraAtivacao?.toJSON() ?? null,
      dataLimiteAcesso: usuarioErp.dataLimiteAcesso?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restUsuarioErp: RestUsuarioErp): IUsuarioErp {
    return {
      ...restUsuarioErp,
      dataHoraAtivacao: restUsuarioErp.dataHoraAtivacao ? dayjs(restUsuarioErp.dataHoraAtivacao) : undefined,
      dataLimiteAcesso: restUsuarioErp.dataLimiteAcesso ? dayjs(restUsuarioErp.dataLimiteAcesso) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestUsuarioErp>): HttpResponse<IUsuarioErp> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestUsuarioErp[]>): HttpResponse<IUsuarioErp[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
