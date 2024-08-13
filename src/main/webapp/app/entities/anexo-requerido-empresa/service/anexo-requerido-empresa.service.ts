import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAnexoRequeridoEmpresa, NewAnexoRequeridoEmpresa } from '../anexo-requerido-empresa.model';

export type PartialUpdateAnexoRequeridoEmpresa = Partial<IAnexoRequeridoEmpresa> & Pick<IAnexoRequeridoEmpresa, 'id'>;

export type EntityResponseType = HttpResponse<IAnexoRequeridoEmpresa>;
export type EntityArrayResponseType = HttpResponse<IAnexoRequeridoEmpresa[]>;

@Injectable({ providedIn: 'root' })
export class AnexoRequeridoEmpresaService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/anexo-requerido-empresas');

  create(anexoRequeridoEmpresa: NewAnexoRequeridoEmpresa): Observable<EntityResponseType> {
    return this.http.post<IAnexoRequeridoEmpresa>(this.resourceUrl, anexoRequeridoEmpresa, { observe: 'response' });
  }

  update(anexoRequeridoEmpresa: IAnexoRequeridoEmpresa): Observable<EntityResponseType> {
    return this.http.put<IAnexoRequeridoEmpresa>(
      `${this.resourceUrl}/${this.getAnexoRequeridoEmpresaIdentifier(anexoRequeridoEmpresa)}`,
      anexoRequeridoEmpresa,
      { observe: 'response' },
    );
  }

  partialUpdate(anexoRequeridoEmpresa: PartialUpdateAnexoRequeridoEmpresa): Observable<EntityResponseType> {
    return this.http.patch<IAnexoRequeridoEmpresa>(
      `${this.resourceUrl}/${this.getAnexoRequeridoEmpresaIdentifier(anexoRequeridoEmpresa)}`,
      anexoRequeridoEmpresa,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAnexoRequeridoEmpresa>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAnexoRequeridoEmpresa[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAnexoRequeridoEmpresaIdentifier(anexoRequeridoEmpresa: Pick<IAnexoRequeridoEmpresa, 'id'>): number {
    return anexoRequeridoEmpresa.id;
  }

  compareAnexoRequeridoEmpresa(o1: Pick<IAnexoRequeridoEmpresa, 'id'> | null, o2: Pick<IAnexoRequeridoEmpresa, 'id'> | null): boolean {
    return o1 && o2 ? this.getAnexoRequeridoEmpresaIdentifier(o1) === this.getAnexoRequeridoEmpresaIdentifier(o2) : o1 === o2;
  }

  addAnexoRequeridoEmpresaToCollectionIfMissing<Type extends Pick<IAnexoRequeridoEmpresa, 'id'>>(
    anexoRequeridoEmpresaCollection: Type[],
    ...anexoRequeridoEmpresasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const anexoRequeridoEmpresas: Type[] = anexoRequeridoEmpresasToCheck.filter(isPresent);
    if (anexoRequeridoEmpresas.length > 0) {
      const anexoRequeridoEmpresaCollectionIdentifiers = anexoRequeridoEmpresaCollection.map(anexoRequeridoEmpresaItem =>
        this.getAnexoRequeridoEmpresaIdentifier(anexoRequeridoEmpresaItem),
      );
      const anexoRequeridoEmpresasToAdd = anexoRequeridoEmpresas.filter(anexoRequeridoEmpresaItem => {
        const anexoRequeridoEmpresaIdentifier = this.getAnexoRequeridoEmpresaIdentifier(anexoRequeridoEmpresaItem);
        if (anexoRequeridoEmpresaCollectionIdentifiers.includes(anexoRequeridoEmpresaIdentifier)) {
          return false;
        }
        anexoRequeridoEmpresaCollectionIdentifiers.push(anexoRequeridoEmpresaIdentifier);
        return true;
      });
      return [...anexoRequeridoEmpresasToAdd, ...anexoRequeridoEmpresaCollection];
    }
    return anexoRequeridoEmpresaCollection;
  }
}
