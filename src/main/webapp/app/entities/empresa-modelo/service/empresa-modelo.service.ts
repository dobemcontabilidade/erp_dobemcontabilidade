import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEmpresaModelo, NewEmpresaModelo } from '../empresa-modelo.model';

export type PartialUpdateEmpresaModelo = Partial<IEmpresaModelo> & Pick<IEmpresaModelo, 'id'>;

export type EntityResponseType = HttpResponse<IEmpresaModelo>;
export type EntityArrayResponseType = HttpResponse<IEmpresaModelo[]>;

@Injectable({ providedIn: 'root' })
export class EmpresaModeloService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/empresa-modelos');

  create(empresaModelo: NewEmpresaModelo): Observable<EntityResponseType> {
    return this.http.post<IEmpresaModelo>(this.resourceUrl, empresaModelo, { observe: 'response' });
  }

  update(empresaModelo: IEmpresaModelo): Observable<EntityResponseType> {
    return this.http.put<IEmpresaModelo>(`${this.resourceUrl}/${this.getEmpresaModeloIdentifier(empresaModelo)}`, empresaModelo, {
      observe: 'response',
    });
  }

  partialUpdate(empresaModelo: PartialUpdateEmpresaModelo): Observable<EntityResponseType> {
    return this.http.patch<IEmpresaModelo>(`${this.resourceUrl}/${this.getEmpresaModeloIdentifier(empresaModelo)}`, empresaModelo, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEmpresaModelo>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEmpresaModelo[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getEmpresaModeloIdentifier(empresaModelo: Pick<IEmpresaModelo, 'id'>): number {
    return empresaModelo.id;
  }

  compareEmpresaModelo(o1: Pick<IEmpresaModelo, 'id'> | null, o2: Pick<IEmpresaModelo, 'id'> | null): boolean {
    return o1 && o2 ? this.getEmpresaModeloIdentifier(o1) === this.getEmpresaModeloIdentifier(o2) : o1 === o2;
  }

  addEmpresaModeloToCollectionIfMissing<Type extends Pick<IEmpresaModelo, 'id'>>(
    empresaModeloCollection: Type[],
    ...empresaModelosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const empresaModelos: Type[] = empresaModelosToCheck.filter(isPresent);
    if (empresaModelos.length > 0) {
      const empresaModeloCollectionIdentifiers = empresaModeloCollection.map(empresaModeloItem =>
        this.getEmpresaModeloIdentifier(empresaModeloItem),
      );
      const empresaModelosToAdd = empresaModelos.filter(empresaModeloItem => {
        const empresaModeloIdentifier = this.getEmpresaModeloIdentifier(empresaModeloItem);
        if (empresaModeloCollectionIdentifiers.includes(empresaModeloIdentifier)) {
          return false;
        }
        empresaModeloCollectionIdentifiers.push(empresaModeloIdentifier);
        return true;
      });
      return [...empresaModelosToAdd, ...empresaModeloCollection];
    }
    return empresaModeloCollection;
  }
}
