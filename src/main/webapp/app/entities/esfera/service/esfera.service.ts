import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEsfera, NewEsfera } from '../esfera.model';

export type PartialUpdateEsfera = Partial<IEsfera> & Pick<IEsfera, 'id'>;

export type EntityResponseType = HttpResponse<IEsfera>;
export type EntityArrayResponseType = HttpResponse<IEsfera[]>;

@Injectable({ providedIn: 'root' })
export class EsferaService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/esferas');

  create(esfera: NewEsfera): Observable<EntityResponseType> {
    return this.http.post<IEsfera>(this.resourceUrl, esfera, { observe: 'response' });
  }

  update(esfera: IEsfera): Observable<EntityResponseType> {
    return this.http.put<IEsfera>(`${this.resourceUrl}/${this.getEsferaIdentifier(esfera)}`, esfera, { observe: 'response' });
  }

  partialUpdate(esfera: PartialUpdateEsfera): Observable<EntityResponseType> {
    return this.http.patch<IEsfera>(`${this.resourceUrl}/${this.getEsferaIdentifier(esfera)}`, esfera, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEsfera>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEsfera[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getEsferaIdentifier(esfera: Pick<IEsfera, 'id'>): number {
    return esfera.id;
  }

  compareEsfera(o1: Pick<IEsfera, 'id'> | null, o2: Pick<IEsfera, 'id'> | null): boolean {
    return o1 && o2 ? this.getEsferaIdentifier(o1) === this.getEsferaIdentifier(o2) : o1 === o2;
  }

  addEsferaToCollectionIfMissing<Type extends Pick<IEsfera, 'id'>>(
    esferaCollection: Type[],
    ...esferasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const esferas: Type[] = esferasToCheck.filter(isPresent);
    if (esferas.length > 0) {
      const esferaCollectionIdentifiers = esferaCollection.map(esferaItem => this.getEsferaIdentifier(esferaItem));
      const esferasToAdd = esferas.filter(esferaItem => {
        const esferaIdentifier = this.getEsferaIdentifier(esferaItem);
        if (esferaCollectionIdentifiers.includes(esferaIdentifier)) {
          return false;
        }
        esferaCollectionIdentifiers.push(esferaIdentifier);
        return true;
      });
      return [...esferasToAdd, ...esferaCollection];
    }
    return esferaCollection;
  }
}
