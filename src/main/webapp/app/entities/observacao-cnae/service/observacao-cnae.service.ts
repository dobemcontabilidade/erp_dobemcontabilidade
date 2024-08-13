import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IObservacaoCnae, NewObservacaoCnae } from '../observacao-cnae.model';

export type PartialUpdateObservacaoCnae = Partial<IObservacaoCnae> & Pick<IObservacaoCnae, 'id'>;

export type EntityResponseType = HttpResponse<IObservacaoCnae>;
export type EntityArrayResponseType = HttpResponse<IObservacaoCnae[]>;

@Injectable({ providedIn: 'root' })
export class ObservacaoCnaeService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/observacao-cnaes');

  create(observacaoCnae: NewObservacaoCnae): Observable<EntityResponseType> {
    return this.http.post<IObservacaoCnae>(this.resourceUrl, observacaoCnae, { observe: 'response' });
  }

  update(observacaoCnae: IObservacaoCnae): Observable<EntityResponseType> {
    return this.http.put<IObservacaoCnae>(`${this.resourceUrl}/${this.getObservacaoCnaeIdentifier(observacaoCnae)}`, observacaoCnae, {
      observe: 'response',
    });
  }

  partialUpdate(observacaoCnae: PartialUpdateObservacaoCnae): Observable<EntityResponseType> {
    return this.http.patch<IObservacaoCnae>(`${this.resourceUrl}/${this.getObservacaoCnaeIdentifier(observacaoCnae)}`, observacaoCnae, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IObservacaoCnae>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IObservacaoCnae[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getObservacaoCnaeIdentifier(observacaoCnae: Pick<IObservacaoCnae, 'id'>): number {
    return observacaoCnae.id;
  }

  compareObservacaoCnae(o1: Pick<IObservacaoCnae, 'id'> | null, o2: Pick<IObservacaoCnae, 'id'> | null): boolean {
    return o1 && o2 ? this.getObservacaoCnaeIdentifier(o1) === this.getObservacaoCnaeIdentifier(o2) : o1 === o2;
  }

  addObservacaoCnaeToCollectionIfMissing<Type extends Pick<IObservacaoCnae, 'id'>>(
    observacaoCnaeCollection: Type[],
    ...observacaoCnaesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const observacaoCnaes: Type[] = observacaoCnaesToCheck.filter(isPresent);
    if (observacaoCnaes.length > 0) {
      const observacaoCnaeCollectionIdentifiers = observacaoCnaeCollection.map(observacaoCnaeItem =>
        this.getObservacaoCnaeIdentifier(observacaoCnaeItem),
      );
      const observacaoCnaesToAdd = observacaoCnaes.filter(observacaoCnaeItem => {
        const observacaoCnaeIdentifier = this.getObservacaoCnaeIdentifier(observacaoCnaeItem);
        if (observacaoCnaeCollectionIdentifiers.includes(observacaoCnaeIdentifier)) {
          return false;
        }
        observacaoCnaeCollectionIdentifiers.push(observacaoCnaeIdentifier);
        return true;
      });
      return [...observacaoCnaesToAdd, ...observacaoCnaeCollection];
    }
    return observacaoCnaeCollection;
  }
}
