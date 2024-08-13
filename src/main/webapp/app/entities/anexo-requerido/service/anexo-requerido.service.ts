import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAnexoRequerido, NewAnexoRequerido } from '../anexo-requerido.model';

export type PartialUpdateAnexoRequerido = Partial<IAnexoRequerido> & Pick<IAnexoRequerido, 'id'>;

export type EntityResponseType = HttpResponse<IAnexoRequerido>;
export type EntityArrayResponseType = HttpResponse<IAnexoRequerido[]>;

@Injectable({ providedIn: 'root' })
export class AnexoRequeridoService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/anexo-requeridos');

  create(anexoRequerido: NewAnexoRequerido): Observable<EntityResponseType> {
    return this.http.post<IAnexoRequerido>(this.resourceUrl, anexoRequerido, { observe: 'response' });
  }

  update(anexoRequerido: IAnexoRequerido): Observable<EntityResponseType> {
    return this.http.put<IAnexoRequerido>(`${this.resourceUrl}/${this.getAnexoRequeridoIdentifier(anexoRequerido)}`, anexoRequerido, {
      observe: 'response',
    });
  }

  partialUpdate(anexoRequerido: PartialUpdateAnexoRequerido): Observable<EntityResponseType> {
    return this.http.patch<IAnexoRequerido>(`${this.resourceUrl}/${this.getAnexoRequeridoIdentifier(anexoRequerido)}`, anexoRequerido, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAnexoRequerido>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAnexoRequerido[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAnexoRequeridoIdentifier(anexoRequerido: Pick<IAnexoRequerido, 'id'>): number {
    return anexoRequerido.id;
  }

  compareAnexoRequerido(o1: Pick<IAnexoRequerido, 'id'> | null, o2: Pick<IAnexoRequerido, 'id'> | null): boolean {
    return o1 && o2 ? this.getAnexoRequeridoIdentifier(o1) === this.getAnexoRequeridoIdentifier(o2) : o1 === o2;
  }

  addAnexoRequeridoToCollectionIfMissing<Type extends Pick<IAnexoRequerido, 'id'>>(
    anexoRequeridoCollection: Type[],
    ...anexoRequeridosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const anexoRequeridos: Type[] = anexoRequeridosToCheck.filter(isPresent);
    if (anexoRequeridos.length > 0) {
      const anexoRequeridoCollectionIdentifiers = anexoRequeridoCollection.map(anexoRequeridoItem =>
        this.getAnexoRequeridoIdentifier(anexoRequeridoItem),
      );
      const anexoRequeridosToAdd = anexoRequeridos.filter(anexoRequeridoItem => {
        const anexoRequeridoIdentifier = this.getAnexoRequeridoIdentifier(anexoRequeridoItem);
        if (anexoRequeridoCollectionIdentifiers.includes(anexoRequeridoIdentifier)) {
          return false;
        }
        anexoRequeridoCollectionIdentifiers.push(anexoRequeridoIdentifier);
        return true;
      });
      return [...anexoRequeridosToAdd, ...anexoRequeridoCollection];
    }
    return anexoRequeridoCollection;
  }
}
