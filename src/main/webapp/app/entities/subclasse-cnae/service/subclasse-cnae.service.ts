import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISubclasseCnae, NewSubclasseCnae } from '../subclasse-cnae.model';

export type PartialUpdateSubclasseCnae = Partial<ISubclasseCnae> & Pick<ISubclasseCnae, 'id'>;

export type EntityResponseType = HttpResponse<ISubclasseCnae>;
export type EntityArrayResponseType = HttpResponse<ISubclasseCnae[]>;

@Injectable({ providedIn: 'root' })
export class SubclasseCnaeService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/subclasse-cnaes');

  create(subclasseCnae: NewSubclasseCnae): Observable<EntityResponseType> {
    return this.http.post<ISubclasseCnae>(this.resourceUrl, subclasseCnae, { observe: 'response' });
  }

  update(subclasseCnae: ISubclasseCnae): Observable<EntityResponseType> {
    return this.http.put<ISubclasseCnae>(`${this.resourceUrl}/${this.getSubclasseCnaeIdentifier(subclasseCnae)}`, subclasseCnae, {
      observe: 'response',
    });
  }

  partialUpdate(subclasseCnae: PartialUpdateSubclasseCnae): Observable<EntityResponseType> {
    return this.http.patch<ISubclasseCnae>(`${this.resourceUrl}/${this.getSubclasseCnaeIdentifier(subclasseCnae)}`, subclasseCnae, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISubclasseCnae>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISubclasseCnae[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getSubclasseCnaeIdentifier(subclasseCnae: Pick<ISubclasseCnae, 'id'>): number {
    return subclasseCnae.id;
  }

  compareSubclasseCnae(o1: Pick<ISubclasseCnae, 'id'> | null, o2: Pick<ISubclasseCnae, 'id'> | null): boolean {
    return o1 && o2 ? this.getSubclasseCnaeIdentifier(o1) === this.getSubclasseCnaeIdentifier(o2) : o1 === o2;
  }

  addSubclasseCnaeToCollectionIfMissing<Type extends Pick<ISubclasseCnae, 'id'>>(
    subclasseCnaeCollection: Type[],
    ...subclasseCnaesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const subclasseCnaes: Type[] = subclasseCnaesToCheck.filter(isPresent);
    if (subclasseCnaes.length > 0) {
      const subclasseCnaeCollectionIdentifiers = subclasseCnaeCollection.map(subclasseCnaeItem =>
        this.getSubclasseCnaeIdentifier(subclasseCnaeItem),
      );
      const subclasseCnaesToAdd = subclasseCnaes.filter(subclasseCnaeItem => {
        const subclasseCnaeIdentifier = this.getSubclasseCnaeIdentifier(subclasseCnaeItem);
        if (subclasseCnaeCollectionIdentifiers.includes(subclasseCnaeIdentifier)) {
          return false;
        }
        subclasseCnaeCollectionIdentifiers.push(subclasseCnaeIdentifier);
        return true;
      });
      return [...subclasseCnaesToAdd, ...subclasseCnaeCollection];
    }
    return subclasseCnaeCollection;
  }
}
