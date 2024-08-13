import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IImpostoEmpresaModelo, NewImpostoEmpresaModelo } from '../imposto-empresa-modelo.model';

export type PartialUpdateImpostoEmpresaModelo = Partial<IImpostoEmpresaModelo> & Pick<IImpostoEmpresaModelo, 'id'>;

export type EntityResponseType = HttpResponse<IImpostoEmpresaModelo>;
export type EntityArrayResponseType = HttpResponse<IImpostoEmpresaModelo[]>;

@Injectable({ providedIn: 'root' })
export class ImpostoEmpresaModeloService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/imposto-empresa-modelos');

  create(impostoEmpresaModelo: NewImpostoEmpresaModelo): Observable<EntityResponseType> {
    return this.http.post<IImpostoEmpresaModelo>(this.resourceUrl, impostoEmpresaModelo, { observe: 'response' });
  }

  update(impostoEmpresaModelo: IImpostoEmpresaModelo): Observable<EntityResponseType> {
    return this.http.put<IImpostoEmpresaModelo>(
      `${this.resourceUrl}/${this.getImpostoEmpresaModeloIdentifier(impostoEmpresaModelo)}`,
      impostoEmpresaModelo,
      { observe: 'response' },
    );
  }

  partialUpdate(impostoEmpresaModelo: PartialUpdateImpostoEmpresaModelo): Observable<EntityResponseType> {
    return this.http.patch<IImpostoEmpresaModelo>(
      `${this.resourceUrl}/${this.getImpostoEmpresaModeloIdentifier(impostoEmpresaModelo)}`,
      impostoEmpresaModelo,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IImpostoEmpresaModelo>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IImpostoEmpresaModelo[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getImpostoEmpresaModeloIdentifier(impostoEmpresaModelo: Pick<IImpostoEmpresaModelo, 'id'>): number {
    return impostoEmpresaModelo.id;
  }

  compareImpostoEmpresaModelo(o1: Pick<IImpostoEmpresaModelo, 'id'> | null, o2: Pick<IImpostoEmpresaModelo, 'id'> | null): boolean {
    return o1 && o2 ? this.getImpostoEmpresaModeloIdentifier(o1) === this.getImpostoEmpresaModeloIdentifier(o2) : o1 === o2;
  }

  addImpostoEmpresaModeloToCollectionIfMissing<Type extends Pick<IImpostoEmpresaModelo, 'id'>>(
    impostoEmpresaModeloCollection: Type[],
    ...impostoEmpresaModelosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const impostoEmpresaModelos: Type[] = impostoEmpresaModelosToCheck.filter(isPresent);
    if (impostoEmpresaModelos.length > 0) {
      const impostoEmpresaModeloCollectionIdentifiers = impostoEmpresaModeloCollection.map(impostoEmpresaModeloItem =>
        this.getImpostoEmpresaModeloIdentifier(impostoEmpresaModeloItem),
      );
      const impostoEmpresaModelosToAdd = impostoEmpresaModelos.filter(impostoEmpresaModeloItem => {
        const impostoEmpresaModeloIdentifier = this.getImpostoEmpresaModeloIdentifier(impostoEmpresaModeloItem);
        if (impostoEmpresaModeloCollectionIdentifiers.includes(impostoEmpresaModeloIdentifier)) {
          return false;
        }
        impostoEmpresaModeloCollectionIdentifiers.push(impostoEmpresaModeloIdentifier);
        return true;
      });
      return [...impostoEmpresaModelosToAdd, ...impostoEmpresaModeloCollection];
    }
    return impostoEmpresaModeloCollection;
  }
}
