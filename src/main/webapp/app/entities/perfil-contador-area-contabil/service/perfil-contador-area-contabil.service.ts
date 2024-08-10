import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPerfilContadorAreaContabil, NewPerfilContadorAreaContabil } from '../perfil-contador-area-contabil.model';

export type PartialUpdatePerfilContadorAreaContabil = Partial<IPerfilContadorAreaContabil> & Pick<IPerfilContadorAreaContabil, 'id'>;

export type EntityResponseType = HttpResponse<IPerfilContadorAreaContabil>;
export type EntityArrayResponseType = HttpResponse<IPerfilContadorAreaContabil[]>;

@Injectable({ providedIn: 'root' })
export class PerfilContadorAreaContabilService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/perfil-contador-area-contabils');

  create(perfilContadorAreaContabil: NewPerfilContadorAreaContabil): Observable<EntityResponseType> {
    return this.http.post<IPerfilContadorAreaContabil>(this.resourceUrl, perfilContadorAreaContabil, { observe: 'response' });
  }

  update(perfilContadorAreaContabil: IPerfilContadorAreaContabil): Observable<EntityResponseType> {
    return this.http.put<IPerfilContadorAreaContabil>(
      `${this.resourceUrl}/${this.getPerfilContadorAreaContabilIdentifier(perfilContadorAreaContabil)}`,
      perfilContadorAreaContabil,
      { observe: 'response' },
    );
  }

  partialUpdate(perfilContadorAreaContabil: PartialUpdatePerfilContadorAreaContabil): Observable<EntityResponseType> {
    return this.http.patch<IPerfilContadorAreaContabil>(
      `${this.resourceUrl}/${this.getPerfilContadorAreaContabilIdentifier(perfilContadorAreaContabil)}`,
      perfilContadorAreaContabil,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPerfilContadorAreaContabil>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPerfilContadorAreaContabil[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPerfilContadorAreaContabilIdentifier(perfilContadorAreaContabil: Pick<IPerfilContadorAreaContabil, 'id'>): number {
    return perfilContadorAreaContabil.id;
  }

  comparePerfilContadorAreaContabil(
    o1: Pick<IPerfilContadorAreaContabil, 'id'> | null,
    o2: Pick<IPerfilContadorAreaContabil, 'id'> | null,
  ): boolean {
    return o1 && o2 ? this.getPerfilContadorAreaContabilIdentifier(o1) === this.getPerfilContadorAreaContabilIdentifier(o2) : o1 === o2;
  }

  addPerfilContadorAreaContabilToCollectionIfMissing<Type extends Pick<IPerfilContadorAreaContabil, 'id'>>(
    perfilContadorAreaContabilCollection: Type[],
    ...perfilContadorAreaContabilsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const perfilContadorAreaContabils: Type[] = perfilContadorAreaContabilsToCheck.filter(isPresent);
    if (perfilContadorAreaContabils.length > 0) {
      const perfilContadorAreaContabilCollectionIdentifiers = perfilContadorAreaContabilCollection.map(perfilContadorAreaContabilItem =>
        this.getPerfilContadorAreaContabilIdentifier(perfilContadorAreaContabilItem),
      );
      const perfilContadorAreaContabilsToAdd = perfilContadorAreaContabils.filter(perfilContadorAreaContabilItem => {
        const perfilContadorAreaContabilIdentifier = this.getPerfilContadorAreaContabilIdentifier(perfilContadorAreaContabilItem);
        if (perfilContadorAreaContabilCollectionIdentifiers.includes(perfilContadorAreaContabilIdentifier)) {
          return false;
        }
        perfilContadorAreaContabilCollectionIdentifiers.push(perfilContadorAreaContabilIdentifier);
        return true;
      });
      return [...perfilContadorAreaContabilsToAdd, ...perfilContadorAreaContabilCollection];
    }
    return perfilContadorAreaContabilCollection;
  }
}
