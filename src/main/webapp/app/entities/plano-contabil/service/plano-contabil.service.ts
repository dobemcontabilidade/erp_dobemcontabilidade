import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPlanoContabil, NewPlanoContabil } from '../plano-contabil.model';

export type PartialUpdatePlanoContabil = Partial<IPlanoContabil> & Pick<IPlanoContabil, 'id'>;

export type EntityResponseType = HttpResponse<IPlanoContabil>;
export type EntityArrayResponseType = HttpResponse<IPlanoContabil[]>;

@Injectable({ providedIn: 'root' })
export class PlanoContabilService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/plano-contabils');

  create(planoContabil: NewPlanoContabil): Observable<EntityResponseType> {
    return this.http.post<IPlanoContabil>(this.resourceUrl, planoContabil, { observe: 'response' });
  }

  update(planoContabil: IPlanoContabil): Observable<EntityResponseType> {
    return this.http.put<IPlanoContabil>(`${this.resourceUrl}/${this.getPlanoContabilIdentifier(planoContabil)}`, planoContabil, {
      observe: 'response',
    });
  }

  partialUpdate(planoContabil: PartialUpdatePlanoContabil): Observable<EntityResponseType> {
    return this.http.patch<IPlanoContabil>(`${this.resourceUrl}/${this.getPlanoContabilIdentifier(planoContabil)}`, planoContabil, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPlanoContabil>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPlanoContabil[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPlanoContabilIdentifier(planoContabil: Pick<IPlanoContabil, 'id'>): number {
    return planoContabil.id;
  }

  comparePlanoContabil(o1: Pick<IPlanoContabil, 'id'> | null, o2: Pick<IPlanoContabil, 'id'> | null): boolean {
    return o1 && o2 ? this.getPlanoContabilIdentifier(o1) === this.getPlanoContabilIdentifier(o2) : o1 === o2;
  }

  addPlanoContabilToCollectionIfMissing<Type extends Pick<IPlanoContabil, 'id'>>(
    planoContabilCollection: Type[],
    ...planoContabilsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const planoContabils: Type[] = planoContabilsToCheck.filter(isPresent);
    if (planoContabils.length > 0) {
      const planoContabilCollectionIdentifiers = planoContabilCollection.map(planoContabilItem =>
        this.getPlanoContabilIdentifier(planoContabilItem),
      );
      const planoContabilsToAdd = planoContabils.filter(planoContabilItem => {
        const planoContabilIdentifier = this.getPlanoContabilIdentifier(planoContabilItem);
        if (planoContabilCollectionIdentifiers.includes(planoContabilIdentifier)) {
          return false;
        }
        planoContabilCollectionIdentifiers.push(planoContabilIdentifier);
        return true;
      });
      return [...planoContabilsToAdd, ...planoContabilCollection];
    }
    return planoContabilCollection;
  }
}
