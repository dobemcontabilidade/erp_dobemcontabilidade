import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICnae, NewCnae } from '../cnae.model';

export type PartialUpdateCnae = Partial<ICnae> & Pick<ICnae, 'id'>;

export type EntityResponseType = HttpResponse<ICnae>;
export type EntityArrayResponseType = HttpResponse<ICnae[]>;

@Injectable({ providedIn: 'root' })
export class CnaeService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/cnaes');

  create(cnae: NewCnae): Observable<EntityResponseType> {
    return this.http.post<ICnae>(this.resourceUrl, cnae, { observe: 'response' });
  }

  update(cnae: ICnae): Observable<EntityResponseType> {
    return this.http.put<ICnae>(`${this.resourceUrl}/${this.getCnaeIdentifier(cnae)}`, cnae, { observe: 'response' });
  }

  partialUpdate(cnae: PartialUpdateCnae): Observable<EntityResponseType> {
    return this.http.patch<ICnae>(`${this.resourceUrl}/${this.getCnaeIdentifier(cnae)}`, cnae, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICnae>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICnae[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCnaeIdentifier(cnae: Pick<ICnae, 'id'>): number {
    return cnae.id;
  }

  compareCnae(o1: Pick<ICnae, 'id'> | null, o2: Pick<ICnae, 'id'> | null): boolean {
    return o1 && o2 ? this.getCnaeIdentifier(o1) === this.getCnaeIdentifier(o2) : o1 === o2;
  }

  addCnaeToCollectionIfMissing<Type extends Pick<ICnae, 'id'>>(
    cnaeCollection: Type[],
    ...cnaesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const cnaes: Type[] = cnaesToCheck.filter(isPresent);
    if (cnaes.length > 0) {
      const cnaeCollectionIdentifiers = cnaeCollection.map(cnaeItem => this.getCnaeIdentifier(cnaeItem));
      const cnaesToAdd = cnaes.filter(cnaeItem => {
        const cnaeIdentifier = this.getCnaeIdentifier(cnaeItem);
        if (cnaeCollectionIdentifiers.includes(cnaeIdentifier)) {
          return false;
        }
        cnaeCollectionIdentifiers.push(cnaeIdentifier);
        return true;
      });
      return [...cnaesToAdd, ...cnaeCollection];
    }
    return cnaeCollection;
  }
}
