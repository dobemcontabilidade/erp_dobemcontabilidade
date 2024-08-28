import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IRedeSocialEmpresa, NewRedeSocialEmpresa } from '../rede-social-empresa.model';

export type PartialUpdateRedeSocialEmpresa = Partial<IRedeSocialEmpresa> & Pick<IRedeSocialEmpresa, 'id'>;

export type EntityResponseType = HttpResponse<IRedeSocialEmpresa>;
export type EntityArrayResponseType = HttpResponse<IRedeSocialEmpresa[]>;

@Injectable({ providedIn: 'root' })
export class RedeSocialEmpresaService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/rede-social-empresas');

  create(redeSocialEmpresa: NewRedeSocialEmpresa): Observable<EntityResponseType> {
    return this.http.post<IRedeSocialEmpresa>(this.resourceUrl, redeSocialEmpresa, { observe: 'response' });
  }

  update(redeSocialEmpresa: IRedeSocialEmpresa): Observable<EntityResponseType> {
    return this.http.put<IRedeSocialEmpresa>(
      `${this.resourceUrl}/${this.getRedeSocialEmpresaIdentifier(redeSocialEmpresa)}`,
      redeSocialEmpresa,
      { observe: 'response' },
    );
  }

  partialUpdate(redeSocialEmpresa: PartialUpdateRedeSocialEmpresa): Observable<EntityResponseType> {
    return this.http.patch<IRedeSocialEmpresa>(
      `${this.resourceUrl}/${this.getRedeSocialEmpresaIdentifier(redeSocialEmpresa)}`,
      redeSocialEmpresa,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRedeSocialEmpresa>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRedeSocialEmpresa[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getRedeSocialEmpresaIdentifier(redeSocialEmpresa: Pick<IRedeSocialEmpresa, 'id'>): number {
    return redeSocialEmpresa.id;
  }

  compareRedeSocialEmpresa(o1: Pick<IRedeSocialEmpresa, 'id'> | null, o2: Pick<IRedeSocialEmpresa, 'id'> | null): boolean {
    return o1 && o2 ? this.getRedeSocialEmpresaIdentifier(o1) === this.getRedeSocialEmpresaIdentifier(o2) : o1 === o2;
  }

  addRedeSocialEmpresaToCollectionIfMissing<Type extends Pick<IRedeSocialEmpresa, 'id'>>(
    redeSocialEmpresaCollection: Type[],
    ...redeSocialEmpresasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const redeSocialEmpresas: Type[] = redeSocialEmpresasToCheck.filter(isPresent);
    if (redeSocialEmpresas.length > 0) {
      const redeSocialEmpresaCollectionIdentifiers = redeSocialEmpresaCollection.map(redeSocialEmpresaItem =>
        this.getRedeSocialEmpresaIdentifier(redeSocialEmpresaItem),
      );
      const redeSocialEmpresasToAdd = redeSocialEmpresas.filter(redeSocialEmpresaItem => {
        const redeSocialEmpresaIdentifier = this.getRedeSocialEmpresaIdentifier(redeSocialEmpresaItem);
        if (redeSocialEmpresaCollectionIdentifiers.includes(redeSocialEmpresaIdentifier)) {
          return false;
        }
        redeSocialEmpresaCollectionIdentifiers.push(redeSocialEmpresaIdentifier);
        return true;
      });
      return [...redeSocialEmpresasToAdd, ...redeSocialEmpresaCollection];
    }
    return redeSocialEmpresaCollection;
  }
}
