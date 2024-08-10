import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITributacao, NewTributacao } from '../tributacao.model';

export type PartialUpdateTributacao = Partial<ITributacao> & Pick<ITributacao, 'id'>;

export type EntityResponseType = HttpResponse<ITributacao>;
export type EntityArrayResponseType = HttpResponse<ITributacao[]>;

@Injectable({ providedIn: 'root' })
export class TributacaoService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/tributacaos');

  create(tributacao: NewTributacao): Observable<EntityResponseType> {
    return this.http.post<ITributacao>(this.resourceUrl, tributacao, { observe: 'response' });
  }

  update(tributacao: ITributacao): Observable<EntityResponseType> {
    return this.http.put<ITributacao>(`${this.resourceUrl}/${this.getTributacaoIdentifier(tributacao)}`, tributacao, {
      observe: 'response',
    });
  }

  partialUpdate(tributacao: PartialUpdateTributacao): Observable<EntityResponseType> {
    return this.http.patch<ITributacao>(`${this.resourceUrl}/${this.getTributacaoIdentifier(tributacao)}`, tributacao, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITributacao>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITributacao[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getTributacaoIdentifier(tributacao: Pick<ITributacao, 'id'>): number {
    return tributacao.id;
  }

  compareTributacao(o1: Pick<ITributacao, 'id'> | null, o2: Pick<ITributacao, 'id'> | null): boolean {
    return o1 && o2 ? this.getTributacaoIdentifier(o1) === this.getTributacaoIdentifier(o2) : o1 === o2;
  }

  addTributacaoToCollectionIfMissing<Type extends Pick<ITributacao, 'id'>>(
    tributacaoCollection: Type[],
    ...tributacaosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const tributacaos: Type[] = tributacaosToCheck.filter(isPresent);
    if (tributacaos.length > 0) {
      const tributacaoCollectionIdentifiers = tributacaoCollection.map(tributacaoItem => this.getTributacaoIdentifier(tributacaoItem));
      const tributacaosToAdd = tributacaos.filter(tributacaoItem => {
        const tributacaoIdentifier = this.getTributacaoIdentifier(tributacaoItem);
        if (tributacaoCollectionIdentifiers.includes(tributacaoIdentifier)) {
          return false;
        }
        tributacaoCollectionIdentifiers.push(tributacaoIdentifier);
        return true;
      });
      return [...tributacaosToAdd, ...tributacaoCollection];
    }
    return tributacaoCollection;
  }
}
