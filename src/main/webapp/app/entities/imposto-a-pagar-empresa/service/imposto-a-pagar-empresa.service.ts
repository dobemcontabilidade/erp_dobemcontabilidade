import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IImpostoAPagarEmpresa, NewImpostoAPagarEmpresa } from '../imposto-a-pagar-empresa.model';

export type PartialUpdateImpostoAPagarEmpresa = Partial<IImpostoAPagarEmpresa> & Pick<IImpostoAPagarEmpresa, 'id'>;

type RestOf<T extends IImpostoAPagarEmpresa | NewImpostoAPagarEmpresa> = Omit<T, 'dataVencimento' | 'dataPagamento'> & {
  dataVencimento?: string | null;
  dataPagamento?: string | null;
};

export type RestImpostoAPagarEmpresa = RestOf<IImpostoAPagarEmpresa>;

export type NewRestImpostoAPagarEmpresa = RestOf<NewImpostoAPagarEmpresa>;

export type PartialUpdateRestImpostoAPagarEmpresa = RestOf<PartialUpdateImpostoAPagarEmpresa>;

export type EntityResponseType = HttpResponse<IImpostoAPagarEmpresa>;
export type EntityArrayResponseType = HttpResponse<IImpostoAPagarEmpresa[]>;

@Injectable({ providedIn: 'root' })
export class ImpostoAPagarEmpresaService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/imposto-a-pagar-empresas');

  create(impostoAPagarEmpresa: NewImpostoAPagarEmpresa): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(impostoAPagarEmpresa);
    return this.http
      .post<RestImpostoAPagarEmpresa>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(impostoAPagarEmpresa: IImpostoAPagarEmpresa): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(impostoAPagarEmpresa);
    return this.http
      .put<RestImpostoAPagarEmpresa>(`${this.resourceUrl}/${this.getImpostoAPagarEmpresaIdentifier(impostoAPagarEmpresa)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(impostoAPagarEmpresa: PartialUpdateImpostoAPagarEmpresa): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(impostoAPagarEmpresa);
    return this.http
      .patch<RestImpostoAPagarEmpresa>(`${this.resourceUrl}/${this.getImpostoAPagarEmpresaIdentifier(impostoAPagarEmpresa)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestImpostoAPagarEmpresa>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestImpostoAPagarEmpresa[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getImpostoAPagarEmpresaIdentifier(impostoAPagarEmpresa: Pick<IImpostoAPagarEmpresa, 'id'>): number {
    return impostoAPagarEmpresa.id;
  }

  compareImpostoAPagarEmpresa(o1: Pick<IImpostoAPagarEmpresa, 'id'> | null, o2: Pick<IImpostoAPagarEmpresa, 'id'> | null): boolean {
    return o1 && o2 ? this.getImpostoAPagarEmpresaIdentifier(o1) === this.getImpostoAPagarEmpresaIdentifier(o2) : o1 === o2;
  }

  addImpostoAPagarEmpresaToCollectionIfMissing<Type extends Pick<IImpostoAPagarEmpresa, 'id'>>(
    impostoAPagarEmpresaCollection: Type[],
    ...impostoAPagarEmpresasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const impostoAPagarEmpresas: Type[] = impostoAPagarEmpresasToCheck.filter(isPresent);
    if (impostoAPagarEmpresas.length > 0) {
      const impostoAPagarEmpresaCollectionIdentifiers = impostoAPagarEmpresaCollection.map(impostoAPagarEmpresaItem =>
        this.getImpostoAPagarEmpresaIdentifier(impostoAPagarEmpresaItem),
      );
      const impostoAPagarEmpresasToAdd = impostoAPagarEmpresas.filter(impostoAPagarEmpresaItem => {
        const impostoAPagarEmpresaIdentifier = this.getImpostoAPagarEmpresaIdentifier(impostoAPagarEmpresaItem);
        if (impostoAPagarEmpresaCollectionIdentifiers.includes(impostoAPagarEmpresaIdentifier)) {
          return false;
        }
        impostoAPagarEmpresaCollectionIdentifiers.push(impostoAPagarEmpresaIdentifier);
        return true;
      });
      return [...impostoAPagarEmpresasToAdd, ...impostoAPagarEmpresaCollection];
    }
    return impostoAPagarEmpresaCollection;
  }

  protected convertDateFromClient<T extends IImpostoAPagarEmpresa | NewImpostoAPagarEmpresa | PartialUpdateImpostoAPagarEmpresa>(
    impostoAPagarEmpresa: T,
  ): RestOf<T> {
    return {
      ...impostoAPagarEmpresa,
      dataVencimento: impostoAPagarEmpresa.dataVencimento?.toJSON() ?? null,
      dataPagamento: impostoAPagarEmpresa.dataPagamento?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restImpostoAPagarEmpresa: RestImpostoAPagarEmpresa): IImpostoAPagarEmpresa {
    return {
      ...restImpostoAPagarEmpresa,
      dataVencimento: restImpostoAPagarEmpresa.dataVencimento ? dayjs(restImpostoAPagarEmpresa.dataVencimento) : undefined,
      dataPagamento: restImpostoAPagarEmpresa.dataPagamento ? dayjs(restImpostoAPagarEmpresa.dataPagamento) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestImpostoAPagarEmpresa>): HttpResponse<IImpostoAPagarEmpresa> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestImpostoAPagarEmpresa[]>): HttpResponse<IImpostoAPagarEmpresa[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
