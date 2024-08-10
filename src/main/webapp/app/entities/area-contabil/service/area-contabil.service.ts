import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAreaContabil, NewAreaContabil } from '../area-contabil.model';

export type PartialUpdateAreaContabil = Partial<IAreaContabil> & Pick<IAreaContabil, 'id'>;

export type EntityResponseType = HttpResponse<IAreaContabil>;
export type EntityArrayResponseType = HttpResponse<IAreaContabil[]>;

@Injectable({ providedIn: 'root' })
export class AreaContabilService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/area-contabils');

  create(areaContabil: NewAreaContabil): Observable<EntityResponseType> {
    return this.http.post<IAreaContabil>(this.resourceUrl, areaContabil, { observe: 'response' });
  }

  update(areaContabil: IAreaContabil): Observable<EntityResponseType> {
    return this.http.put<IAreaContabil>(`${this.resourceUrl}/${this.getAreaContabilIdentifier(areaContabil)}`, areaContabil, {
      observe: 'response',
    });
  }

  partialUpdate(areaContabil: PartialUpdateAreaContabil): Observable<EntityResponseType> {
    return this.http.patch<IAreaContabil>(`${this.resourceUrl}/${this.getAreaContabilIdentifier(areaContabil)}`, areaContabil, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAreaContabil>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAreaContabil[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAreaContabilIdentifier(areaContabil: Pick<IAreaContabil, 'id'>): number {
    return areaContabil.id;
  }

  compareAreaContabil(o1: Pick<IAreaContabil, 'id'> | null, o2: Pick<IAreaContabil, 'id'> | null): boolean {
    return o1 && o2 ? this.getAreaContabilIdentifier(o1) === this.getAreaContabilIdentifier(o2) : o1 === o2;
  }

  addAreaContabilToCollectionIfMissing<Type extends Pick<IAreaContabil, 'id'>>(
    areaContabilCollection: Type[],
    ...areaContabilsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const areaContabils: Type[] = areaContabilsToCheck.filter(isPresent);
    if (areaContabils.length > 0) {
      const areaContabilCollectionIdentifiers = areaContabilCollection.map(areaContabilItem =>
        this.getAreaContabilIdentifier(areaContabilItem),
      );
      const areaContabilsToAdd = areaContabils.filter(areaContabilItem => {
        const areaContabilIdentifier = this.getAreaContabilIdentifier(areaContabilItem);
        if (areaContabilCollectionIdentifiers.includes(areaContabilIdentifier)) {
          return false;
        }
        areaContabilCollectionIdentifiers.push(areaContabilIdentifier);
        return true;
      });
      return [...areaContabilsToAdd, ...areaContabilCollection];
    }
    return areaContabilCollection;
  }
}
