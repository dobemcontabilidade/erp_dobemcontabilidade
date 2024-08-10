import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IFormaDePagamento, NewFormaDePagamento } from '../forma-de-pagamento.model';

export type PartialUpdateFormaDePagamento = Partial<IFormaDePagamento> & Pick<IFormaDePagamento, 'id'>;

export type EntityResponseType = HttpResponse<IFormaDePagamento>;
export type EntityArrayResponseType = HttpResponse<IFormaDePagamento[]>;

@Injectable({ providedIn: 'root' })
export class FormaDePagamentoService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/forma-de-pagamentos');

  create(formaDePagamento: NewFormaDePagamento): Observable<EntityResponseType> {
    return this.http.post<IFormaDePagamento>(this.resourceUrl, formaDePagamento, { observe: 'response' });
  }

  update(formaDePagamento: IFormaDePagamento): Observable<EntityResponseType> {
    return this.http.put<IFormaDePagamento>(
      `${this.resourceUrl}/${this.getFormaDePagamentoIdentifier(formaDePagamento)}`,
      formaDePagamento,
      { observe: 'response' },
    );
  }

  partialUpdate(formaDePagamento: PartialUpdateFormaDePagamento): Observable<EntityResponseType> {
    return this.http.patch<IFormaDePagamento>(
      `${this.resourceUrl}/${this.getFormaDePagamentoIdentifier(formaDePagamento)}`,
      formaDePagamento,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFormaDePagamento>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFormaDePagamento[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getFormaDePagamentoIdentifier(formaDePagamento: Pick<IFormaDePagamento, 'id'>): number {
    return formaDePagamento.id;
  }

  compareFormaDePagamento(o1: Pick<IFormaDePagamento, 'id'> | null, o2: Pick<IFormaDePagamento, 'id'> | null): boolean {
    return o1 && o2 ? this.getFormaDePagamentoIdentifier(o1) === this.getFormaDePagamentoIdentifier(o2) : o1 === o2;
  }

  addFormaDePagamentoToCollectionIfMissing<Type extends Pick<IFormaDePagamento, 'id'>>(
    formaDePagamentoCollection: Type[],
    ...formaDePagamentosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const formaDePagamentos: Type[] = formaDePagamentosToCheck.filter(isPresent);
    if (formaDePagamentos.length > 0) {
      const formaDePagamentoCollectionIdentifiers = formaDePagamentoCollection.map(formaDePagamentoItem =>
        this.getFormaDePagamentoIdentifier(formaDePagamentoItem),
      );
      const formaDePagamentosToAdd = formaDePagamentos.filter(formaDePagamentoItem => {
        const formaDePagamentoIdentifier = this.getFormaDePagamentoIdentifier(formaDePagamentoItem);
        if (formaDePagamentoCollectionIdentifiers.includes(formaDePagamentoIdentifier)) {
          return false;
        }
        formaDePagamentoCollectionIdentifiers.push(formaDePagamentoIdentifier);
        return true;
      });
      return [...formaDePagamentosToAdd, ...formaDePagamentoCollection];
    }
    return formaDePagamentoCollection;
  }
}
