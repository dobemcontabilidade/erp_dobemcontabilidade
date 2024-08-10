import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IFrequencia, NewFrequencia } from '../frequencia.model';

export type PartialUpdateFrequencia = Partial<IFrequencia> & Pick<IFrequencia, 'id'>;

export type EntityResponseType = HttpResponse<IFrequencia>;
export type EntityArrayResponseType = HttpResponse<IFrequencia[]>;

@Injectable({ providedIn: 'root' })
export class FrequenciaService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/frequencias');

  create(frequencia: NewFrequencia): Observable<EntityResponseType> {
    return this.http.post<IFrequencia>(this.resourceUrl, frequencia, { observe: 'response' });
  }

  update(frequencia: IFrequencia): Observable<EntityResponseType> {
    return this.http.put<IFrequencia>(`${this.resourceUrl}/${this.getFrequenciaIdentifier(frequencia)}`, frequencia, {
      observe: 'response',
    });
  }

  partialUpdate(frequencia: PartialUpdateFrequencia): Observable<EntityResponseType> {
    return this.http.patch<IFrequencia>(`${this.resourceUrl}/${this.getFrequenciaIdentifier(frequencia)}`, frequencia, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFrequencia>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFrequencia[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getFrequenciaIdentifier(frequencia: Pick<IFrequencia, 'id'>): number {
    return frequencia.id;
  }

  compareFrequencia(o1: Pick<IFrequencia, 'id'> | null, o2: Pick<IFrequencia, 'id'> | null): boolean {
    return o1 && o2 ? this.getFrequenciaIdentifier(o1) === this.getFrequenciaIdentifier(o2) : o1 === o2;
  }

  addFrequenciaToCollectionIfMissing<Type extends Pick<IFrequencia, 'id'>>(
    frequenciaCollection: Type[],
    ...frequenciasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const frequencias: Type[] = frequenciasToCheck.filter(isPresent);
    if (frequencias.length > 0) {
      const frequenciaCollectionIdentifiers = frequenciaCollection.map(frequenciaItem => this.getFrequenciaIdentifier(frequenciaItem));
      const frequenciasToAdd = frequencias.filter(frequenciaItem => {
        const frequenciaIdentifier = this.getFrequenciaIdentifier(frequenciaItem);
        if (frequenciaCollectionIdentifiers.includes(frequenciaIdentifier)) {
          return false;
        }
        frequenciaCollectionIdentifiers.push(frequenciaIdentifier);
        return true;
      });
      return [...frequenciasToAdd, ...frequenciaCollection];
    }
    return frequenciaCollection;
  }
}
