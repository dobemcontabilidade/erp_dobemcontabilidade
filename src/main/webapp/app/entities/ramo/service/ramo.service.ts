import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IRamo, NewRamo } from '../ramo.model';

export type PartialUpdateRamo = Partial<IRamo> & Pick<IRamo, 'id'>;

export type EntityResponseType = HttpResponse<IRamo>;
export type EntityArrayResponseType = HttpResponse<IRamo[]>;

@Injectable({ providedIn: 'root' })
export class RamoService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/ramos');

  create(ramo: NewRamo): Observable<EntityResponseType> {
    return this.http.post<IRamo>(this.resourceUrl, ramo, { observe: 'response' });
  }

  update(ramo: IRamo): Observable<EntityResponseType> {
    return this.http.put<IRamo>(`${this.resourceUrl}/${this.getRamoIdentifier(ramo)}`, ramo, { observe: 'response' });
  }

  partialUpdate(ramo: PartialUpdateRamo): Observable<EntityResponseType> {
    return this.http.patch<IRamo>(`${this.resourceUrl}/${this.getRamoIdentifier(ramo)}`, ramo, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRamo>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRamo[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getRamoIdentifier(ramo: Pick<IRamo, 'id'>): number {
    return ramo.id;
  }

  compareRamo(o1: Pick<IRamo, 'id'> | null, o2: Pick<IRamo, 'id'> | null): boolean {
    return o1 && o2 ? this.getRamoIdentifier(o1) === this.getRamoIdentifier(o2) : o1 === o2;
  }

  addRamoToCollectionIfMissing<Type extends Pick<IRamo, 'id'>>(
    ramoCollection: Type[],
    ...ramosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const ramos: Type[] = ramosToCheck.filter(isPresent);
    if (ramos.length > 0) {
      const ramoCollectionIdentifiers = ramoCollection.map(ramoItem => this.getRamoIdentifier(ramoItem));
      const ramosToAdd = ramos.filter(ramoItem => {
        const ramoIdentifier = this.getRamoIdentifier(ramoItem);
        if (ramoCollectionIdentifiers.includes(ramoIdentifier)) {
          return false;
        }
        ramoCollectionIdentifiers.push(ramoIdentifier);
        return true;
      });
      return [...ramosToAdd, ...ramoCollection];
    }
    return ramoCollection;
  }
}
