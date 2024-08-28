import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDescontoPeriodoPagamento, NewDescontoPeriodoPagamento } from '../desconto-periodo-pagamento.model';

export type PartialUpdateDescontoPeriodoPagamento = Partial<IDescontoPeriodoPagamento> & Pick<IDescontoPeriodoPagamento, 'id'>;

export type EntityResponseType = HttpResponse<IDescontoPeriodoPagamento>;
export type EntityArrayResponseType = HttpResponse<IDescontoPeriodoPagamento[]>;

@Injectable({ providedIn: 'root' })
export class DescontoPeriodoPagamentoService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/desconto-periodo-pagamentos');

  create(descontoPeriodoPagamento: NewDescontoPeriodoPagamento): Observable<EntityResponseType> {
    return this.http.post<IDescontoPeriodoPagamento>(this.resourceUrl, descontoPeriodoPagamento, { observe: 'response' });
  }

  update(descontoPeriodoPagamento: IDescontoPeriodoPagamento): Observable<EntityResponseType> {
    return this.http.put<IDescontoPeriodoPagamento>(
      `${this.resourceUrl}/${this.getDescontoPeriodoPagamentoIdentifier(descontoPeriodoPagamento)}`,
      descontoPeriodoPagamento,
      { observe: 'response' },
    );
  }

  partialUpdate(descontoPeriodoPagamento: PartialUpdateDescontoPeriodoPagamento): Observable<EntityResponseType> {
    return this.http.patch<IDescontoPeriodoPagamento>(
      `${this.resourceUrl}/${this.getDescontoPeriodoPagamentoIdentifier(descontoPeriodoPagamento)}`,
      descontoPeriodoPagamento,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDescontoPeriodoPagamento>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDescontoPeriodoPagamento[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getDescontoPeriodoPagamentoIdentifier(descontoPeriodoPagamento: Pick<IDescontoPeriodoPagamento, 'id'>): number {
    return descontoPeriodoPagamento.id;
  }

  compareDescontoPeriodoPagamento(
    o1: Pick<IDescontoPeriodoPagamento, 'id'> | null,
    o2: Pick<IDescontoPeriodoPagamento, 'id'> | null,
  ): boolean {
    return o1 && o2 ? this.getDescontoPeriodoPagamentoIdentifier(o1) === this.getDescontoPeriodoPagamentoIdentifier(o2) : o1 === o2;
  }

  addDescontoPeriodoPagamentoToCollectionIfMissing<Type extends Pick<IDescontoPeriodoPagamento, 'id'>>(
    descontoPeriodoPagamentoCollection: Type[],
    ...descontoPeriodoPagamentosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const descontoPeriodoPagamentos: Type[] = descontoPeriodoPagamentosToCheck.filter(isPresent);
    if (descontoPeriodoPagamentos.length > 0) {
      const descontoPeriodoPagamentoCollectionIdentifiers = descontoPeriodoPagamentoCollection.map(descontoPeriodoPagamentoItem =>
        this.getDescontoPeriodoPagamentoIdentifier(descontoPeriodoPagamentoItem),
      );
      const descontoPeriodoPagamentosToAdd = descontoPeriodoPagamentos.filter(descontoPeriodoPagamentoItem => {
        const descontoPeriodoPagamentoIdentifier = this.getDescontoPeriodoPagamentoIdentifier(descontoPeriodoPagamentoItem);
        if (descontoPeriodoPagamentoCollectionIdentifiers.includes(descontoPeriodoPagamentoIdentifier)) {
          return false;
        }
        descontoPeriodoPagamentoCollectionIdentifiers.push(descontoPeriodoPagamentoIdentifier);
        return true;
      });
      return [...descontoPeriodoPagamentosToAdd, ...descontoPeriodoPagamentoCollection];
    }
    return descontoPeriodoPagamentoCollection;
  }
}
