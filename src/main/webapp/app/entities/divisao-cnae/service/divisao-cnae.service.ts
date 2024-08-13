import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDivisaoCnae, NewDivisaoCnae } from '../divisao-cnae.model';

export type PartialUpdateDivisaoCnae = Partial<IDivisaoCnae> & Pick<IDivisaoCnae, 'id'>;

export type EntityResponseType = HttpResponse<IDivisaoCnae>;
export type EntityArrayResponseType = HttpResponse<IDivisaoCnae[]>;

@Injectable({ providedIn: 'root' })
export class DivisaoCnaeService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/divisao-cnaes');

  create(divisaoCnae: NewDivisaoCnae): Observable<EntityResponseType> {
    return this.http.post<IDivisaoCnae>(this.resourceUrl, divisaoCnae, { observe: 'response' });
  }

  update(divisaoCnae: IDivisaoCnae): Observable<EntityResponseType> {
    return this.http.put<IDivisaoCnae>(`${this.resourceUrl}/${this.getDivisaoCnaeIdentifier(divisaoCnae)}`, divisaoCnae, {
      observe: 'response',
    });
  }

  partialUpdate(divisaoCnae: PartialUpdateDivisaoCnae): Observable<EntityResponseType> {
    return this.http.patch<IDivisaoCnae>(`${this.resourceUrl}/${this.getDivisaoCnaeIdentifier(divisaoCnae)}`, divisaoCnae, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDivisaoCnae>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDivisaoCnae[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getDivisaoCnaeIdentifier(divisaoCnae: Pick<IDivisaoCnae, 'id'>): number {
    return divisaoCnae.id;
  }

  compareDivisaoCnae(o1: Pick<IDivisaoCnae, 'id'> | null, o2: Pick<IDivisaoCnae, 'id'> | null): boolean {
    return o1 && o2 ? this.getDivisaoCnaeIdentifier(o1) === this.getDivisaoCnaeIdentifier(o2) : o1 === o2;
  }

  addDivisaoCnaeToCollectionIfMissing<Type extends Pick<IDivisaoCnae, 'id'>>(
    divisaoCnaeCollection: Type[],
    ...divisaoCnaesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const divisaoCnaes: Type[] = divisaoCnaesToCheck.filter(isPresent);
    if (divisaoCnaes.length > 0) {
      const divisaoCnaeCollectionIdentifiers = divisaoCnaeCollection.map(divisaoCnaeItem => this.getDivisaoCnaeIdentifier(divisaoCnaeItem));
      const divisaoCnaesToAdd = divisaoCnaes.filter(divisaoCnaeItem => {
        const divisaoCnaeIdentifier = this.getDivisaoCnaeIdentifier(divisaoCnaeItem);
        if (divisaoCnaeCollectionIdentifiers.includes(divisaoCnaeIdentifier)) {
          return false;
        }
        divisaoCnaeCollectionIdentifiers.push(divisaoCnaeIdentifier);
        return true;
      });
      return [...divisaoCnaesToAdd, ...divisaoCnaeCollection];
    }
    return divisaoCnaeCollection;
  }
}
