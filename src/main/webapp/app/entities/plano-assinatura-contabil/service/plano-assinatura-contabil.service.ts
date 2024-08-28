import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPlanoAssinaturaContabil, NewPlanoAssinaturaContabil } from '../plano-assinatura-contabil.model';

export type PartialUpdatePlanoAssinaturaContabil = Partial<IPlanoAssinaturaContabil> & Pick<IPlanoAssinaturaContabil, 'id'>;

export type EntityResponseType = HttpResponse<IPlanoAssinaturaContabil>;
export type EntityArrayResponseType = HttpResponse<IPlanoAssinaturaContabil[]>;

@Injectable({ providedIn: 'root' })
export class PlanoAssinaturaContabilService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/plano-assinatura-contabils');

  create(planoAssinaturaContabil: NewPlanoAssinaturaContabil): Observable<EntityResponseType> {
    return this.http.post<IPlanoAssinaturaContabil>(this.resourceUrl, planoAssinaturaContabil, { observe: 'response' });
  }

  update(planoAssinaturaContabil: IPlanoAssinaturaContabil): Observable<EntityResponseType> {
    return this.http.put<IPlanoAssinaturaContabil>(
      `${this.resourceUrl}/${this.getPlanoAssinaturaContabilIdentifier(planoAssinaturaContabil)}`,
      planoAssinaturaContabil,
      { observe: 'response' },
    );
  }

  partialUpdate(planoAssinaturaContabil: PartialUpdatePlanoAssinaturaContabil): Observable<EntityResponseType> {
    return this.http.patch<IPlanoAssinaturaContabil>(
      `${this.resourceUrl}/${this.getPlanoAssinaturaContabilIdentifier(planoAssinaturaContabil)}`,
      planoAssinaturaContabil,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPlanoAssinaturaContabil>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPlanoAssinaturaContabil[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPlanoAssinaturaContabilIdentifier(planoAssinaturaContabil: Pick<IPlanoAssinaturaContabil, 'id'>): number {
    return planoAssinaturaContabil.id;
  }

  comparePlanoAssinaturaContabil(
    o1: Pick<IPlanoAssinaturaContabil, 'id'> | null,
    o2: Pick<IPlanoAssinaturaContabil, 'id'> | null,
  ): boolean {
    return o1 && o2 ? this.getPlanoAssinaturaContabilIdentifier(o1) === this.getPlanoAssinaturaContabilIdentifier(o2) : o1 === o2;
  }

  addPlanoAssinaturaContabilToCollectionIfMissing<Type extends Pick<IPlanoAssinaturaContabil, 'id'>>(
    planoAssinaturaContabilCollection: Type[],
    ...planoAssinaturaContabilsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const planoAssinaturaContabils: Type[] = planoAssinaturaContabilsToCheck.filter(isPresent);
    if (planoAssinaturaContabils.length > 0) {
      const planoAssinaturaContabilCollectionIdentifiers = planoAssinaturaContabilCollection.map(planoAssinaturaContabilItem =>
        this.getPlanoAssinaturaContabilIdentifier(planoAssinaturaContabilItem),
      );
      const planoAssinaturaContabilsToAdd = planoAssinaturaContabils.filter(planoAssinaturaContabilItem => {
        const planoAssinaturaContabilIdentifier = this.getPlanoAssinaturaContabilIdentifier(planoAssinaturaContabilItem);
        if (planoAssinaturaContabilCollectionIdentifiers.includes(planoAssinaturaContabilIdentifier)) {
          return false;
        }
        planoAssinaturaContabilCollectionIdentifiers.push(planoAssinaturaContabilIdentifier);
        return true;
      });
      return [...planoAssinaturaContabilsToAdd, ...planoAssinaturaContabilCollection];
    }
    return planoAssinaturaContabilCollection;
  }
}
