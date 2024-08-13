import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEstrangeiro, NewEstrangeiro } from '../estrangeiro.model';

export type PartialUpdateEstrangeiro = Partial<IEstrangeiro> & Pick<IEstrangeiro, 'id'>;

export type EntityResponseType = HttpResponse<IEstrangeiro>;
export type EntityArrayResponseType = HttpResponse<IEstrangeiro[]>;

@Injectable({ providedIn: 'root' })
export class EstrangeiroService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/estrangeiros');

  create(estrangeiro: NewEstrangeiro): Observable<EntityResponseType> {
    return this.http.post<IEstrangeiro>(this.resourceUrl, estrangeiro, { observe: 'response' });
  }

  update(estrangeiro: IEstrangeiro): Observable<EntityResponseType> {
    return this.http.put<IEstrangeiro>(`${this.resourceUrl}/${this.getEstrangeiroIdentifier(estrangeiro)}`, estrangeiro, {
      observe: 'response',
    });
  }

  partialUpdate(estrangeiro: PartialUpdateEstrangeiro): Observable<EntityResponseType> {
    return this.http.patch<IEstrangeiro>(`${this.resourceUrl}/${this.getEstrangeiroIdentifier(estrangeiro)}`, estrangeiro, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEstrangeiro>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEstrangeiro[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getEstrangeiroIdentifier(estrangeiro: Pick<IEstrangeiro, 'id'>): number {
    return estrangeiro.id;
  }

  compareEstrangeiro(o1: Pick<IEstrangeiro, 'id'> | null, o2: Pick<IEstrangeiro, 'id'> | null): boolean {
    return o1 && o2 ? this.getEstrangeiroIdentifier(o1) === this.getEstrangeiroIdentifier(o2) : o1 === o2;
  }

  addEstrangeiroToCollectionIfMissing<Type extends Pick<IEstrangeiro, 'id'>>(
    estrangeiroCollection: Type[],
    ...estrangeirosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const estrangeiros: Type[] = estrangeirosToCheck.filter(isPresent);
    if (estrangeiros.length > 0) {
      const estrangeiroCollectionIdentifiers = estrangeiroCollection.map(estrangeiroItem => this.getEstrangeiroIdentifier(estrangeiroItem));
      const estrangeirosToAdd = estrangeiros.filter(estrangeiroItem => {
        const estrangeiroIdentifier = this.getEstrangeiroIdentifier(estrangeiroItem);
        if (estrangeiroCollectionIdentifiers.includes(estrangeiroIdentifier)) {
          return false;
        }
        estrangeiroCollectionIdentifiers.push(estrangeiroIdentifier);
        return true;
      });
      return [...estrangeirosToAdd, ...estrangeiroCollection];
    }
    return estrangeiroCollection;
  }
}
