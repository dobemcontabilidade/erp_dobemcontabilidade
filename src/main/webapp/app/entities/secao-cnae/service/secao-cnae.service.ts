import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISecaoCnae, NewSecaoCnae } from '../secao-cnae.model';

export type PartialUpdateSecaoCnae = Partial<ISecaoCnae> & Pick<ISecaoCnae, 'id'>;

export type EntityResponseType = HttpResponse<ISecaoCnae>;
export type EntityArrayResponseType = HttpResponse<ISecaoCnae[]>;

@Injectable({ providedIn: 'root' })
export class SecaoCnaeService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/secao-cnaes');

  create(secaoCnae: NewSecaoCnae): Observable<EntityResponseType> {
    return this.http.post<ISecaoCnae>(this.resourceUrl, secaoCnae, { observe: 'response' });
  }

  update(secaoCnae: ISecaoCnae): Observable<EntityResponseType> {
    return this.http.put<ISecaoCnae>(`${this.resourceUrl}/${this.getSecaoCnaeIdentifier(secaoCnae)}`, secaoCnae, { observe: 'response' });
  }

  partialUpdate(secaoCnae: PartialUpdateSecaoCnae): Observable<EntityResponseType> {
    return this.http.patch<ISecaoCnae>(`${this.resourceUrl}/${this.getSecaoCnaeIdentifier(secaoCnae)}`, secaoCnae, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISecaoCnae>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISecaoCnae[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getSecaoCnaeIdentifier(secaoCnae: Pick<ISecaoCnae, 'id'>): number {
    return secaoCnae.id;
  }

  compareSecaoCnae(o1: Pick<ISecaoCnae, 'id'> | null, o2: Pick<ISecaoCnae, 'id'> | null): boolean {
    return o1 && o2 ? this.getSecaoCnaeIdentifier(o1) === this.getSecaoCnaeIdentifier(o2) : o1 === o2;
  }

  addSecaoCnaeToCollectionIfMissing<Type extends Pick<ISecaoCnae, 'id'>>(
    secaoCnaeCollection: Type[],
    ...secaoCnaesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const secaoCnaes: Type[] = secaoCnaesToCheck.filter(isPresent);
    if (secaoCnaes.length > 0) {
      const secaoCnaeCollectionIdentifiers = secaoCnaeCollection.map(secaoCnaeItem => this.getSecaoCnaeIdentifier(secaoCnaeItem));
      const secaoCnaesToAdd = secaoCnaes.filter(secaoCnaeItem => {
        const secaoCnaeIdentifier = this.getSecaoCnaeIdentifier(secaoCnaeItem);
        if (secaoCnaeCollectionIdentifiers.includes(secaoCnaeIdentifier)) {
          return false;
        }
        secaoCnaeCollectionIdentifiers.push(secaoCnaeIdentifier);
        return true;
      });
      return [...secaoCnaesToAdd, ...secaoCnaeCollection];
    }
    return secaoCnaeCollection;
  }
}
