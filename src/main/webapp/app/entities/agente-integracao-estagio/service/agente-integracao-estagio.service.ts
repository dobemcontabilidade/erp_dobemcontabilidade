import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAgenteIntegracaoEstagio, NewAgenteIntegracaoEstagio } from '../agente-integracao-estagio.model';

export type PartialUpdateAgenteIntegracaoEstagio = Partial<IAgenteIntegracaoEstagio> & Pick<IAgenteIntegracaoEstagio, 'id'>;

export type EntityResponseType = HttpResponse<IAgenteIntegracaoEstagio>;
export type EntityArrayResponseType = HttpResponse<IAgenteIntegracaoEstagio[]>;

@Injectable({ providedIn: 'root' })
export class AgenteIntegracaoEstagioService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/agente-integracao-estagios');

  create(agenteIntegracaoEstagio: NewAgenteIntegracaoEstagio): Observable<EntityResponseType> {
    return this.http.post<IAgenteIntegracaoEstagio>(this.resourceUrl, agenteIntegracaoEstagio, { observe: 'response' });
  }

  update(agenteIntegracaoEstagio: IAgenteIntegracaoEstagio): Observable<EntityResponseType> {
    return this.http.put<IAgenteIntegracaoEstagio>(
      `${this.resourceUrl}/${this.getAgenteIntegracaoEstagioIdentifier(agenteIntegracaoEstagio)}`,
      agenteIntegracaoEstagio,
      { observe: 'response' },
    );
  }

  partialUpdate(agenteIntegracaoEstagio: PartialUpdateAgenteIntegracaoEstagio): Observable<EntityResponseType> {
    return this.http.patch<IAgenteIntegracaoEstagio>(
      `${this.resourceUrl}/${this.getAgenteIntegracaoEstagioIdentifier(agenteIntegracaoEstagio)}`,
      agenteIntegracaoEstagio,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAgenteIntegracaoEstagio>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAgenteIntegracaoEstagio[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAgenteIntegracaoEstagioIdentifier(agenteIntegracaoEstagio: Pick<IAgenteIntegracaoEstagio, 'id'>): number {
    return agenteIntegracaoEstagio.id;
  }

  compareAgenteIntegracaoEstagio(
    o1: Pick<IAgenteIntegracaoEstagio, 'id'> | null,
    o2: Pick<IAgenteIntegracaoEstagio, 'id'> | null,
  ): boolean {
    return o1 && o2 ? this.getAgenteIntegracaoEstagioIdentifier(o1) === this.getAgenteIntegracaoEstagioIdentifier(o2) : o1 === o2;
  }

  addAgenteIntegracaoEstagioToCollectionIfMissing<Type extends Pick<IAgenteIntegracaoEstagio, 'id'>>(
    agenteIntegracaoEstagioCollection: Type[],
    ...agenteIntegracaoEstagiosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const agenteIntegracaoEstagios: Type[] = agenteIntegracaoEstagiosToCheck.filter(isPresent);
    if (agenteIntegracaoEstagios.length > 0) {
      const agenteIntegracaoEstagioCollectionIdentifiers = agenteIntegracaoEstagioCollection.map(agenteIntegracaoEstagioItem =>
        this.getAgenteIntegracaoEstagioIdentifier(agenteIntegracaoEstagioItem),
      );
      const agenteIntegracaoEstagiosToAdd = agenteIntegracaoEstagios.filter(agenteIntegracaoEstagioItem => {
        const agenteIntegracaoEstagioIdentifier = this.getAgenteIntegracaoEstagioIdentifier(agenteIntegracaoEstagioItem);
        if (agenteIntegracaoEstagioCollectionIdentifiers.includes(agenteIntegracaoEstagioIdentifier)) {
          return false;
        }
        agenteIntegracaoEstagioCollectionIdentifiers.push(agenteIntegracaoEstagioIdentifier);
        return true;
      });
      return [...agenteIntegracaoEstagiosToAdd, ...agenteIntegracaoEstagioCollection];
    }
    return agenteIntegracaoEstagioCollection;
  }
}
