import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAdicionalTributacao, NewAdicionalTributacao } from '../adicional-tributacao.model';

export type PartialUpdateAdicionalTributacao = Partial<IAdicionalTributacao> & Pick<IAdicionalTributacao, 'id'>;

export type EntityResponseType = HttpResponse<IAdicionalTributacao>;
export type EntityArrayResponseType = HttpResponse<IAdicionalTributacao[]>;

@Injectable({ providedIn: 'root' })
export class AdicionalTributacaoService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/adicional-tributacaos');

  create(adicionalTributacao: NewAdicionalTributacao): Observable<EntityResponseType> {
    return this.http.post<IAdicionalTributacao>(this.resourceUrl, adicionalTributacao, { observe: 'response' });
  }

  update(adicionalTributacao: IAdicionalTributacao): Observable<EntityResponseType> {
    return this.http.put<IAdicionalTributacao>(
      `${this.resourceUrl}/${this.getAdicionalTributacaoIdentifier(adicionalTributacao)}`,
      adicionalTributacao,
      { observe: 'response' },
    );
  }

  partialUpdate(adicionalTributacao: PartialUpdateAdicionalTributacao): Observable<EntityResponseType> {
    return this.http.patch<IAdicionalTributacao>(
      `${this.resourceUrl}/${this.getAdicionalTributacaoIdentifier(adicionalTributacao)}`,
      adicionalTributacao,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAdicionalTributacao>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAdicionalTributacao[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAdicionalTributacaoIdentifier(adicionalTributacao: Pick<IAdicionalTributacao, 'id'>): number {
    return adicionalTributacao.id;
  }

  compareAdicionalTributacao(o1: Pick<IAdicionalTributacao, 'id'> | null, o2: Pick<IAdicionalTributacao, 'id'> | null): boolean {
    return o1 && o2 ? this.getAdicionalTributacaoIdentifier(o1) === this.getAdicionalTributacaoIdentifier(o2) : o1 === o2;
  }

  addAdicionalTributacaoToCollectionIfMissing<Type extends Pick<IAdicionalTributacao, 'id'>>(
    adicionalTributacaoCollection: Type[],
    ...adicionalTributacaosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const adicionalTributacaos: Type[] = adicionalTributacaosToCheck.filter(isPresent);
    if (adicionalTributacaos.length > 0) {
      const adicionalTributacaoCollectionIdentifiers = adicionalTributacaoCollection.map(adicionalTributacaoItem =>
        this.getAdicionalTributacaoIdentifier(adicionalTributacaoItem),
      );
      const adicionalTributacaosToAdd = adicionalTributacaos.filter(adicionalTributacaoItem => {
        const adicionalTributacaoIdentifier = this.getAdicionalTributacaoIdentifier(adicionalTributacaoItem);
        if (adicionalTributacaoCollectionIdentifiers.includes(adicionalTributacaoIdentifier)) {
          return false;
        }
        adicionalTributacaoCollectionIdentifiers.push(adicionalTributacaoIdentifier);
        return true;
      });
      return [...adicionalTributacaosToAdd, ...adicionalTributacaoCollection];
    }
    return adicionalTributacaoCollection;
  }
}
