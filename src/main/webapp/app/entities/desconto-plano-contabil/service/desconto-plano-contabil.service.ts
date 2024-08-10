import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDescontoPlanoContabil, NewDescontoPlanoContabil } from '../desconto-plano-contabil.model';

export type PartialUpdateDescontoPlanoContabil = Partial<IDescontoPlanoContabil> & Pick<IDescontoPlanoContabil, 'id'>;

export type EntityResponseType = HttpResponse<IDescontoPlanoContabil>;
export type EntityArrayResponseType = HttpResponse<IDescontoPlanoContabil[]>;

@Injectable({ providedIn: 'root' })
export class DescontoPlanoContabilService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/desconto-plano-contabils');

  create(descontoPlanoContabil: NewDescontoPlanoContabil): Observable<EntityResponseType> {
    return this.http.post<IDescontoPlanoContabil>(this.resourceUrl, descontoPlanoContabil, { observe: 'response' });
  }

  update(descontoPlanoContabil: IDescontoPlanoContabil): Observable<EntityResponseType> {
    return this.http.put<IDescontoPlanoContabil>(
      `${this.resourceUrl}/${this.getDescontoPlanoContabilIdentifier(descontoPlanoContabil)}`,
      descontoPlanoContabil,
      { observe: 'response' },
    );
  }

  partialUpdate(descontoPlanoContabil: PartialUpdateDescontoPlanoContabil): Observable<EntityResponseType> {
    return this.http.patch<IDescontoPlanoContabil>(
      `${this.resourceUrl}/${this.getDescontoPlanoContabilIdentifier(descontoPlanoContabil)}`,
      descontoPlanoContabil,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDescontoPlanoContabil>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDescontoPlanoContabil[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getDescontoPlanoContabilIdentifier(descontoPlanoContabil: Pick<IDescontoPlanoContabil, 'id'>): number {
    return descontoPlanoContabil.id;
  }

  compareDescontoPlanoContabil(o1: Pick<IDescontoPlanoContabil, 'id'> | null, o2: Pick<IDescontoPlanoContabil, 'id'> | null): boolean {
    return o1 && o2 ? this.getDescontoPlanoContabilIdentifier(o1) === this.getDescontoPlanoContabilIdentifier(o2) : o1 === o2;
  }

  addDescontoPlanoContabilToCollectionIfMissing<Type extends Pick<IDescontoPlanoContabil, 'id'>>(
    descontoPlanoContabilCollection: Type[],
    ...descontoPlanoContabilsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const descontoPlanoContabils: Type[] = descontoPlanoContabilsToCheck.filter(isPresent);
    if (descontoPlanoContabils.length > 0) {
      const descontoPlanoContabilCollectionIdentifiers = descontoPlanoContabilCollection.map(descontoPlanoContabilItem =>
        this.getDescontoPlanoContabilIdentifier(descontoPlanoContabilItem),
      );
      const descontoPlanoContabilsToAdd = descontoPlanoContabils.filter(descontoPlanoContabilItem => {
        const descontoPlanoContabilIdentifier = this.getDescontoPlanoContabilIdentifier(descontoPlanoContabilItem);
        if (descontoPlanoContabilCollectionIdentifiers.includes(descontoPlanoContabilIdentifier)) {
          return false;
        }
        descontoPlanoContabilCollectionIdentifiers.push(descontoPlanoContabilIdentifier);
        return true;
      });
      return [...descontoPlanoContabilsToAdd, ...descontoPlanoContabilCollection];
    }
    return descontoPlanoContabilCollection;
  }
}
