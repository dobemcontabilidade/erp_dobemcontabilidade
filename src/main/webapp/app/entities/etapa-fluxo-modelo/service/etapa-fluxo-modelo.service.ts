import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEtapaFluxoModelo, NewEtapaFluxoModelo } from '../etapa-fluxo-modelo.model';

export type PartialUpdateEtapaFluxoModelo = Partial<IEtapaFluxoModelo> & Pick<IEtapaFluxoModelo, 'id'>;

export type EntityResponseType = HttpResponse<IEtapaFluxoModelo>;
export type EntityArrayResponseType = HttpResponse<IEtapaFluxoModelo[]>;

@Injectable({ providedIn: 'root' })
export class EtapaFluxoModeloService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/etapa-fluxo-modelos');

  create(etapaFluxoModelo: NewEtapaFluxoModelo): Observable<EntityResponseType> {
    return this.http.post<IEtapaFluxoModelo>(this.resourceUrl, etapaFluxoModelo, { observe: 'response' });
  }

  update(etapaFluxoModelo: IEtapaFluxoModelo): Observable<EntityResponseType> {
    return this.http.put<IEtapaFluxoModelo>(
      `${this.resourceUrl}/${this.getEtapaFluxoModeloIdentifier(etapaFluxoModelo)}`,
      etapaFluxoModelo,
      { observe: 'response' },
    );
  }

  partialUpdate(etapaFluxoModelo: PartialUpdateEtapaFluxoModelo): Observable<EntityResponseType> {
    return this.http.patch<IEtapaFluxoModelo>(
      `${this.resourceUrl}/${this.getEtapaFluxoModeloIdentifier(etapaFluxoModelo)}`,
      etapaFluxoModelo,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEtapaFluxoModelo>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEtapaFluxoModelo[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getEtapaFluxoModeloIdentifier(etapaFluxoModelo: Pick<IEtapaFluxoModelo, 'id'>): number {
    return etapaFluxoModelo.id;
  }

  compareEtapaFluxoModelo(o1: Pick<IEtapaFluxoModelo, 'id'> | null, o2: Pick<IEtapaFluxoModelo, 'id'> | null): boolean {
    return o1 && o2 ? this.getEtapaFluxoModeloIdentifier(o1) === this.getEtapaFluxoModeloIdentifier(o2) : o1 === o2;
  }

  addEtapaFluxoModeloToCollectionIfMissing<Type extends Pick<IEtapaFluxoModelo, 'id'>>(
    etapaFluxoModeloCollection: Type[],
    ...etapaFluxoModelosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const etapaFluxoModelos: Type[] = etapaFluxoModelosToCheck.filter(isPresent);
    if (etapaFluxoModelos.length > 0) {
      const etapaFluxoModeloCollectionIdentifiers = etapaFluxoModeloCollection.map(etapaFluxoModeloItem =>
        this.getEtapaFluxoModeloIdentifier(etapaFluxoModeloItem),
      );
      const etapaFluxoModelosToAdd = etapaFluxoModelos.filter(etapaFluxoModeloItem => {
        const etapaFluxoModeloIdentifier = this.getEtapaFluxoModeloIdentifier(etapaFluxoModeloItem);
        if (etapaFluxoModeloCollectionIdentifiers.includes(etapaFluxoModeloIdentifier)) {
          return false;
        }
        etapaFluxoModeloCollectionIdentifiers.push(etapaFluxoModeloIdentifier);
        return true;
      });
      return [...etapaFluxoModelosToAdd, ...etapaFluxoModeloCollection];
    }
    return etapaFluxoModeloCollection;
  }
}
