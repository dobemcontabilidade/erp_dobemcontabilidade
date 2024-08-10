import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDescontoPlanoContaAzul, NewDescontoPlanoContaAzul } from '../desconto-plano-conta-azul.model';

export type PartialUpdateDescontoPlanoContaAzul = Partial<IDescontoPlanoContaAzul> & Pick<IDescontoPlanoContaAzul, 'id'>;

export type EntityResponseType = HttpResponse<IDescontoPlanoContaAzul>;
export type EntityArrayResponseType = HttpResponse<IDescontoPlanoContaAzul[]>;

@Injectable({ providedIn: 'root' })
export class DescontoPlanoContaAzulService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/desconto-plano-conta-azuls');

  create(descontoPlanoContaAzul: NewDescontoPlanoContaAzul): Observable<EntityResponseType> {
    return this.http.post<IDescontoPlanoContaAzul>(this.resourceUrl, descontoPlanoContaAzul, { observe: 'response' });
  }

  update(descontoPlanoContaAzul: IDescontoPlanoContaAzul): Observable<EntityResponseType> {
    return this.http.put<IDescontoPlanoContaAzul>(
      `${this.resourceUrl}/${this.getDescontoPlanoContaAzulIdentifier(descontoPlanoContaAzul)}`,
      descontoPlanoContaAzul,
      { observe: 'response' },
    );
  }

  partialUpdate(descontoPlanoContaAzul: PartialUpdateDescontoPlanoContaAzul): Observable<EntityResponseType> {
    return this.http.patch<IDescontoPlanoContaAzul>(
      `${this.resourceUrl}/${this.getDescontoPlanoContaAzulIdentifier(descontoPlanoContaAzul)}`,
      descontoPlanoContaAzul,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDescontoPlanoContaAzul>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDescontoPlanoContaAzul[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getDescontoPlanoContaAzulIdentifier(descontoPlanoContaAzul: Pick<IDescontoPlanoContaAzul, 'id'>): number {
    return descontoPlanoContaAzul.id;
  }

  compareDescontoPlanoContaAzul(o1: Pick<IDescontoPlanoContaAzul, 'id'> | null, o2: Pick<IDescontoPlanoContaAzul, 'id'> | null): boolean {
    return o1 && o2 ? this.getDescontoPlanoContaAzulIdentifier(o1) === this.getDescontoPlanoContaAzulIdentifier(o2) : o1 === o2;
  }

  addDescontoPlanoContaAzulToCollectionIfMissing<Type extends Pick<IDescontoPlanoContaAzul, 'id'>>(
    descontoPlanoContaAzulCollection: Type[],
    ...descontoPlanoContaAzulsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const descontoPlanoContaAzuls: Type[] = descontoPlanoContaAzulsToCheck.filter(isPresent);
    if (descontoPlanoContaAzuls.length > 0) {
      const descontoPlanoContaAzulCollectionIdentifiers = descontoPlanoContaAzulCollection.map(descontoPlanoContaAzulItem =>
        this.getDescontoPlanoContaAzulIdentifier(descontoPlanoContaAzulItem),
      );
      const descontoPlanoContaAzulsToAdd = descontoPlanoContaAzuls.filter(descontoPlanoContaAzulItem => {
        const descontoPlanoContaAzulIdentifier = this.getDescontoPlanoContaAzulIdentifier(descontoPlanoContaAzulItem);
        if (descontoPlanoContaAzulCollectionIdentifiers.includes(descontoPlanoContaAzulIdentifier)) {
          return false;
        }
        descontoPlanoContaAzulCollectionIdentifiers.push(descontoPlanoContaAzulIdentifier);
        return true;
      });
      return [...descontoPlanoContaAzulsToAdd, ...descontoPlanoContaAzulCollection];
    }
    return descontoPlanoContaAzulCollection;
  }
}
