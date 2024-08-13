import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IOrdemServico, NewOrdemServico } from '../ordem-servico.model';

export type PartialUpdateOrdemServico = Partial<IOrdemServico> & Pick<IOrdemServico, 'id'>;

type RestOf<T extends IOrdemServico | NewOrdemServico> = Omit<T, 'prazo' | 'dataCriacao' | 'dataHoraCancelamento'> & {
  prazo?: string | null;
  dataCriacao?: string | null;
  dataHoraCancelamento?: string | null;
};

export type RestOrdemServico = RestOf<IOrdemServico>;

export type NewRestOrdemServico = RestOf<NewOrdemServico>;

export type PartialUpdateRestOrdemServico = RestOf<PartialUpdateOrdemServico>;

export type EntityResponseType = HttpResponse<IOrdemServico>;
export type EntityArrayResponseType = HttpResponse<IOrdemServico[]>;

@Injectable({ providedIn: 'root' })
export class OrdemServicoService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/ordem-servicos');

  create(ordemServico: NewOrdemServico): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(ordemServico);
    return this.http
      .post<RestOrdemServico>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(ordemServico: IOrdemServico): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(ordemServico);
    return this.http
      .put<RestOrdemServico>(`${this.resourceUrl}/${this.getOrdemServicoIdentifier(ordemServico)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(ordemServico: PartialUpdateOrdemServico): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(ordemServico);
    return this.http
      .patch<RestOrdemServico>(`${this.resourceUrl}/${this.getOrdemServicoIdentifier(ordemServico)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestOrdemServico>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestOrdemServico[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getOrdemServicoIdentifier(ordemServico: Pick<IOrdemServico, 'id'>): number {
    return ordemServico.id;
  }

  compareOrdemServico(o1: Pick<IOrdemServico, 'id'> | null, o2: Pick<IOrdemServico, 'id'> | null): boolean {
    return o1 && o2 ? this.getOrdemServicoIdentifier(o1) === this.getOrdemServicoIdentifier(o2) : o1 === o2;
  }

  addOrdemServicoToCollectionIfMissing<Type extends Pick<IOrdemServico, 'id'>>(
    ordemServicoCollection: Type[],
    ...ordemServicosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const ordemServicos: Type[] = ordemServicosToCheck.filter(isPresent);
    if (ordemServicos.length > 0) {
      const ordemServicoCollectionIdentifiers = ordemServicoCollection.map(ordemServicoItem =>
        this.getOrdemServicoIdentifier(ordemServicoItem),
      );
      const ordemServicosToAdd = ordemServicos.filter(ordemServicoItem => {
        const ordemServicoIdentifier = this.getOrdemServicoIdentifier(ordemServicoItem);
        if (ordemServicoCollectionIdentifiers.includes(ordemServicoIdentifier)) {
          return false;
        }
        ordemServicoCollectionIdentifiers.push(ordemServicoIdentifier);
        return true;
      });
      return [...ordemServicosToAdd, ...ordemServicoCollection];
    }
    return ordemServicoCollection;
  }

  protected convertDateFromClient<T extends IOrdemServico | NewOrdemServico | PartialUpdateOrdemServico>(ordemServico: T): RestOf<T> {
    return {
      ...ordemServico,
      prazo: ordemServico.prazo?.toJSON() ?? null,
      dataCriacao: ordemServico.dataCriacao?.toJSON() ?? null,
      dataHoraCancelamento: ordemServico.dataHoraCancelamento?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restOrdemServico: RestOrdemServico): IOrdemServico {
    return {
      ...restOrdemServico,
      prazo: restOrdemServico.prazo ? dayjs(restOrdemServico.prazo) : undefined,
      dataCriacao: restOrdemServico.dataCriacao ? dayjs(restOrdemServico.dataCriacao) : undefined,
      dataHoraCancelamento: restOrdemServico.dataHoraCancelamento ? dayjs(restOrdemServico.dataHoraCancelamento) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestOrdemServico>): HttpResponse<IOrdemServico> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestOrdemServico[]>): HttpResponse<IOrdemServico[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
