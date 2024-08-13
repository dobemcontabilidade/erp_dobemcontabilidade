import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITermoContratoContabil, NewTermoContratoContabil } from '../termo-contrato-contabil.model';

export type PartialUpdateTermoContratoContabil = Partial<ITermoContratoContabil> & Pick<ITermoContratoContabil, 'id'>;

export type EntityResponseType = HttpResponse<ITermoContratoContabil>;
export type EntityArrayResponseType = HttpResponse<ITermoContratoContabil[]>;

@Injectable({ providedIn: 'root' })
export class TermoContratoContabilService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/termo-contrato-contabils');

  create(termoContratoContabil: NewTermoContratoContabil): Observable<EntityResponseType> {
    return this.http.post<ITermoContratoContabil>(this.resourceUrl, termoContratoContabil, { observe: 'response' });
  }

  update(termoContratoContabil: ITermoContratoContabil): Observable<EntityResponseType> {
    return this.http.put<ITermoContratoContabil>(
      `${this.resourceUrl}/${this.getTermoContratoContabilIdentifier(termoContratoContabil)}`,
      termoContratoContabil,
      { observe: 'response' },
    );
  }

  partialUpdate(termoContratoContabil: PartialUpdateTermoContratoContabil): Observable<EntityResponseType> {
    return this.http.patch<ITermoContratoContabil>(
      `${this.resourceUrl}/${this.getTermoContratoContabilIdentifier(termoContratoContabil)}`,
      termoContratoContabil,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITermoContratoContabil>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITermoContratoContabil[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getTermoContratoContabilIdentifier(termoContratoContabil: Pick<ITermoContratoContabil, 'id'>): number {
    return termoContratoContabil.id;
  }

  compareTermoContratoContabil(o1: Pick<ITermoContratoContabil, 'id'> | null, o2: Pick<ITermoContratoContabil, 'id'> | null): boolean {
    return o1 && o2 ? this.getTermoContratoContabilIdentifier(o1) === this.getTermoContratoContabilIdentifier(o2) : o1 === o2;
  }

  addTermoContratoContabilToCollectionIfMissing<Type extends Pick<ITermoContratoContabil, 'id'>>(
    termoContratoContabilCollection: Type[],
    ...termoContratoContabilsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const termoContratoContabils: Type[] = termoContratoContabilsToCheck.filter(isPresent);
    if (termoContratoContabils.length > 0) {
      const termoContratoContabilCollectionIdentifiers = termoContratoContabilCollection.map(termoContratoContabilItem =>
        this.getTermoContratoContabilIdentifier(termoContratoContabilItem),
      );
      const termoContratoContabilsToAdd = termoContratoContabils.filter(termoContratoContabilItem => {
        const termoContratoContabilIdentifier = this.getTermoContratoContabilIdentifier(termoContratoContabilItem);
        if (termoContratoContabilCollectionIdentifiers.includes(termoContratoContabilIdentifier)) {
          return false;
        }
        termoContratoContabilCollectionIdentifiers.push(termoContratoContabilIdentifier);
        return true;
      });
      return [...termoContratoContabilsToAdd, ...termoContratoContabilCollection];
    }
    return termoContratoContabilCollection;
  }
}
