import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAnexoEmpresa, NewAnexoEmpresa } from '../anexo-empresa.model';

export type PartialUpdateAnexoEmpresa = Partial<IAnexoEmpresa> & Pick<IAnexoEmpresa, 'id'>;

export type EntityResponseType = HttpResponse<IAnexoEmpresa>;
export type EntityArrayResponseType = HttpResponse<IAnexoEmpresa[]>;

@Injectable({ providedIn: 'root' })
export class AnexoEmpresaService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/anexo-empresas');

  create(anexoEmpresa: NewAnexoEmpresa): Observable<EntityResponseType> {
    return this.http.post<IAnexoEmpresa>(this.resourceUrl, anexoEmpresa, { observe: 'response' });
  }

  update(anexoEmpresa: IAnexoEmpresa): Observable<EntityResponseType> {
    return this.http.put<IAnexoEmpresa>(`${this.resourceUrl}/${this.getAnexoEmpresaIdentifier(anexoEmpresa)}`, anexoEmpresa, {
      observe: 'response',
    });
  }

  partialUpdate(anexoEmpresa: PartialUpdateAnexoEmpresa): Observable<EntityResponseType> {
    return this.http.patch<IAnexoEmpresa>(`${this.resourceUrl}/${this.getAnexoEmpresaIdentifier(anexoEmpresa)}`, anexoEmpresa, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAnexoEmpresa>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAnexoEmpresa[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAnexoEmpresaIdentifier(anexoEmpresa: Pick<IAnexoEmpresa, 'id'>): number {
    return anexoEmpresa.id;
  }

  compareAnexoEmpresa(o1: Pick<IAnexoEmpresa, 'id'> | null, o2: Pick<IAnexoEmpresa, 'id'> | null): boolean {
    return o1 && o2 ? this.getAnexoEmpresaIdentifier(o1) === this.getAnexoEmpresaIdentifier(o2) : o1 === o2;
  }

  addAnexoEmpresaToCollectionIfMissing<Type extends Pick<IAnexoEmpresa, 'id'>>(
    anexoEmpresaCollection: Type[],
    ...anexoEmpresasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const anexoEmpresas: Type[] = anexoEmpresasToCheck.filter(isPresent);
    if (anexoEmpresas.length > 0) {
      const anexoEmpresaCollectionIdentifiers = anexoEmpresaCollection.map(anexoEmpresaItem =>
        this.getAnexoEmpresaIdentifier(anexoEmpresaItem),
      );
      const anexoEmpresasToAdd = anexoEmpresas.filter(anexoEmpresaItem => {
        const anexoEmpresaIdentifier = this.getAnexoEmpresaIdentifier(anexoEmpresaItem);
        if (anexoEmpresaCollectionIdentifiers.includes(anexoEmpresaIdentifier)) {
          return false;
        }
        anexoEmpresaCollectionIdentifiers.push(anexoEmpresaIdentifier);
        return true;
      });
      return [...anexoEmpresasToAdd, ...anexoEmpresaCollection];
    }
    return anexoEmpresaCollection;
  }
}
