import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPlanoContaAzul, NewPlanoContaAzul } from '../plano-conta-azul.model';

export type PartialUpdatePlanoContaAzul = Partial<IPlanoContaAzul> & Pick<IPlanoContaAzul, 'id'>;

export type EntityResponseType = HttpResponse<IPlanoContaAzul>;
export type EntityArrayResponseType = HttpResponse<IPlanoContaAzul[]>;

@Injectable({ providedIn: 'root' })
export class PlanoContaAzulService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/plano-conta-azuls');

  create(planoContaAzul: NewPlanoContaAzul): Observable<EntityResponseType> {
    return this.http.post<IPlanoContaAzul>(this.resourceUrl, planoContaAzul, { observe: 'response' });
  }

  update(planoContaAzul: IPlanoContaAzul): Observable<EntityResponseType> {
    return this.http.put<IPlanoContaAzul>(`${this.resourceUrl}/${this.getPlanoContaAzulIdentifier(planoContaAzul)}`, planoContaAzul, {
      observe: 'response',
    });
  }

  partialUpdate(planoContaAzul: PartialUpdatePlanoContaAzul): Observable<EntityResponseType> {
    return this.http.patch<IPlanoContaAzul>(`${this.resourceUrl}/${this.getPlanoContaAzulIdentifier(planoContaAzul)}`, planoContaAzul, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPlanoContaAzul>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPlanoContaAzul[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPlanoContaAzulIdentifier(planoContaAzul: Pick<IPlanoContaAzul, 'id'>): number {
    return planoContaAzul.id;
  }

  comparePlanoContaAzul(o1: Pick<IPlanoContaAzul, 'id'> | null, o2: Pick<IPlanoContaAzul, 'id'> | null): boolean {
    return o1 && o2 ? this.getPlanoContaAzulIdentifier(o1) === this.getPlanoContaAzulIdentifier(o2) : o1 === o2;
  }

  addPlanoContaAzulToCollectionIfMissing<Type extends Pick<IPlanoContaAzul, 'id'>>(
    planoContaAzulCollection: Type[],
    ...planoContaAzulsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const planoContaAzuls: Type[] = planoContaAzulsToCheck.filter(isPresent);
    if (planoContaAzuls.length > 0) {
      const planoContaAzulCollectionIdentifiers = planoContaAzulCollection.map(planoContaAzulItem =>
        this.getPlanoContaAzulIdentifier(planoContaAzulItem),
      );
      const planoContaAzulsToAdd = planoContaAzuls.filter(planoContaAzulItem => {
        const planoContaAzulIdentifier = this.getPlanoContaAzulIdentifier(planoContaAzulItem);
        if (planoContaAzulCollectionIdentifiers.includes(planoContaAzulIdentifier)) {
          return false;
        }
        planoContaAzulCollectionIdentifiers.push(planoContaAzulIdentifier);
        return true;
      });
      return [...planoContaAzulsToAdd, ...planoContaAzulCollection];
    }
    return planoContaAzulCollection;
  }
}
