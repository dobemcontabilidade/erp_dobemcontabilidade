import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPerfilRedeSocial, NewPerfilRedeSocial } from '../perfil-rede-social.model';

export type PartialUpdatePerfilRedeSocial = Partial<IPerfilRedeSocial> & Pick<IPerfilRedeSocial, 'id'>;

export type EntityResponseType = HttpResponse<IPerfilRedeSocial>;
export type EntityArrayResponseType = HttpResponse<IPerfilRedeSocial[]>;

@Injectable({ providedIn: 'root' })
export class PerfilRedeSocialService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/perfil-rede-socials');

  create(perfilRedeSocial: NewPerfilRedeSocial): Observable<EntityResponseType> {
    return this.http.post<IPerfilRedeSocial>(this.resourceUrl, perfilRedeSocial, { observe: 'response' });
  }

  update(perfilRedeSocial: IPerfilRedeSocial): Observable<EntityResponseType> {
    return this.http.put<IPerfilRedeSocial>(
      `${this.resourceUrl}/${this.getPerfilRedeSocialIdentifier(perfilRedeSocial)}`,
      perfilRedeSocial,
      { observe: 'response' },
    );
  }

  partialUpdate(perfilRedeSocial: PartialUpdatePerfilRedeSocial): Observable<EntityResponseType> {
    return this.http.patch<IPerfilRedeSocial>(
      `${this.resourceUrl}/${this.getPerfilRedeSocialIdentifier(perfilRedeSocial)}`,
      perfilRedeSocial,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPerfilRedeSocial>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPerfilRedeSocial[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPerfilRedeSocialIdentifier(perfilRedeSocial: Pick<IPerfilRedeSocial, 'id'>): number {
    return perfilRedeSocial.id;
  }

  comparePerfilRedeSocial(o1: Pick<IPerfilRedeSocial, 'id'> | null, o2: Pick<IPerfilRedeSocial, 'id'> | null): boolean {
    return o1 && o2 ? this.getPerfilRedeSocialIdentifier(o1) === this.getPerfilRedeSocialIdentifier(o2) : o1 === o2;
  }

  addPerfilRedeSocialToCollectionIfMissing<Type extends Pick<IPerfilRedeSocial, 'id'>>(
    perfilRedeSocialCollection: Type[],
    ...perfilRedeSocialsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const perfilRedeSocials: Type[] = perfilRedeSocialsToCheck.filter(isPresent);
    if (perfilRedeSocials.length > 0) {
      const perfilRedeSocialCollectionIdentifiers = perfilRedeSocialCollection.map(perfilRedeSocialItem =>
        this.getPerfilRedeSocialIdentifier(perfilRedeSocialItem),
      );
      const perfilRedeSocialsToAdd = perfilRedeSocials.filter(perfilRedeSocialItem => {
        const perfilRedeSocialIdentifier = this.getPerfilRedeSocialIdentifier(perfilRedeSocialItem);
        if (perfilRedeSocialCollectionIdentifiers.includes(perfilRedeSocialIdentifier)) {
          return false;
        }
        perfilRedeSocialCollectionIdentifiers.push(perfilRedeSocialIdentifier);
        return true;
      });
      return [...perfilRedeSocialsToAdd, ...perfilRedeSocialCollection];
    }
    return perfilRedeSocialCollection;
  }
}
