import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAtividadeEmpresa, NewAtividadeEmpresa } from '../atividade-empresa.model';

export type PartialUpdateAtividadeEmpresa = Partial<IAtividadeEmpresa> & Pick<IAtividadeEmpresa, 'id'>;

export type EntityResponseType = HttpResponse<IAtividadeEmpresa>;
export type EntityArrayResponseType = HttpResponse<IAtividadeEmpresa[]>;

@Injectable({ providedIn: 'root' })
export class AtividadeEmpresaService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/atividade-empresas');

  create(atividadeEmpresa: NewAtividadeEmpresa): Observable<EntityResponseType> {
    return this.http.post<IAtividadeEmpresa>(this.resourceUrl, atividadeEmpresa, { observe: 'response' });
  }

  update(atividadeEmpresa: IAtividadeEmpresa): Observable<EntityResponseType> {
    return this.http.put<IAtividadeEmpresa>(
      `${this.resourceUrl}/${this.getAtividadeEmpresaIdentifier(atividadeEmpresa)}`,
      atividadeEmpresa,
      { observe: 'response' },
    );
  }

  partialUpdate(atividadeEmpresa: PartialUpdateAtividadeEmpresa): Observable<EntityResponseType> {
    return this.http.patch<IAtividadeEmpresa>(
      `${this.resourceUrl}/${this.getAtividadeEmpresaIdentifier(atividadeEmpresa)}`,
      atividadeEmpresa,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAtividadeEmpresa>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAtividadeEmpresa[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAtividadeEmpresaIdentifier(atividadeEmpresa: Pick<IAtividadeEmpresa, 'id'>): number {
    return atividadeEmpresa.id;
  }

  compareAtividadeEmpresa(o1: Pick<IAtividadeEmpresa, 'id'> | null, o2: Pick<IAtividadeEmpresa, 'id'> | null): boolean {
    return o1 && o2 ? this.getAtividadeEmpresaIdentifier(o1) === this.getAtividadeEmpresaIdentifier(o2) : o1 === o2;
  }

  addAtividadeEmpresaToCollectionIfMissing<Type extends Pick<IAtividadeEmpresa, 'id'>>(
    atividadeEmpresaCollection: Type[],
    ...atividadeEmpresasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const atividadeEmpresas: Type[] = atividadeEmpresasToCheck.filter(isPresent);
    if (atividadeEmpresas.length > 0) {
      const atividadeEmpresaCollectionIdentifiers = atividadeEmpresaCollection.map(atividadeEmpresaItem =>
        this.getAtividadeEmpresaIdentifier(atividadeEmpresaItem),
      );
      const atividadeEmpresasToAdd = atividadeEmpresas.filter(atividadeEmpresaItem => {
        const atividadeEmpresaIdentifier = this.getAtividadeEmpresaIdentifier(atividadeEmpresaItem);
        if (atividadeEmpresaCollectionIdentifiers.includes(atividadeEmpresaIdentifier)) {
          return false;
        }
        atividadeEmpresaCollectionIdentifiers.push(atividadeEmpresaIdentifier);
        return true;
      });
      return [...atividadeEmpresasToAdd, ...atividadeEmpresaCollection];
    }
    return atividadeEmpresaCollection;
  }
}
