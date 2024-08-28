import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPeriodoPagamento, NewPeriodoPagamento } from '../periodo-pagamento.model';

export type PartialUpdatePeriodoPagamento = Partial<IPeriodoPagamento> & Pick<IPeriodoPagamento, 'id'>;

export type EntityResponseType = HttpResponse<IPeriodoPagamento>;
export type EntityArrayResponseType = HttpResponse<IPeriodoPagamento[]>;

@Injectable({ providedIn: 'root' })
export class PeriodoPagamentoService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/periodo-pagamentos');

  create(periodoPagamento: NewPeriodoPagamento): Observable<EntityResponseType> {
    return this.http.post<IPeriodoPagamento>(this.resourceUrl, periodoPagamento, { observe: 'response' });
  }

  update(periodoPagamento: IPeriodoPagamento): Observable<EntityResponseType> {
    return this.http.put<IPeriodoPagamento>(
      `${this.resourceUrl}/${this.getPeriodoPagamentoIdentifier(periodoPagamento)}`,
      periodoPagamento,
      { observe: 'response' },
    );
  }

  partialUpdate(periodoPagamento: PartialUpdatePeriodoPagamento): Observable<EntityResponseType> {
    return this.http.patch<IPeriodoPagamento>(
      `${this.resourceUrl}/${this.getPeriodoPagamentoIdentifier(periodoPagamento)}`,
      periodoPagamento,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPeriodoPagamento>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPeriodoPagamento[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPeriodoPagamentoIdentifier(periodoPagamento: Pick<IPeriodoPagamento, 'id'>): number {
    return periodoPagamento.id;
  }

  comparePeriodoPagamento(o1: Pick<IPeriodoPagamento, 'id'> | null, o2: Pick<IPeriodoPagamento, 'id'> | null): boolean {
    return o1 && o2 ? this.getPeriodoPagamentoIdentifier(o1) === this.getPeriodoPagamentoIdentifier(o2) : o1 === o2;
  }

  addPeriodoPagamentoToCollectionIfMissing<Type extends Pick<IPeriodoPagamento, 'id'>>(
    periodoPagamentoCollection: Type[],
    ...periodoPagamentosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const periodoPagamentos: Type[] = periodoPagamentosToCheck.filter(isPresent);
    if (periodoPagamentos.length > 0) {
      const periodoPagamentoCollectionIdentifiers = periodoPagamentoCollection.map(periodoPagamentoItem =>
        this.getPeriodoPagamentoIdentifier(periodoPagamentoItem),
      );
      const periodoPagamentosToAdd = periodoPagamentos.filter(periodoPagamentoItem => {
        const periodoPagamentoIdentifier = this.getPeriodoPagamentoIdentifier(periodoPagamentoItem);
        if (periodoPagamentoCollectionIdentifiers.includes(periodoPagamentoIdentifier)) {
          return false;
        }
        periodoPagamentoCollectionIdentifiers.push(periodoPagamentoIdentifier);
        return true;
      });
      return [...periodoPagamentosToAdd, ...periodoPagamentoCollection];
    }
    return periodoPagamentoCollection;
  }
}
