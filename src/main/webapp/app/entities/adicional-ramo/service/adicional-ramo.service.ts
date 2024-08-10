import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAdicionalRamo, NewAdicionalRamo } from '../adicional-ramo.model';

export type PartialUpdateAdicionalRamo = Partial<IAdicionalRamo> & Pick<IAdicionalRamo, 'id'>;

export type EntityResponseType = HttpResponse<IAdicionalRamo>;
export type EntityArrayResponseType = HttpResponse<IAdicionalRamo[]>;

@Injectable({ providedIn: 'root' })
export class AdicionalRamoService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/adicional-ramos');

  create(adicionalRamo: NewAdicionalRamo): Observable<EntityResponseType> {
    return this.http.post<IAdicionalRamo>(this.resourceUrl, adicionalRamo, { observe: 'response' });
  }

  update(adicionalRamo: IAdicionalRamo): Observable<EntityResponseType> {
    return this.http.put<IAdicionalRamo>(`${this.resourceUrl}/${this.getAdicionalRamoIdentifier(adicionalRamo)}`, adicionalRamo, {
      observe: 'response',
    });
  }

  partialUpdate(adicionalRamo: PartialUpdateAdicionalRamo): Observable<EntityResponseType> {
    return this.http.patch<IAdicionalRamo>(`${this.resourceUrl}/${this.getAdicionalRamoIdentifier(adicionalRamo)}`, adicionalRamo, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAdicionalRamo>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAdicionalRamo[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAdicionalRamoIdentifier(adicionalRamo: Pick<IAdicionalRamo, 'id'>): number {
    return adicionalRamo.id;
  }

  compareAdicionalRamo(o1: Pick<IAdicionalRamo, 'id'> | null, o2: Pick<IAdicionalRamo, 'id'> | null): boolean {
    return o1 && o2 ? this.getAdicionalRamoIdentifier(o1) === this.getAdicionalRamoIdentifier(o2) : o1 === o2;
  }

  addAdicionalRamoToCollectionIfMissing<Type extends Pick<IAdicionalRamo, 'id'>>(
    adicionalRamoCollection: Type[],
    ...adicionalRamosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const adicionalRamos: Type[] = adicionalRamosToCheck.filter(isPresent);
    if (adicionalRamos.length > 0) {
      const adicionalRamoCollectionIdentifiers = adicionalRamoCollection.map(adicionalRamoItem =>
        this.getAdicionalRamoIdentifier(adicionalRamoItem),
      );
      const adicionalRamosToAdd = adicionalRamos.filter(adicionalRamoItem => {
        const adicionalRamoIdentifier = this.getAdicionalRamoIdentifier(adicionalRamoItem);
        if (adicionalRamoCollectionIdentifiers.includes(adicionalRamoIdentifier)) {
          return false;
        }
        adicionalRamoCollectionIdentifiers.push(adicionalRamoIdentifier);
        return true;
      });
      return [...adicionalRamosToAdd, ...adicionalRamoCollection];
    }
    return adicionalRamoCollection;
  }
}
