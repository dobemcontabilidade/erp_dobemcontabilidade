import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IFluxoExecucao, NewFluxoExecucao } from '../fluxo-execucao.model';

export type PartialUpdateFluxoExecucao = Partial<IFluxoExecucao> & Pick<IFluxoExecucao, 'id'>;

export type EntityResponseType = HttpResponse<IFluxoExecucao>;
export type EntityArrayResponseType = HttpResponse<IFluxoExecucao[]>;

@Injectable({ providedIn: 'root' })
export class FluxoExecucaoService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/fluxo-execucaos');

  create(fluxoExecucao: NewFluxoExecucao): Observable<EntityResponseType> {
    return this.http.post<IFluxoExecucao>(this.resourceUrl, fluxoExecucao, { observe: 'response' });
  }

  update(fluxoExecucao: IFluxoExecucao): Observable<EntityResponseType> {
    return this.http.put<IFluxoExecucao>(`${this.resourceUrl}/${this.getFluxoExecucaoIdentifier(fluxoExecucao)}`, fluxoExecucao, {
      observe: 'response',
    });
  }

  partialUpdate(fluxoExecucao: PartialUpdateFluxoExecucao): Observable<EntityResponseType> {
    return this.http.patch<IFluxoExecucao>(`${this.resourceUrl}/${this.getFluxoExecucaoIdentifier(fluxoExecucao)}`, fluxoExecucao, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFluxoExecucao>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFluxoExecucao[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getFluxoExecucaoIdentifier(fluxoExecucao: Pick<IFluxoExecucao, 'id'>): number {
    return fluxoExecucao.id;
  }

  compareFluxoExecucao(o1: Pick<IFluxoExecucao, 'id'> | null, o2: Pick<IFluxoExecucao, 'id'> | null): boolean {
    return o1 && o2 ? this.getFluxoExecucaoIdentifier(o1) === this.getFluxoExecucaoIdentifier(o2) : o1 === o2;
  }

  addFluxoExecucaoToCollectionIfMissing<Type extends Pick<IFluxoExecucao, 'id'>>(
    fluxoExecucaoCollection: Type[],
    ...fluxoExecucaosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const fluxoExecucaos: Type[] = fluxoExecucaosToCheck.filter(isPresent);
    if (fluxoExecucaos.length > 0) {
      const fluxoExecucaoCollectionIdentifiers = fluxoExecucaoCollection.map(fluxoExecucaoItem =>
        this.getFluxoExecucaoIdentifier(fluxoExecucaoItem),
      );
      const fluxoExecucaosToAdd = fluxoExecucaos.filter(fluxoExecucaoItem => {
        const fluxoExecucaoIdentifier = this.getFluxoExecucaoIdentifier(fluxoExecucaoItem);
        if (fluxoExecucaoCollectionIdentifiers.includes(fluxoExecucaoIdentifier)) {
          return false;
        }
        fluxoExecucaoCollectionIdentifiers.push(fluxoExecucaoIdentifier);
        return true;
      });
      return [...fluxoExecucaosToAdd, ...fluxoExecucaoCollection];
    }
    return fluxoExecucaoCollection;
  }
}
