import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICriterioAvaliacao, NewCriterioAvaliacao } from '../criterio-avaliacao.model';

export type PartialUpdateCriterioAvaliacao = Partial<ICriterioAvaliacao> & Pick<ICriterioAvaliacao, 'id'>;

export type EntityResponseType = HttpResponse<ICriterioAvaliacao>;
export type EntityArrayResponseType = HttpResponse<ICriterioAvaliacao[]>;

@Injectable({ providedIn: 'root' })
export class CriterioAvaliacaoService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/criterio-avaliacaos');

  create(criterioAvaliacao: NewCriterioAvaliacao): Observable<EntityResponseType> {
    return this.http.post<ICriterioAvaliacao>(this.resourceUrl, criterioAvaliacao, { observe: 'response' });
  }

  update(criterioAvaliacao: ICriterioAvaliacao): Observable<EntityResponseType> {
    return this.http.put<ICriterioAvaliacao>(
      `${this.resourceUrl}/${this.getCriterioAvaliacaoIdentifier(criterioAvaliacao)}`,
      criterioAvaliacao,
      { observe: 'response' },
    );
  }

  partialUpdate(criterioAvaliacao: PartialUpdateCriterioAvaliacao): Observable<EntityResponseType> {
    return this.http.patch<ICriterioAvaliacao>(
      `${this.resourceUrl}/${this.getCriterioAvaliacaoIdentifier(criterioAvaliacao)}`,
      criterioAvaliacao,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICriterioAvaliacao>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICriterioAvaliacao[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCriterioAvaliacaoIdentifier(criterioAvaliacao: Pick<ICriterioAvaliacao, 'id'>): number {
    return criterioAvaliacao.id;
  }

  compareCriterioAvaliacao(o1: Pick<ICriterioAvaliacao, 'id'> | null, o2: Pick<ICriterioAvaliacao, 'id'> | null): boolean {
    return o1 && o2 ? this.getCriterioAvaliacaoIdentifier(o1) === this.getCriterioAvaliacaoIdentifier(o2) : o1 === o2;
  }

  addCriterioAvaliacaoToCollectionIfMissing<Type extends Pick<ICriterioAvaliacao, 'id'>>(
    criterioAvaliacaoCollection: Type[],
    ...criterioAvaliacaosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const criterioAvaliacaos: Type[] = criterioAvaliacaosToCheck.filter(isPresent);
    if (criterioAvaliacaos.length > 0) {
      const criterioAvaliacaoCollectionIdentifiers = criterioAvaliacaoCollection.map(criterioAvaliacaoItem =>
        this.getCriterioAvaliacaoIdentifier(criterioAvaliacaoItem),
      );
      const criterioAvaliacaosToAdd = criterioAvaliacaos.filter(criterioAvaliacaoItem => {
        const criterioAvaliacaoIdentifier = this.getCriterioAvaliacaoIdentifier(criterioAvaliacaoItem);
        if (criterioAvaliacaoCollectionIdentifiers.includes(criterioAvaliacaoIdentifier)) {
          return false;
        }
        criterioAvaliacaoCollectionIdentifiers.push(criterioAvaliacaoIdentifier);
        return true;
      });
      return [...criterioAvaliacaosToAdd, ...criterioAvaliacaoCollection];
    }
    return criterioAvaliacaoCollection;
  }
}
