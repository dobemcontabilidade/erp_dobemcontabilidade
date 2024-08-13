import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IServicoContabilEtapaFluxoExecucao, NewServicoContabilEtapaFluxoExecucao } from '../servico-contabil-etapa-fluxo-execucao.model';

export type PartialUpdateServicoContabilEtapaFluxoExecucao = Partial<IServicoContabilEtapaFluxoExecucao> &
  Pick<IServicoContabilEtapaFluxoExecucao, 'id'>;

export type EntityResponseType = HttpResponse<IServicoContabilEtapaFluxoExecucao>;
export type EntityArrayResponseType = HttpResponse<IServicoContabilEtapaFluxoExecucao[]>;

@Injectable({ providedIn: 'root' })
export class ServicoContabilEtapaFluxoExecucaoService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/servico-contabil-etapa-fluxo-execucaos');

  create(servicoContabilEtapaFluxoExecucao: NewServicoContabilEtapaFluxoExecucao): Observable<EntityResponseType> {
    return this.http.post<IServicoContabilEtapaFluxoExecucao>(this.resourceUrl, servicoContabilEtapaFluxoExecucao, { observe: 'response' });
  }

  update(servicoContabilEtapaFluxoExecucao: IServicoContabilEtapaFluxoExecucao): Observable<EntityResponseType> {
    return this.http.put<IServicoContabilEtapaFluxoExecucao>(
      `${this.resourceUrl}/${this.getServicoContabilEtapaFluxoExecucaoIdentifier(servicoContabilEtapaFluxoExecucao)}`,
      servicoContabilEtapaFluxoExecucao,
      { observe: 'response' },
    );
  }

  partialUpdate(servicoContabilEtapaFluxoExecucao: PartialUpdateServicoContabilEtapaFluxoExecucao): Observable<EntityResponseType> {
    return this.http.patch<IServicoContabilEtapaFluxoExecucao>(
      `${this.resourceUrl}/${this.getServicoContabilEtapaFluxoExecucaoIdentifier(servicoContabilEtapaFluxoExecucao)}`,
      servicoContabilEtapaFluxoExecucao,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IServicoContabilEtapaFluxoExecucao>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IServicoContabilEtapaFluxoExecucao[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getServicoContabilEtapaFluxoExecucaoIdentifier(
    servicoContabilEtapaFluxoExecucao: Pick<IServicoContabilEtapaFluxoExecucao, 'id'>,
  ): number {
    return servicoContabilEtapaFluxoExecucao.id;
  }

  compareServicoContabilEtapaFluxoExecucao(
    o1: Pick<IServicoContabilEtapaFluxoExecucao, 'id'> | null,
    o2: Pick<IServicoContabilEtapaFluxoExecucao, 'id'> | null,
  ): boolean {
    return o1 && o2
      ? this.getServicoContabilEtapaFluxoExecucaoIdentifier(o1) === this.getServicoContabilEtapaFluxoExecucaoIdentifier(o2)
      : o1 === o2;
  }

  addServicoContabilEtapaFluxoExecucaoToCollectionIfMissing<Type extends Pick<IServicoContabilEtapaFluxoExecucao, 'id'>>(
    servicoContabilEtapaFluxoExecucaoCollection: Type[],
    ...servicoContabilEtapaFluxoExecucaosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const servicoContabilEtapaFluxoExecucaos: Type[] = servicoContabilEtapaFluxoExecucaosToCheck.filter(isPresent);
    if (servicoContabilEtapaFluxoExecucaos.length > 0) {
      const servicoContabilEtapaFluxoExecucaoCollectionIdentifiers = servicoContabilEtapaFluxoExecucaoCollection.map(
        servicoContabilEtapaFluxoExecucaoItem => this.getServicoContabilEtapaFluxoExecucaoIdentifier(servicoContabilEtapaFluxoExecucaoItem),
      );
      const servicoContabilEtapaFluxoExecucaosToAdd = servicoContabilEtapaFluxoExecucaos.filter(servicoContabilEtapaFluxoExecucaoItem => {
        const servicoContabilEtapaFluxoExecucaoIdentifier = this.getServicoContabilEtapaFluxoExecucaoIdentifier(
          servicoContabilEtapaFluxoExecucaoItem,
        );
        if (servicoContabilEtapaFluxoExecucaoCollectionIdentifiers.includes(servicoContabilEtapaFluxoExecucaoIdentifier)) {
          return false;
        }
        servicoContabilEtapaFluxoExecucaoCollectionIdentifiers.push(servicoContabilEtapaFluxoExecucaoIdentifier);
        return true;
      });
      return [...servicoContabilEtapaFluxoExecucaosToAdd, ...servicoContabilEtapaFluxoExecucaoCollection];
    }
    return servicoContabilEtapaFluxoExecucaoCollection;
  }
}
