import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICriterioAvaliacaoAtor, NewCriterioAvaliacaoAtor } from '../criterio-avaliacao-ator.model';

export type PartialUpdateCriterioAvaliacaoAtor = Partial<ICriterioAvaliacaoAtor> & Pick<ICriterioAvaliacaoAtor, 'id'>;

export type EntityResponseType = HttpResponse<ICriterioAvaliacaoAtor>;
export type EntityArrayResponseType = HttpResponse<ICriterioAvaliacaoAtor[]>;

@Injectable({ providedIn: 'root' })
export class CriterioAvaliacaoAtorService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/criterio-avaliacao-ators');

  create(criterioAvaliacaoAtor: NewCriterioAvaliacaoAtor): Observable<EntityResponseType> {
    return this.http.post<ICriterioAvaliacaoAtor>(this.resourceUrl, criterioAvaliacaoAtor, { observe: 'response' });
  }

  update(criterioAvaliacaoAtor: ICriterioAvaliacaoAtor): Observable<EntityResponseType> {
    return this.http.put<ICriterioAvaliacaoAtor>(
      `${this.resourceUrl}/${this.getCriterioAvaliacaoAtorIdentifier(criterioAvaliacaoAtor)}`,
      criterioAvaliacaoAtor,
      { observe: 'response' },
    );
  }

  partialUpdate(criterioAvaliacaoAtor: PartialUpdateCriterioAvaliacaoAtor): Observable<EntityResponseType> {
    return this.http.patch<ICriterioAvaliacaoAtor>(
      `${this.resourceUrl}/${this.getCriterioAvaliacaoAtorIdentifier(criterioAvaliacaoAtor)}`,
      criterioAvaliacaoAtor,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICriterioAvaliacaoAtor>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICriterioAvaliacaoAtor[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCriterioAvaliacaoAtorIdentifier(criterioAvaliacaoAtor: Pick<ICriterioAvaliacaoAtor, 'id'>): number {
    return criterioAvaliacaoAtor.id;
  }

  compareCriterioAvaliacaoAtor(o1: Pick<ICriterioAvaliacaoAtor, 'id'> | null, o2: Pick<ICriterioAvaliacaoAtor, 'id'> | null): boolean {
    return o1 && o2 ? this.getCriterioAvaliacaoAtorIdentifier(o1) === this.getCriterioAvaliacaoAtorIdentifier(o2) : o1 === o2;
  }

  addCriterioAvaliacaoAtorToCollectionIfMissing<Type extends Pick<ICriterioAvaliacaoAtor, 'id'>>(
    criterioAvaliacaoAtorCollection: Type[],
    ...criterioAvaliacaoAtorsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const criterioAvaliacaoAtors: Type[] = criterioAvaliacaoAtorsToCheck.filter(isPresent);
    if (criterioAvaliacaoAtors.length > 0) {
      const criterioAvaliacaoAtorCollectionIdentifiers = criterioAvaliacaoAtorCollection.map(criterioAvaliacaoAtorItem =>
        this.getCriterioAvaliacaoAtorIdentifier(criterioAvaliacaoAtorItem),
      );
      const criterioAvaliacaoAtorsToAdd = criterioAvaliacaoAtors.filter(criterioAvaliacaoAtorItem => {
        const criterioAvaliacaoAtorIdentifier = this.getCriterioAvaliacaoAtorIdentifier(criterioAvaliacaoAtorItem);
        if (criterioAvaliacaoAtorCollectionIdentifiers.includes(criterioAvaliacaoAtorIdentifier)) {
          return false;
        }
        criterioAvaliacaoAtorCollectionIdentifiers.push(criterioAvaliacaoAtorIdentifier);
        return true;
      });
      return [...criterioAvaliacaoAtorsToAdd, ...criterioAvaliacaoAtorCollection];
    }
    return criterioAvaliacaoAtorCollection;
  }
}
