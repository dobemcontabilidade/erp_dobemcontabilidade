import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISegmentoCnae, NewSegmentoCnae } from '../segmento-cnae.model';

export type PartialUpdateSegmentoCnae = Partial<ISegmentoCnae> & Pick<ISegmentoCnae, 'id'>;

export type EntityResponseType = HttpResponse<ISegmentoCnae>;
export type EntityArrayResponseType = HttpResponse<ISegmentoCnae[]>;

@Injectable({ providedIn: 'root' })
export class SegmentoCnaeService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/segmento-cnaes');

  create(segmentoCnae: NewSegmentoCnae): Observable<EntityResponseType> {
    return this.http.post<ISegmentoCnae>(this.resourceUrl, segmentoCnae, { observe: 'response' });
  }

  update(segmentoCnae: ISegmentoCnae): Observable<EntityResponseType> {
    return this.http.put<ISegmentoCnae>(`${this.resourceUrl}/${this.getSegmentoCnaeIdentifier(segmentoCnae)}`, segmentoCnae, {
      observe: 'response',
    });
  }

  partialUpdate(segmentoCnae: PartialUpdateSegmentoCnae): Observable<EntityResponseType> {
    return this.http.patch<ISegmentoCnae>(`${this.resourceUrl}/${this.getSegmentoCnaeIdentifier(segmentoCnae)}`, segmentoCnae, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISegmentoCnae>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISegmentoCnae[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getSegmentoCnaeIdentifier(segmentoCnae: Pick<ISegmentoCnae, 'id'>): number {
    return segmentoCnae.id;
  }

  compareSegmentoCnae(o1: Pick<ISegmentoCnae, 'id'> | null, o2: Pick<ISegmentoCnae, 'id'> | null): boolean {
    return o1 && o2 ? this.getSegmentoCnaeIdentifier(o1) === this.getSegmentoCnaeIdentifier(o2) : o1 === o2;
  }

  addSegmentoCnaeToCollectionIfMissing<Type extends Pick<ISegmentoCnae, 'id'>>(
    segmentoCnaeCollection: Type[],
    ...segmentoCnaesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const segmentoCnaes: Type[] = segmentoCnaesToCheck.filter(isPresent);
    if (segmentoCnaes.length > 0) {
      const segmentoCnaeCollectionIdentifiers = segmentoCnaeCollection.map(segmentoCnaeItem =>
        this.getSegmentoCnaeIdentifier(segmentoCnaeItem),
      );
      const segmentoCnaesToAdd = segmentoCnaes.filter(segmentoCnaeItem => {
        const segmentoCnaeIdentifier = this.getSegmentoCnaeIdentifier(segmentoCnaeItem);
        if (segmentoCnaeCollectionIdentifiers.includes(segmentoCnaeIdentifier)) {
          return false;
        }
        segmentoCnaeCollectionIdentifiers.push(segmentoCnaeIdentifier);
        return true;
      });
      return [...segmentoCnaesToAdd, ...segmentoCnaeCollection];
    }
    return segmentoCnaeCollection;
  }
}
