import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IFluxoModelo, NewFluxoModelo } from '../fluxo-modelo.model';

export type PartialUpdateFluxoModelo = Partial<IFluxoModelo> & Pick<IFluxoModelo, 'id'>;

export type EntityResponseType = HttpResponse<IFluxoModelo>;
export type EntityArrayResponseType = HttpResponse<IFluxoModelo[]>;

@Injectable({ providedIn: 'root' })
export class FluxoModeloService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/fluxo-modelos');

  create(fluxoModelo: NewFluxoModelo): Observable<EntityResponseType> {
    return this.http.post<IFluxoModelo>(this.resourceUrl, fluxoModelo, { observe: 'response' });
  }

  update(fluxoModelo: IFluxoModelo): Observable<EntityResponseType> {
    return this.http.put<IFluxoModelo>(`${this.resourceUrl}/${this.getFluxoModeloIdentifier(fluxoModelo)}`, fluxoModelo, {
      observe: 'response',
    });
  }

  partialUpdate(fluxoModelo: PartialUpdateFluxoModelo): Observable<EntityResponseType> {
    return this.http.patch<IFluxoModelo>(`${this.resourceUrl}/${this.getFluxoModeloIdentifier(fluxoModelo)}`, fluxoModelo, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFluxoModelo>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFluxoModelo[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getFluxoModeloIdentifier(fluxoModelo: Pick<IFluxoModelo, 'id'>): number {
    return fluxoModelo.id;
  }

  compareFluxoModelo(o1: Pick<IFluxoModelo, 'id'> | null, o2: Pick<IFluxoModelo, 'id'> | null): boolean {
    return o1 && o2 ? this.getFluxoModeloIdentifier(o1) === this.getFluxoModeloIdentifier(o2) : o1 === o2;
  }

  addFluxoModeloToCollectionIfMissing<Type extends Pick<IFluxoModelo, 'id'>>(
    fluxoModeloCollection: Type[],
    ...fluxoModelosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const fluxoModelos: Type[] = fluxoModelosToCheck.filter(isPresent);
    if (fluxoModelos.length > 0) {
      const fluxoModeloCollectionIdentifiers = fluxoModeloCollection.map(fluxoModeloItem => this.getFluxoModeloIdentifier(fluxoModeloItem));
      const fluxoModelosToAdd = fluxoModelos.filter(fluxoModeloItem => {
        const fluxoModeloIdentifier = this.getFluxoModeloIdentifier(fluxoModeloItem);
        if (fluxoModeloCollectionIdentifiers.includes(fluxoModeloIdentifier)) {
          return false;
        }
        fluxoModeloCollectionIdentifiers.push(fluxoModeloIdentifier);
        return true;
      });
      return [...fluxoModelosToAdd, ...fluxoModeloCollection];
    }
    return fluxoModeloCollection;
  }
}
