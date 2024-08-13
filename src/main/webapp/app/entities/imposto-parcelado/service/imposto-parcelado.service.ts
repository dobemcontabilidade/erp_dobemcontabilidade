import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IImpostoParcelado, NewImpostoParcelado } from '../imposto-parcelado.model';

export type PartialUpdateImpostoParcelado = Partial<IImpostoParcelado> & Pick<IImpostoParcelado, 'id'>;

export type EntityResponseType = HttpResponse<IImpostoParcelado>;
export type EntityArrayResponseType = HttpResponse<IImpostoParcelado[]>;

@Injectable({ providedIn: 'root' })
export class ImpostoParceladoService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/imposto-parcelados');

  create(impostoParcelado: NewImpostoParcelado): Observable<EntityResponseType> {
    return this.http.post<IImpostoParcelado>(this.resourceUrl, impostoParcelado, { observe: 'response' });
  }

  update(impostoParcelado: IImpostoParcelado): Observable<EntityResponseType> {
    return this.http.put<IImpostoParcelado>(
      `${this.resourceUrl}/${this.getImpostoParceladoIdentifier(impostoParcelado)}`,
      impostoParcelado,
      { observe: 'response' },
    );
  }

  partialUpdate(impostoParcelado: PartialUpdateImpostoParcelado): Observable<EntityResponseType> {
    return this.http.patch<IImpostoParcelado>(
      `${this.resourceUrl}/${this.getImpostoParceladoIdentifier(impostoParcelado)}`,
      impostoParcelado,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IImpostoParcelado>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IImpostoParcelado[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getImpostoParceladoIdentifier(impostoParcelado: Pick<IImpostoParcelado, 'id'>): number {
    return impostoParcelado.id;
  }

  compareImpostoParcelado(o1: Pick<IImpostoParcelado, 'id'> | null, o2: Pick<IImpostoParcelado, 'id'> | null): boolean {
    return o1 && o2 ? this.getImpostoParceladoIdentifier(o1) === this.getImpostoParceladoIdentifier(o2) : o1 === o2;
  }

  addImpostoParceladoToCollectionIfMissing<Type extends Pick<IImpostoParcelado, 'id'>>(
    impostoParceladoCollection: Type[],
    ...impostoParceladosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const impostoParcelados: Type[] = impostoParceladosToCheck.filter(isPresent);
    if (impostoParcelados.length > 0) {
      const impostoParceladoCollectionIdentifiers = impostoParceladoCollection.map(impostoParceladoItem =>
        this.getImpostoParceladoIdentifier(impostoParceladoItem),
      );
      const impostoParceladosToAdd = impostoParcelados.filter(impostoParceladoItem => {
        const impostoParceladoIdentifier = this.getImpostoParceladoIdentifier(impostoParceladoItem);
        if (impostoParceladoCollectionIdentifiers.includes(impostoParceladoIdentifier)) {
          return false;
        }
        impostoParceladoCollectionIdentifiers.push(impostoParceladoIdentifier);
        return true;
      });
      return [...impostoParceladosToAdd, ...impostoParceladoCollection];
    }
    return impostoParceladoCollection;
  }
}
