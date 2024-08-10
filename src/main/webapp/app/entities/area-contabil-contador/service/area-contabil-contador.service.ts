import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAreaContabilContador, NewAreaContabilContador } from '../area-contabil-contador.model';

export type PartialUpdateAreaContabilContador = Partial<IAreaContabilContador> & Pick<IAreaContabilContador, 'id'>;

export type EntityResponseType = HttpResponse<IAreaContabilContador>;
export type EntityArrayResponseType = HttpResponse<IAreaContabilContador[]>;

@Injectable({ providedIn: 'root' })
export class AreaContabilContadorService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/area-contabil-contadors');

  create(areaContabilContador: NewAreaContabilContador): Observable<EntityResponseType> {
    return this.http.post<IAreaContabilContador>(this.resourceUrl, areaContabilContador, { observe: 'response' });
  }

  update(areaContabilContador: IAreaContabilContador): Observable<EntityResponseType> {
    return this.http.put<IAreaContabilContador>(
      `${this.resourceUrl}/${this.getAreaContabilContadorIdentifier(areaContabilContador)}`,
      areaContabilContador,
      { observe: 'response' },
    );
  }

  partialUpdate(areaContabilContador: PartialUpdateAreaContabilContador): Observable<EntityResponseType> {
    return this.http.patch<IAreaContabilContador>(
      `${this.resourceUrl}/${this.getAreaContabilContadorIdentifier(areaContabilContador)}`,
      areaContabilContador,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAreaContabilContador>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAreaContabilContador[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAreaContabilContadorIdentifier(areaContabilContador: Pick<IAreaContabilContador, 'id'>): number {
    return areaContabilContador.id;
  }

  compareAreaContabilContador(o1: Pick<IAreaContabilContador, 'id'> | null, o2: Pick<IAreaContabilContador, 'id'> | null): boolean {
    return o1 && o2 ? this.getAreaContabilContadorIdentifier(o1) === this.getAreaContabilContadorIdentifier(o2) : o1 === o2;
  }

  addAreaContabilContadorToCollectionIfMissing<Type extends Pick<IAreaContabilContador, 'id'>>(
    areaContabilContadorCollection: Type[],
    ...areaContabilContadorsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const areaContabilContadors: Type[] = areaContabilContadorsToCheck.filter(isPresent);
    if (areaContabilContadors.length > 0) {
      const areaContabilContadorCollectionIdentifiers = areaContabilContadorCollection.map(areaContabilContadorItem =>
        this.getAreaContabilContadorIdentifier(areaContabilContadorItem),
      );
      const areaContabilContadorsToAdd = areaContabilContadors.filter(areaContabilContadorItem => {
        const areaContabilContadorIdentifier = this.getAreaContabilContadorIdentifier(areaContabilContadorItem);
        if (areaContabilContadorCollectionIdentifiers.includes(areaContabilContadorIdentifier)) {
          return false;
        }
        areaContabilContadorCollectionIdentifiers.push(areaContabilContadorIdentifier);
        return true;
      });
      return [...areaContabilContadorsToAdd, ...areaContabilContadorCollection];
    }
    return areaContabilContadorCollection;
  }
}
