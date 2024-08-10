import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITermoDeAdesao, NewTermoDeAdesao } from '../termo-de-adesao.model';

export type PartialUpdateTermoDeAdesao = Partial<ITermoDeAdesao> & Pick<ITermoDeAdesao, 'id'>;

export type EntityResponseType = HttpResponse<ITermoDeAdesao>;
export type EntityArrayResponseType = HttpResponse<ITermoDeAdesao[]>;

@Injectable({ providedIn: 'root' })
export class TermoDeAdesaoService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/termo-de-adesaos');

  create(termoDeAdesao: NewTermoDeAdesao): Observable<EntityResponseType> {
    return this.http.post<ITermoDeAdesao>(this.resourceUrl, termoDeAdesao, { observe: 'response' });
  }

  update(termoDeAdesao: ITermoDeAdesao): Observable<EntityResponseType> {
    return this.http.put<ITermoDeAdesao>(`${this.resourceUrl}/${this.getTermoDeAdesaoIdentifier(termoDeAdesao)}`, termoDeAdesao, {
      observe: 'response',
    });
  }

  partialUpdate(termoDeAdesao: PartialUpdateTermoDeAdesao): Observable<EntityResponseType> {
    return this.http.patch<ITermoDeAdesao>(`${this.resourceUrl}/${this.getTermoDeAdesaoIdentifier(termoDeAdesao)}`, termoDeAdesao, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITermoDeAdesao>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITermoDeAdesao[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getTermoDeAdesaoIdentifier(termoDeAdesao: Pick<ITermoDeAdesao, 'id'>): number {
    return termoDeAdesao.id;
  }

  compareTermoDeAdesao(o1: Pick<ITermoDeAdesao, 'id'> | null, o2: Pick<ITermoDeAdesao, 'id'> | null): boolean {
    return o1 && o2 ? this.getTermoDeAdesaoIdentifier(o1) === this.getTermoDeAdesaoIdentifier(o2) : o1 === o2;
  }

  addTermoDeAdesaoToCollectionIfMissing<Type extends Pick<ITermoDeAdesao, 'id'>>(
    termoDeAdesaoCollection: Type[],
    ...termoDeAdesaosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const termoDeAdesaos: Type[] = termoDeAdesaosToCheck.filter(isPresent);
    if (termoDeAdesaos.length > 0) {
      const termoDeAdesaoCollectionIdentifiers = termoDeAdesaoCollection.map(termoDeAdesaoItem =>
        this.getTermoDeAdesaoIdentifier(termoDeAdesaoItem),
      );
      const termoDeAdesaosToAdd = termoDeAdesaos.filter(termoDeAdesaoItem => {
        const termoDeAdesaoIdentifier = this.getTermoDeAdesaoIdentifier(termoDeAdesaoItem);
        if (termoDeAdesaoCollectionIdentifiers.includes(termoDeAdesaoIdentifier)) {
          return false;
        }
        termoDeAdesaoCollectionIdentifiers.push(termoDeAdesaoIdentifier);
        return true;
      });
      return [...termoDeAdesaosToAdd, ...termoDeAdesaoCollection];
    }
    return termoDeAdesaoCollection;
  }
}
