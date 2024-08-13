import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAnexoServicoContabilEmpresa, NewAnexoServicoContabilEmpresa } from '../anexo-servico-contabil-empresa.model';

export type PartialUpdateAnexoServicoContabilEmpresa = Partial<IAnexoServicoContabilEmpresa> & Pick<IAnexoServicoContabilEmpresa, 'id'>;

type RestOf<T extends IAnexoServicoContabilEmpresa | NewAnexoServicoContabilEmpresa> = Omit<T, 'dataHoraUpload'> & {
  dataHoraUpload?: string | null;
};

export type RestAnexoServicoContabilEmpresa = RestOf<IAnexoServicoContabilEmpresa>;

export type NewRestAnexoServicoContabilEmpresa = RestOf<NewAnexoServicoContabilEmpresa>;

export type PartialUpdateRestAnexoServicoContabilEmpresa = RestOf<PartialUpdateAnexoServicoContabilEmpresa>;

export type EntityResponseType = HttpResponse<IAnexoServicoContabilEmpresa>;
export type EntityArrayResponseType = HttpResponse<IAnexoServicoContabilEmpresa[]>;

@Injectable({ providedIn: 'root' })
export class AnexoServicoContabilEmpresaService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/anexo-servico-contabil-empresas');

  create(anexoServicoContabilEmpresa: NewAnexoServicoContabilEmpresa): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(anexoServicoContabilEmpresa);
    return this.http
      .post<RestAnexoServicoContabilEmpresa>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(anexoServicoContabilEmpresa: IAnexoServicoContabilEmpresa): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(anexoServicoContabilEmpresa);
    return this.http
      .put<RestAnexoServicoContabilEmpresa>(
        `${this.resourceUrl}/${this.getAnexoServicoContabilEmpresaIdentifier(anexoServicoContabilEmpresa)}`,
        copy,
        { observe: 'response' },
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(anexoServicoContabilEmpresa: PartialUpdateAnexoServicoContabilEmpresa): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(anexoServicoContabilEmpresa);
    return this.http
      .patch<RestAnexoServicoContabilEmpresa>(
        `${this.resourceUrl}/${this.getAnexoServicoContabilEmpresaIdentifier(anexoServicoContabilEmpresa)}`,
        copy,
        { observe: 'response' },
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestAnexoServicoContabilEmpresa>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestAnexoServicoContabilEmpresa[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAnexoServicoContabilEmpresaIdentifier(anexoServicoContabilEmpresa: Pick<IAnexoServicoContabilEmpresa, 'id'>): number {
    return anexoServicoContabilEmpresa.id;
  }

  compareAnexoServicoContabilEmpresa(
    o1: Pick<IAnexoServicoContabilEmpresa, 'id'> | null,
    o2: Pick<IAnexoServicoContabilEmpresa, 'id'> | null,
  ): boolean {
    return o1 && o2 ? this.getAnexoServicoContabilEmpresaIdentifier(o1) === this.getAnexoServicoContabilEmpresaIdentifier(o2) : o1 === o2;
  }

  addAnexoServicoContabilEmpresaToCollectionIfMissing<Type extends Pick<IAnexoServicoContabilEmpresa, 'id'>>(
    anexoServicoContabilEmpresaCollection: Type[],
    ...anexoServicoContabilEmpresasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const anexoServicoContabilEmpresas: Type[] = anexoServicoContabilEmpresasToCheck.filter(isPresent);
    if (anexoServicoContabilEmpresas.length > 0) {
      const anexoServicoContabilEmpresaCollectionIdentifiers = anexoServicoContabilEmpresaCollection.map(anexoServicoContabilEmpresaItem =>
        this.getAnexoServicoContabilEmpresaIdentifier(anexoServicoContabilEmpresaItem),
      );
      const anexoServicoContabilEmpresasToAdd = anexoServicoContabilEmpresas.filter(anexoServicoContabilEmpresaItem => {
        const anexoServicoContabilEmpresaIdentifier = this.getAnexoServicoContabilEmpresaIdentifier(anexoServicoContabilEmpresaItem);
        if (anexoServicoContabilEmpresaCollectionIdentifiers.includes(anexoServicoContabilEmpresaIdentifier)) {
          return false;
        }
        anexoServicoContabilEmpresaCollectionIdentifiers.push(anexoServicoContabilEmpresaIdentifier);
        return true;
      });
      return [...anexoServicoContabilEmpresasToAdd, ...anexoServicoContabilEmpresaCollection];
    }
    return anexoServicoContabilEmpresaCollection;
  }

  protected convertDateFromClient<
    T extends IAnexoServicoContabilEmpresa | NewAnexoServicoContabilEmpresa | PartialUpdateAnexoServicoContabilEmpresa,
  >(anexoServicoContabilEmpresa: T): RestOf<T> {
    return {
      ...anexoServicoContabilEmpresa,
      dataHoraUpload: anexoServicoContabilEmpresa.dataHoraUpload?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restAnexoServicoContabilEmpresa: RestAnexoServicoContabilEmpresa): IAnexoServicoContabilEmpresa {
    return {
      ...restAnexoServicoContabilEmpresa,
      dataHoraUpload: restAnexoServicoContabilEmpresa.dataHoraUpload ? dayjs(restAnexoServicoContabilEmpresa.dataHoraUpload) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestAnexoServicoContabilEmpresa>): HttpResponse<IAnexoServicoContabilEmpresa> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(
    res: HttpResponse<RestAnexoServicoContabilEmpresa[]>,
  ): HttpResponse<IAnexoServicoContabilEmpresa[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
