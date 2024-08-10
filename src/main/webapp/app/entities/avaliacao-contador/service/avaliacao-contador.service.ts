import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAvaliacaoContador, NewAvaliacaoContador } from '../avaliacao-contador.model';

export type PartialUpdateAvaliacaoContador = Partial<IAvaliacaoContador> & Pick<IAvaliacaoContador, 'id'>;

export type EntityResponseType = HttpResponse<IAvaliacaoContador>;
export type EntityArrayResponseType = HttpResponse<IAvaliacaoContador[]>;

@Injectable({ providedIn: 'root' })
export class AvaliacaoContadorService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/avaliacao-contadors');

  create(avaliacaoContador: NewAvaliacaoContador): Observable<EntityResponseType> {
    return this.http.post<IAvaliacaoContador>(this.resourceUrl, avaliacaoContador, { observe: 'response' });
  }

  update(avaliacaoContador: IAvaliacaoContador): Observable<EntityResponseType> {
    return this.http.put<IAvaliacaoContador>(
      `${this.resourceUrl}/${this.getAvaliacaoContadorIdentifier(avaliacaoContador)}`,
      avaliacaoContador,
      { observe: 'response' },
    );
  }

  partialUpdate(avaliacaoContador: PartialUpdateAvaliacaoContador): Observable<EntityResponseType> {
    return this.http.patch<IAvaliacaoContador>(
      `${this.resourceUrl}/${this.getAvaliacaoContadorIdentifier(avaliacaoContador)}`,
      avaliacaoContador,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAvaliacaoContador>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAvaliacaoContador[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAvaliacaoContadorIdentifier(avaliacaoContador: Pick<IAvaliacaoContador, 'id'>): number {
    return avaliacaoContador.id;
  }

  compareAvaliacaoContador(o1: Pick<IAvaliacaoContador, 'id'> | null, o2: Pick<IAvaliacaoContador, 'id'> | null): boolean {
    return o1 && o2 ? this.getAvaliacaoContadorIdentifier(o1) === this.getAvaliacaoContadorIdentifier(o2) : o1 === o2;
  }

  addAvaliacaoContadorToCollectionIfMissing<Type extends Pick<IAvaliacaoContador, 'id'>>(
    avaliacaoContadorCollection: Type[],
    ...avaliacaoContadorsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const avaliacaoContadors: Type[] = avaliacaoContadorsToCheck.filter(isPresent);
    if (avaliacaoContadors.length > 0) {
      const avaliacaoContadorCollectionIdentifiers = avaliacaoContadorCollection.map(avaliacaoContadorItem =>
        this.getAvaliacaoContadorIdentifier(avaliacaoContadorItem),
      );
      const avaliacaoContadorsToAdd = avaliacaoContadors.filter(avaliacaoContadorItem => {
        const avaliacaoContadorIdentifier = this.getAvaliacaoContadorIdentifier(avaliacaoContadorItem);
        if (avaliacaoContadorCollectionIdentifiers.includes(avaliacaoContadorIdentifier)) {
          return false;
        }
        avaliacaoContadorCollectionIdentifiers.push(avaliacaoContadorIdentifier);
        return true;
      });
      return [...avaliacaoContadorsToAdd, ...avaliacaoContadorCollection];
    }
    return avaliacaoContadorCollection;
  }
}
