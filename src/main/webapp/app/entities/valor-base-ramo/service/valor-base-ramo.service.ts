import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IValorBaseRamo, NewValorBaseRamo } from '../valor-base-ramo.model';

export type PartialUpdateValorBaseRamo = Partial<IValorBaseRamo> & Pick<IValorBaseRamo, 'id'>;

export type EntityResponseType = HttpResponse<IValorBaseRamo>;
export type EntityArrayResponseType = HttpResponse<IValorBaseRamo[]>;

@Injectable({ providedIn: 'root' })
export class ValorBaseRamoService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/valor-base-ramos');

  create(valorBaseRamo: NewValorBaseRamo): Observable<EntityResponseType> {
    return this.http.post<IValorBaseRamo>(this.resourceUrl, valorBaseRamo, { observe: 'response' });
  }

  update(valorBaseRamo: IValorBaseRamo): Observable<EntityResponseType> {
    return this.http.put<IValorBaseRamo>(`${this.resourceUrl}/${this.getValorBaseRamoIdentifier(valorBaseRamo)}`, valorBaseRamo, {
      observe: 'response',
    });
  }

  partialUpdate(valorBaseRamo: PartialUpdateValorBaseRamo): Observable<EntityResponseType> {
    return this.http.patch<IValorBaseRamo>(`${this.resourceUrl}/${this.getValorBaseRamoIdentifier(valorBaseRamo)}`, valorBaseRamo, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IValorBaseRamo>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IValorBaseRamo[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getValorBaseRamoIdentifier(valorBaseRamo: Pick<IValorBaseRamo, 'id'>): number {
    return valorBaseRamo.id;
  }

  compareValorBaseRamo(o1: Pick<IValorBaseRamo, 'id'> | null, o2: Pick<IValorBaseRamo, 'id'> | null): boolean {
    return o1 && o2 ? this.getValorBaseRamoIdentifier(o1) === this.getValorBaseRamoIdentifier(o2) : o1 === o2;
  }

  addValorBaseRamoToCollectionIfMissing<Type extends Pick<IValorBaseRamo, 'id'>>(
    valorBaseRamoCollection: Type[],
    ...valorBaseRamosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const valorBaseRamos: Type[] = valorBaseRamosToCheck.filter(isPresent);
    if (valorBaseRamos.length > 0) {
      const valorBaseRamoCollectionIdentifiers = valorBaseRamoCollection.map(valorBaseRamoItem =>
        this.getValorBaseRamoIdentifier(valorBaseRamoItem),
      );
      const valorBaseRamosToAdd = valorBaseRamos.filter(valorBaseRamoItem => {
        const valorBaseRamoIdentifier = this.getValorBaseRamoIdentifier(valorBaseRamoItem);
        if (valorBaseRamoCollectionIdentifiers.includes(valorBaseRamoIdentifier)) {
          return false;
        }
        valorBaseRamoCollectionIdentifiers.push(valorBaseRamoIdentifier);
        return true;
      });
      return [...valorBaseRamosToAdd, ...valorBaseRamoCollection];
    }
    return valorBaseRamoCollection;
  }
}
