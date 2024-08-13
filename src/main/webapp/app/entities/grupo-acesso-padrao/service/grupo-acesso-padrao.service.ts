import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IGrupoAcessoPadrao, NewGrupoAcessoPadrao } from '../grupo-acesso-padrao.model';

export type PartialUpdateGrupoAcessoPadrao = Partial<IGrupoAcessoPadrao> & Pick<IGrupoAcessoPadrao, 'id'>;

export type EntityResponseType = HttpResponse<IGrupoAcessoPadrao>;
export type EntityArrayResponseType = HttpResponse<IGrupoAcessoPadrao[]>;

@Injectable({ providedIn: 'root' })
export class GrupoAcessoPadraoService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/grupo-acesso-padraos');

  create(grupoAcessoPadrao: NewGrupoAcessoPadrao): Observable<EntityResponseType> {
    return this.http.post<IGrupoAcessoPadrao>(this.resourceUrl, grupoAcessoPadrao, { observe: 'response' });
  }

  update(grupoAcessoPadrao: IGrupoAcessoPadrao): Observable<EntityResponseType> {
    return this.http.put<IGrupoAcessoPadrao>(
      `${this.resourceUrl}/${this.getGrupoAcessoPadraoIdentifier(grupoAcessoPadrao)}`,
      grupoAcessoPadrao,
      { observe: 'response' },
    );
  }

  partialUpdate(grupoAcessoPadrao: PartialUpdateGrupoAcessoPadrao): Observable<EntityResponseType> {
    return this.http.patch<IGrupoAcessoPadrao>(
      `${this.resourceUrl}/${this.getGrupoAcessoPadraoIdentifier(grupoAcessoPadrao)}`,
      grupoAcessoPadrao,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IGrupoAcessoPadrao>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IGrupoAcessoPadrao[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getGrupoAcessoPadraoIdentifier(grupoAcessoPadrao: Pick<IGrupoAcessoPadrao, 'id'>): number {
    return grupoAcessoPadrao.id;
  }

  compareGrupoAcessoPadrao(o1: Pick<IGrupoAcessoPadrao, 'id'> | null, o2: Pick<IGrupoAcessoPadrao, 'id'> | null): boolean {
    return o1 && o2 ? this.getGrupoAcessoPadraoIdentifier(o1) === this.getGrupoAcessoPadraoIdentifier(o2) : o1 === o2;
  }

  addGrupoAcessoPadraoToCollectionIfMissing<Type extends Pick<IGrupoAcessoPadrao, 'id'>>(
    grupoAcessoPadraoCollection: Type[],
    ...grupoAcessoPadraosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const grupoAcessoPadraos: Type[] = grupoAcessoPadraosToCheck.filter(isPresent);
    if (grupoAcessoPadraos.length > 0) {
      const grupoAcessoPadraoCollectionIdentifiers = grupoAcessoPadraoCollection.map(grupoAcessoPadraoItem =>
        this.getGrupoAcessoPadraoIdentifier(grupoAcessoPadraoItem),
      );
      const grupoAcessoPadraosToAdd = grupoAcessoPadraos.filter(grupoAcessoPadraoItem => {
        const grupoAcessoPadraoIdentifier = this.getGrupoAcessoPadraoIdentifier(grupoAcessoPadraoItem);
        if (grupoAcessoPadraoCollectionIdentifiers.includes(grupoAcessoPadraoIdentifier)) {
          return false;
        }
        grupoAcessoPadraoCollectionIdentifiers.push(grupoAcessoPadraoIdentifier);
        return true;
      });
      return [...grupoAcessoPadraosToAdd, ...grupoAcessoPadraoCollection];
    }
    return grupoAcessoPadraoCollection;
  }
}
