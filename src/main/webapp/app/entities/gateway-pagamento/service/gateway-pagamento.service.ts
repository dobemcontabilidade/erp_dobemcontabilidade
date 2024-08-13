import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IGatewayPagamento, NewGatewayPagamento } from '../gateway-pagamento.model';

export type PartialUpdateGatewayPagamento = Partial<IGatewayPagamento> & Pick<IGatewayPagamento, 'id'>;

export type EntityResponseType = HttpResponse<IGatewayPagamento>;
export type EntityArrayResponseType = HttpResponse<IGatewayPagamento[]>;

@Injectable({ providedIn: 'root' })
export class GatewayPagamentoService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/gateway-pagamentos');

  create(gatewayPagamento: NewGatewayPagamento): Observable<EntityResponseType> {
    return this.http.post<IGatewayPagamento>(this.resourceUrl, gatewayPagamento, { observe: 'response' });
  }

  update(gatewayPagamento: IGatewayPagamento): Observable<EntityResponseType> {
    return this.http.put<IGatewayPagamento>(
      `${this.resourceUrl}/${this.getGatewayPagamentoIdentifier(gatewayPagamento)}`,
      gatewayPagamento,
      { observe: 'response' },
    );
  }

  partialUpdate(gatewayPagamento: PartialUpdateGatewayPagamento): Observable<EntityResponseType> {
    return this.http.patch<IGatewayPagamento>(
      `${this.resourceUrl}/${this.getGatewayPagamentoIdentifier(gatewayPagamento)}`,
      gatewayPagamento,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IGatewayPagamento>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IGatewayPagamento[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getGatewayPagamentoIdentifier(gatewayPagamento: Pick<IGatewayPagamento, 'id'>): number {
    return gatewayPagamento.id;
  }

  compareGatewayPagamento(o1: Pick<IGatewayPagamento, 'id'> | null, o2: Pick<IGatewayPagamento, 'id'> | null): boolean {
    return o1 && o2 ? this.getGatewayPagamentoIdentifier(o1) === this.getGatewayPagamentoIdentifier(o2) : o1 === o2;
  }

  addGatewayPagamentoToCollectionIfMissing<Type extends Pick<IGatewayPagamento, 'id'>>(
    gatewayPagamentoCollection: Type[],
    ...gatewayPagamentosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const gatewayPagamentos: Type[] = gatewayPagamentosToCheck.filter(isPresent);
    if (gatewayPagamentos.length > 0) {
      const gatewayPagamentoCollectionIdentifiers = gatewayPagamentoCollection.map(gatewayPagamentoItem =>
        this.getGatewayPagamentoIdentifier(gatewayPagamentoItem),
      );
      const gatewayPagamentosToAdd = gatewayPagamentos.filter(gatewayPagamentoItem => {
        const gatewayPagamentoIdentifier = this.getGatewayPagamentoIdentifier(gatewayPagamentoItem);
        if (gatewayPagamentoCollectionIdentifiers.includes(gatewayPagamentoIdentifier)) {
          return false;
        }
        gatewayPagamentoCollectionIdentifiers.push(gatewayPagamentoIdentifier);
        return true;
      });
      return [...gatewayPagamentosToAdd, ...gatewayPagamentoCollection];
    }
    return gatewayPagamentoCollection;
  }
}
