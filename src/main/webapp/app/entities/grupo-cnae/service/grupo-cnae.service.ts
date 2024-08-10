import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IGrupoCnae, NewGrupoCnae } from '../grupo-cnae.model';

export type PartialUpdateGrupoCnae = Partial<IGrupoCnae> & Pick<IGrupoCnae, 'id'>;

export type EntityResponseType = HttpResponse<IGrupoCnae>;
export type EntityArrayResponseType = HttpResponse<IGrupoCnae[]>;

@Injectable({ providedIn: 'root' })
export class GrupoCnaeService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/grupo-cnaes');

  create(grupoCnae: NewGrupoCnae): Observable<EntityResponseType> {
    return this.http.post<IGrupoCnae>(this.resourceUrl, grupoCnae, { observe: 'response' });
  }

  update(grupoCnae: IGrupoCnae): Observable<EntityResponseType> {
    return this.http.put<IGrupoCnae>(`${this.resourceUrl}/${this.getGrupoCnaeIdentifier(grupoCnae)}`, grupoCnae, { observe: 'response' });
  }

  partialUpdate(grupoCnae: PartialUpdateGrupoCnae): Observable<EntityResponseType> {
    return this.http.patch<IGrupoCnae>(`${this.resourceUrl}/${this.getGrupoCnaeIdentifier(grupoCnae)}`, grupoCnae, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IGrupoCnae>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IGrupoCnae[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getGrupoCnaeIdentifier(grupoCnae: Pick<IGrupoCnae, 'id'>): number {
    return grupoCnae.id;
  }

  compareGrupoCnae(o1: Pick<IGrupoCnae, 'id'> | null, o2: Pick<IGrupoCnae, 'id'> | null): boolean {
    return o1 && o2 ? this.getGrupoCnaeIdentifier(o1) === this.getGrupoCnaeIdentifier(o2) : o1 === o2;
  }

  addGrupoCnaeToCollectionIfMissing<Type extends Pick<IGrupoCnae, 'id'>>(
    grupoCnaeCollection: Type[],
    ...grupoCnaesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const grupoCnaes: Type[] = grupoCnaesToCheck.filter(isPresent);
    if (grupoCnaes.length > 0) {
      const grupoCnaeCollectionIdentifiers = grupoCnaeCollection.map(grupoCnaeItem => this.getGrupoCnaeIdentifier(grupoCnaeItem));
      const grupoCnaesToAdd = grupoCnaes.filter(grupoCnaeItem => {
        const grupoCnaeIdentifier = this.getGrupoCnaeIdentifier(grupoCnaeItem);
        if (grupoCnaeCollectionIdentifiers.includes(grupoCnaeIdentifier)) {
          return false;
        }
        grupoCnaeCollectionIdentifiers.push(grupoCnaeIdentifier);
        return true;
      });
      return [...grupoCnaesToAdd, ...grupoCnaeCollection];
    }
    return grupoCnaeCollection;
  }
}
