import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IServicoContabilOrdemServico, NewServicoContabilOrdemServico } from '../servico-contabil-ordem-servico.model';

export type PartialUpdateServicoContabilOrdemServico = Partial<IServicoContabilOrdemServico> & Pick<IServicoContabilOrdemServico, 'id'>;

type RestOf<T extends IServicoContabilOrdemServico | NewServicoContabilOrdemServico> = Omit<T, 'dataAdmin' | 'dataLegal'> & {
  dataAdmin?: string | null;
  dataLegal?: string | null;
};

export type RestServicoContabilOrdemServico = RestOf<IServicoContabilOrdemServico>;

export type NewRestServicoContabilOrdemServico = RestOf<NewServicoContabilOrdemServico>;

export type PartialUpdateRestServicoContabilOrdemServico = RestOf<PartialUpdateServicoContabilOrdemServico>;

export type EntityResponseType = HttpResponse<IServicoContabilOrdemServico>;
export type EntityArrayResponseType = HttpResponse<IServicoContabilOrdemServico[]>;

@Injectable({ providedIn: 'root' })
export class ServicoContabilOrdemServicoService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/servico-contabil-ordem-servicos');

  create(servicoContabilOrdemServico: NewServicoContabilOrdemServico): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(servicoContabilOrdemServico);
    return this.http
      .post<RestServicoContabilOrdemServico>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(servicoContabilOrdemServico: IServicoContabilOrdemServico): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(servicoContabilOrdemServico);
    return this.http
      .put<RestServicoContabilOrdemServico>(
        `${this.resourceUrl}/${this.getServicoContabilOrdemServicoIdentifier(servicoContabilOrdemServico)}`,
        copy,
        { observe: 'response' },
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(servicoContabilOrdemServico: PartialUpdateServicoContabilOrdemServico): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(servicoContabilOrdemServico);
    return this.http
      .patch<RestServicoContabilOrdemServico>(
        `${this.resourceUrl}/${this.getServicoContabilOrdemServicoIdentifier(servicoContabilOrdemServico)}`,
        copy,
        { observe: 'response' },
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestServicoContabilOrdemServico>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestServicoContabilOrdemServico[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getServicoContabilOrdemServicoIdentifier(servicoContabilOrdemServico: Pick<IServicoContabilOrdemServico, 'id'>): number {
    return servicoContabilOrdemServico.id;
  }

  compareServicoContabilOrdemServico(
    o1: Pick<IServicoContabilOrdemServico, 'id'> | null,
    o2: Pick<IServicoContabilOrdemServico, 'id'> | null,
  ): boolean {
    return o1 && o2 ? this.getServicoContabilOrdemServicoIdentifier(o1) === this.getServicoContabilOrdemServicoIdentifier(o2) : o1 === o2;
  }

  addServicoContabilOrdemServicoToCollectionIfMissing<Type extends Pick<IServicoContabilOrdemServico, 'id'>>(
    servicoContabilOrdemServicoCollection: Type[],
    ...servicoContabilOrdemServicosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const servicoContabilOrdemServicos: Type[] = servicoContabilOrdemServicosToCheck.filter(isPresent);
    if (servicoContabilOrdemServicos.length > 0) {
      const servicoContabilOrdemServicoCollectionIdentifiers = servicoContabilOrdemServicoCollection.map(servicoContabilOrdemServicoItem =>
        this.getServicoContabilOrdemServicoIdentifier(servicoContabilOrdemServicoItem),
      );
      const servicoContabilOrdemServicosToAdd = servicoContabilOrdemServicos.filter(servicoContabilOrdemServicoItem => {
        const servicoContabilOrdemServicoIdentifier = this.getServicoContabilOrdemServicoIdentifier(servicoContabilOrdemServicoItem);
        if (servicoContabilOrdemServicoCollectionIdentifiers.includes(servicoContabilOrdemServicoIdentifier)) {
          return false;
        }
        servicoContabilOrdemServicoCollectionIdentifiers.push(servicoContabilOrdemServicoIdentifier);
        return true;
      });
      return [...servicoContabilOrdemServicosToAdd, ...servicoContabilOrdemServicoCollection];
    }
    return servicoContabilOrdemServicoCollection;
  }

  protected convertDateFromClient<
    T extends IServicoContabilOrdemServico | NewServicoContabilOrdemServico | PartialUpdateServicoContabilOrdemServico,
  >(servicoContabilOrdemServico: T): RestOf<T> {
    return {
      ...servicoContabilOrdemServico,
      dataAdmin: servicoContabilOrdemServico.dataAdmin?.toJSON() ?? null,
      dataLegal: servicoContabilOrdemServico.dataLegal?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restServicoContabilOrdemServico: RestServicoContabilOrdemServico): IServicoContabilOrdemServico {
    return {
      ...restServicoContabilOrdemServico,
      dataAdmin: restServicoContabilOrdemServico.dataAdmin ? dayjs(restServicoContabilOrdemServico.dataAdmin) : undefined,
      dataLegal: restServicoContabilOrdemServico.dataLegal ? dayjs(restServicoContabilOrdemServico.dataLegal) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestServicoContabilOrdemServico>): HttpResponse<IServicoContabilOrdemServico> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(
    res: HttpResponse<RestServicoContabilOrdemServico[]>,
  ): HttpResponse<IServicoContabilOrdemServico[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
