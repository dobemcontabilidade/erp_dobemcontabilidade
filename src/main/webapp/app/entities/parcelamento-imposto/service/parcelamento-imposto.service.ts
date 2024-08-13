import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IParcelamentoImposto, NewParcelamentoImposto } from '../parcelamento-imposto.model';

export type PartialUpdateParcelamentoImposto = Partial<IParcelamentoImposto> & Pick<IParcelamentoImposto, 'id'>;

export type EntityResponseType = HttpResponse<IParcelamentoImposto>;
export type EntityArrayResponseType = HttpResponse<IParcelamentoImposto[]>;

@Injectable({ providedIn: 'root' })
export class ParcelamentoImpostoService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/parcelamento-impostos');

  create(parcelamentoImposto: NewParcelamentoImposto): Observable<EntityResponseType> {
    return this.http.post<IParcelamentoImposto>(this.resourceUrl, parcelamentoImposto, { observe: 'response' });
  }

  update(parcelamentoImposto: IParcelamentoImposto): Observable<EntityResponseType> {
    return this.http.put<IParcelamentoImposto>(
      `${this.resourceUrl}/${this.getParcelamentoImpostoIdentifier(parcelamentoImposto)}`,
      parcelamentoImposto,
      { observe: 'response' },
    );
  }

  partialUpdate(parcelamentoImposto: PartialUpdateParcelamentoImposto): Observable<EntityResponseType> {
    return this.http.patch<IParcelamentoImposto>(
      `${this.resourceUrl}/${this.getParcelamentoImpostoIdentifier(parcelamentoImposto)}`,
      parcelamentoImposto,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IParcelamentoImposto>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IParcelamentoImposto[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getParcelamentoImpostoIdentifier(parcelamentoImposto: Pick<IParcelamentoImposto, 'id'>): number {
    return parcelamentoImposto.id;
  }

  compareParcelamentoImposto(o1: Pick<IParcelamentoImposto, 'id'> | null, o2: Pick<IParcelamentoImposto, 'id'> | null): boolean {
    return o1 && o2 ? this.getParcelamentoImpostoIdentifier(o1) === this.getParcelamentoImpostoIdentifier(o2) : o1 === o2;
  }

  addParcelamentoImpostoToCollectionIfMissing<Type extends Pick<IParcelamentoImposto, 'id'>>(
    parcelamentoImpostoCollection: Type[],
    ...parcelamentoImpostosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const parcelamentoImpostos: Type[] = parcelamentoImpostosToCheck.filter(isPresent);
    if (parcelamentoImpostos.length > 0) {
      const parcelamentoImpostoCollectionIdentifiers = parcelamentoImpostoCollection.map(parcelamentoImpostoItem =>
        this.getParcelamentoImpostoIdentifier(parcelamentoImpostoItem),
      );
      const parcelamentoImpostosToAdd = parcelamentoImpostos.filter(parcelamentoImpostoItem => {
        const parcelamentoImpostoIdentifier = this.getParcelamentoImpostoIdentifier(parcelamentoImpostoItem);
        if (parcelamentoImpostoCollectionIdentifiers.includes(parcelamentoImpostoIdentifier)) {
          return false;
        }
        parcelamentoImpostoCollectionIdentifiers.push(parcelamentoImpostoIdentifier);
        return true;
      });
      return [...parcelamentoImpostosToAdd, ...parcelamentoImpostoCollection];
    }
    return parcelamentoImpostoCollection;
  }
}
