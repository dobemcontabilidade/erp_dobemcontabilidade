import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPerfilAcesso, NewPerfilAcesso } from '../perfil-acesso.model';

export type PartialUpdatePerfilAcesso = Partial<IPerfilAcesso> & Pick<IPerfilAcesso, 'id'>;

export type EntityResponseType = HttpResponse<IPerfilAcesso>;
export type EntityArrayResponseType = HttpResponse<IPerfilAcesso[]>;

@Injectable({ providedIn: 'root' })
export class PerfilAcessoService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/perfil-acessos');

  create(perfilAcesso: NewPerfilAcesso): Observable<EntityResponseType> {
    return this.http.post<IPerfilAcesso>(this.resourceUrl, perfilAcesso, { observe: 'response' });
  }

  update(perfilAcesso: IPerfilAcesso): Observable<EntityResponseType> {
    return this.http.put<IPerfilAcesso>(`${this.resourceUrl}/${this.getPerfilAcessoIdentifier(perfilAcesso)}`, perfilAcesso, {
      observe: 'response',
    });
  }

  partialUpdate(perfilAcesso: PartialUpdatePerfilAcesso): Observable<EntityResponseType> {
    return this.http.patch<IPerfilAcesso>(`${this.resourceUrl}/${this.getPerfilAcessoIdentifier(perfilAcesso)}`, perfilAcesso, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPerfilAcesso>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPerfilAcesso[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPerfilAcessoIdentifier(perfilAcesso: Pick<IPerfilAcesso, 'id'>): number {
    return perfilAcesso.id;
  }

  comparePerfilAcesso(o1: Pick<IPerfilAcesso, 'id'> | null, o2: Pick<IPerfilAcesso, 'id'> | null): boolean {
    return o1 && o2 ? this.getPerfilAcessoIdentifier(o1) === this.getPerfilAcessoIdentifier(o2) : o1 === o2;
  }

  addPerfilAcessoToCollectionIfMissing<Type extends Pick<IPerfilAcesso, 'id'>>(
    perfilAcessoCollection: Type[],
    ...perfilAcessosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const perfilAcessos: Type[] = perfilAcessosToCheck.filter(isPresent);
    if (perfilAcessos.length > 0) {
      const perfilAcessoCollectionIdentifiers = perfilAcessoCollection.map(perfilAcessoItem =>
        this.getPerfilAcessoIdentifier(perfilAcessoItem),
      );
      const perfilAcessosToAdd = perfilAcessos.filter(perfilAcessoItem => {
        const perfilAcessoIdentifier = this.getPerfilAcessoIdentifier(perfilAcessoItem);
        if (perfilAcessoCollectionIdentifiers.includes(perfilAcessoIdentifier)) {
          return false;
        }
        perfilAcessoCollectionIdentifiers.push(perfilAcessoIdentifier);
        return true;
      });
      return [...perfilAcessosToAdd, ...perfilAcessoCollection];
    }
    return perfilAcessoCollection;
  }
}
