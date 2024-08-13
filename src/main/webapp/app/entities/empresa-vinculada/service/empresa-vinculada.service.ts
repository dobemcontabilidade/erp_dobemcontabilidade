import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEmpresaVinculada, NewEmpresaVinculada } from '../empresa-vinculada.model';

export type PartialUpdateEmpresaVinculada = Partial<IEmpresaVinculada> & Pick<IEmpresaVinculada, 'id'>;

export type EntityResponseType = HttpResponse<IEmpresaVinculada>;
export type EntityArrayResponseType = HttpResponse<IEmpresaVinculada[]>;

@Injectable({ providedIn: 'root' })
export class EmpresaVinculadaService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/empresa-vinculadas');

  create(empresaVinculada: NewEmpresaVinculada): Observable<EntityResponseType> {
    return this.http.post<IEmpresaVinculada>(this.resourceUrl, empresaVinculada, { observe: 'response' });
  }

  update(empresaVinculada: IEmpresaVinculada): Observable<EntityResponseType> {
    return this.http.put<IEmpresaVinculada>(
      `${this.resourceUrl}/${this.getEmpresaVinculadaIdentifier(empresaVinculada)}`,
      empresaVinculada,
      { observe: 'response' },
    );
  }

  partialUpdate(empresaVinculada: PartialUpdateEmpresaVinculada): Observable<EntityResponseType> {
    return this.http.patch<IEmpresaVinculada>(
      `${this.resourceUrl}/${this.getEmpresaVinculadaIdentifier(empresaVinculada)}`,
      empresaVinculada,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEmpresaVinculada>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEmpresaVinculada[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getEmpresaVinculadaIdentifier(empresaVinculada: Pick<IEmpresaVinculada, 'id'>): number {
    return empresaVinculada.id;
  }

  compareEmpresaVinculada(o1: Pick<IEmpresaVinculada, 'id'> | null, o2: Pick<IEmpresaVinculada, 'id'> | null): boolean {
    return o1 && o2 ? this.getEmpresaVinculadaIdentifier(o1) === this.getEmpresaVinculadaIdentifier(o2) : o1 === o2;
  }

  addEmpresaVinculadaToCollectionIfMissing<Type extends Pick<IEmpresaVinculada, 'id'>>(
    empresaVinculadaCollection: Type[],
    ...empresaVinculadasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const empresaVinculadas: Type[] = empresaVinculadasToCheck.filter(isPresent);
    if (empresaVinculadas.length > 0) {
      const empresaVinculadaCollectionIdentifiers = empresaVinculadaCollection.map(empresaVinculadaItem =>
        this.getEmpresaVinculadaIdentifier(empresaVinculadaItem),
      );
      const empresaVinculadasToAdd = empresaVinculadas.filter(empresaVinculadaItem => {
        const empresaVinculadaIdentifier = this.getEmpresaVinculadaIdentifier(empresaVinculadaItem);
        if (empresaVinculadaCollectionIdentifiers.includes(empresaVinculadaIdentifier)) {
          return false;
        }
        empresaVinculadaCollectionIdentifiers.push(empresaVinculadaIdentifier);
        return true;
      });
      return [...empresaVinculadasToAdd, ...empresaVinculadaCollection];
    }
    return empresaVinculadaCollection;
  }
}
