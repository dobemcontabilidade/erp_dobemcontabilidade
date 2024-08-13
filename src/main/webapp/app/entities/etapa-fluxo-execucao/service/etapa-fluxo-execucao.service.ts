import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEtapaFluxoExecucao, NewEtapaFluxoExecucao } from '../etapa-fluxo-execucao.model';

export type PartialUpdateEtapaFluxoExecucao = Partial<IEtapaFluxoExecucao> & Pick<IEtapaFluxoExecucao, 'id'>;

export type EntityResponseType = HttpResponse<IEtapaFluxoExecucao>;
export type EntityArrayResponseType = HttpResponse<IEtapaFluxoExecucao[]>;

@Injectable({ providedIn: 'root' })
export class EtapaFluxoExecucaoService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/etapa-fluxo-execucaos');

  create(etapaFluxoExecucao: NewEtapaFluxoExecucao): Observable<EntityResponseType> {
    return this.http.post<IEtapaFluxoExecucao>(this.resourceUrl, etapaFluxoExecucao, { observe: 'response' });
  }

  update(etapaFluxoExecucao: IEtapaFluxoExecucao): Observable<EntityResponseType> {
    return this.http.put<IEtapaFluxoExecucao>(
      `${this.resourceUrl}/${this.getEtapaFluxoExecucaoIdentifier(etapaFluxoExecucao)}`,
      etapaFluxoExecucao,
      { observe: 'response' },
    );
  }

  partialUpdate(etapaFluxoExecucao: PartialUpdateEtapaFluxoExecucao): Observable<EntityResponseType> {
    return this.http.patch<IEtapaFluxoExecucao>(
      `${this.resourceUrl}/${this.getEtapaFluxoExecucaoIdentifier(etapaFluxoExecucao)}`,
      etapaFluxoExecucao,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEtapaFluxoExecucao>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEtapaFluxoExecucao[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getEtapaFluxoExecucaoIdentifier(etapaFluxoExecucao: Pick<IEtapaFluxoExecucao, 'id'>): number {
    return etapaFluxoExecucao.id;
  }

  compareEtapaFluxoExecucao(o1: Pick<IEtapaFluxoExecucao, 'id'> | null, o2: Pick<IEtapaFluxoExecucao, 'id'> | null): boolean {
    return o1 && o2 ? this.getEtapaFluxoExecucaoIdentifier(o1) === this.getEtapaFluxoExecucaoIdentifier(o2) : o1 === o2;
  }

  addEtapaFluxoExecucaoToCollectionIfMissing<Type extends Pick<IEtapaFluxoExecucao, 'id'>>(
    etapaFluxoExecucaoCollection: Type[],
    ...etapaFluxoExecucaosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const etapaFluxoExecucaos: Type[] = etapaFluxoExecucaosToCheck.filter(isPresent);
    if (etapaFluxoExecucaos.length > 0) {
      const etapaFluxoExecucaoCollectionIdentifiers = etapaFluxoExecucaoCollection.map(etapaFluxoExecucaoItem =>
        this.getEtapaFluxoExecucaoIdentifier(etapaFluxoExecucaoItem),
      );
      const etapaFluxoExecucaosToAdd = etapaFluxoExecucaos.filter(etapaFluxoExecucaoItem => {
        const etapaFluxoExecucaoIdentifier = this.getEtapaFluxoExecucaoIdentifier(etapaFluxoExecucaoItem);
        if (etapaFluxoExecucaoCollectionIdentifiers.includes(etapaFluxoExecucaoIdentifier)) {
          return false;
        }
        etapaFluxoExecucaoCollectionIdentifiers.push(etapaFluxoExecucaoIdentifier);
        return true;
      });
      return [...etapaFluxoExecucaosToAdd, ...etapaFluxoExecucaoCollection];
    }
    return etapaFluxoExecucaoCollection;
  }
}
