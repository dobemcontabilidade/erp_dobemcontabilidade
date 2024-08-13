import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISocio, NewSocio } from '../socio.model';

export type PartialUpdateSocio = Partial<ISocio> & Pick<ISocio, 'id'>;

export type EntityResponseType = HttpResponse<ISocio>;
export type EntityArrayResponseType = HttpResponse<ISocio[]>;

@Injectable({ providedIn: 'root' })
export class SocioService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/socios');

  create(socio: NewSocio): Observable<EntityResponseType> {
    return this.http.post<ISocio>(this.resourceUrl, socio, { observe: 'response' });
  }

  update(socio: ISocio): Observable<EntityResponseType> {
    return this.http.put<ISocio>(`${this.resourceUrl}/${this.getSocioIdentifier(socio)}`, socio, { observe: 'response' });
  }

  partialUpdate(socio: PartialUpdateSocio): Observable<EntityResponseType> {
    return this.http.patch<ISocio>(`${this.resourceUrl}/${this.getSocioIdentifier(socio)}`, socio, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISocio>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISocio[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getSocioIdentifier(socio: Pick<ISocio, 'id'>): number {
    return socio.id;
  }

  compareSocio(o1: Pick<ISocio, 'id'> | null, o2: Pick<ISocio, 'id'> | null): boolean {
    return o1 && o2 ? this.getSocioIdentifier(o1) === this.getSocioIdentifier(o2) : o1 === o2;
  }

  addSocioToCollectionIfMissing<Type extends Pick<ISocio, 'id'>>(
    socioCollection: Type[],
    ...sociosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const socios: Type[] = sociosToCheck.filter(isPresent);
    if (socios.length > 0) {
      const socioCollectionIdentifiers = socioCollection.map(socioItem => this.getSocioIdentifier(socioItem));
      const sociosToAdd = socios.filter(socioItem => {
        const socioIdentifier = this.getSocioIdentifier(socioItem);
        if (socioCollectionIdentifiers.includes(socioIdentifier)) {
          return false;
        }
        socioCollectionIdentifiers.push(socioIdentifier);
        return true;
      });
      return [...sociosToAdd, ...socioCollection];
    }
    return socioCollection;
  }
}
