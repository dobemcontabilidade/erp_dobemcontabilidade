import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IModulo, NewModulo } from '../modulo.model';

export type PartialUpdateModulo = Partial<IModulo> & Pick<IModulo, 'id'>;

export type EntityResponseType = HttpResponse<IModulo>;
export type EntityArrayResponseType = HttpResponse<IModulo[]>;

@Injectable({ providedIn: 'root' })
export class ModuloService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/modulos');

  create(modulo: NewModulo): Observable<EntityResponseType> {
    return this.http.post<IModulo>(this.resourceUrl, modulo, { observe: 'response' });
  }

  update(modulo: IModulo): Observable<EntityResponseType> {
    return this.http.put<IModulo>(`${this.resourceUrl}/${this.getModuloIdentifier(modulo)}`, modulo, { observe: 'response' });
  }

  partialUpdate(modulo: PartialUpdateModulo): Observable<EntityResponseType> {
    return this.http.patch<IModulo>(`${this.resourceUrl}/${this.getModuloIdentifier(modulo)}`, modulo, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IModulo>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IModulo[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getModuloIdentifier(modulo: Pick<IModulo, 'id'>): number {
    return modulo.id;
  }

  compareModulo(o1: Pick<IModulo, 'id'> | null, o2: Pick<IModulo, 'id'> | null): boolean {
    return o1 && o2 ? this.getModuloIdentifier(o1) === this.getModuloIdentifier(o2) : o1 === o2;
  }

  addModuloToCollectionIfMissing<Type extends Pick<IModulo, 'id'>>(
    moduloCollection: Type[],
    ...modulosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const modulos: Type[] = modulosToCheck.filter(isPresent);
    if (modulos.length > 0) {
      const moduloCollectionIdentifiers = moduloCollection.map(moduloItem => this.getModuloIdentifier(moduloItem));
      const modulosToAdd = modulos.filter(moduloItem => {
        const moduloIdentifier = this.getModuloIdentifier(moduloItem);
        if (moduloCollectionIdentifiers.includes(moduloIdentifier)) {
          return false;
        }
        moduloCollectionIdentifiers.push(moduloIdentifier);
        return true;
      });
      return [...modulosToAdd, ...moduloCollection];
    }
    return moduloCollection;
  }
}
