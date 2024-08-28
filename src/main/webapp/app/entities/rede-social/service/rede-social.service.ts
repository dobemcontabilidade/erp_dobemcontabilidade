import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IRedeSocial, NewRedeSocial } from '../rede-social.model';

export type PartialUpdateRedeSocial = Partial<IRedeSocial> & Pick<IRedeSocial, 'id'>;

export type EntityResponseType = HttpResponse<IRedeSocial>;
export type EntityArrayResponseType = HttpResponse<IRedeSocial[]>;

@Injectable({ providedIn: 'root' })
export class RedeSocialService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/rede-socials');

  create(redeSocial: NewRedeSocial): Observable<EntityResponseType> {
    return this.http.post<IRedeSocial>(this.resourceUrl, redeSocial, { observe: 'response' });
  }

  update(redeSocial: IRedeSocial): Observable<EntityResponseType> {
    return this.http.put<IRedeSocial>(`${this.resourceUrl}/${this.getRedeSocialIdentifier(redeSocial)}`, redeSocial, {
      observe: 'response',
    });
  }

  partialUpdate(redeSocial: PartialUpdateRedeSocial): Observable<EntityResponseType> {
    return this.http.patch<IRedeSocial>(`${this.resourceUrl}/${this.getRedeSocialIdentifier(redeSocial)}`, redeSocial, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRedeSocial>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRedeSocial[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getRedeSocialIdentifier(redeSocial: Pick<IRedeSocial, 'id'>): number {
    return redeSocial.id;
  }

  compareRedeSocial(o1: Pick<IRedeSocial, 'id'> | null, o2: Pick<IRedeSocial, 'id'> | null): boolean {
    return o1 && o2 ? this.getRedeSocialIdentifier(o1) === this.getRedeSocialIdentifier(o2) : o1 === o2;
  }

  addRedeSocialToCollectionIfMissing<Type extends Pick<IRedeSocial, 'id'>>(
    redeSocialCollection: Type[],
    ...redeSocialsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const redeSocials: Type[] = redeSocialsToCheck.filter(isPresent);
    if (redeSocials.length > 0) {
      const redeSocialCollectionIdentifiers = redeSocialCollection.map(redeSocialItem => this.getRedeSocialIdentifier(redeSocialItem));
      const redeSocialsToAdd = redeSocials.filter(redeSocialItem => {
        const redeSocialIdentifier = this.getRedeSocialIdentifier(redeSocialItem);
        if (redeSocialCollectionIdentifiers.includes(redeSocialIdentifier)) {
          return false;
        }
        redeSocialCollectionIdentifiers.push(redeSocialIdentifier);
        return true;
      });
      return [...redeSocialsToAdd, ...redeSocialCollection];
    }
    return redeSocialCollection;
  }
}
