import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IFuncionalidade, NewFuncionalidade } from '../funcionalidade.model';

export type PartialUpdateFuncionalidade = Partial<IFuncionalidade> & Pick<IFuncionalidade, 'id'>;

export type EntityResponseType = HttpResponse<IFuncionalidade>;
export type EntityArrayResponseType = HttpResponse<IFuncionalidade[]>;

@Injectable({ providedIn: 'root' })
export class FuncionalidadeService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/funcionalidades');

  create(funcionalidade: NewFuncionalidade): Observable<EntityResponseType> {
    return this.http.post<IFuncionalidade>(this.resourceUrl, funcionalidade, { observe: 'response' });
  }

  update(funcionalidade: IFuncionalidade): Observable<EntityResponseType> {
    return this.http.put<IFuncionalidade>(`${this.resourceUrl}/${this.getFuncionalidadeIdentifier(funcionalidade)}`, funcionalidade, {
      observe: 'response',
    });
  }

  partialUpdate(funcionalidade: PartialUpdateFuncionalidade): Observable<EntityResponseType> {
    return this.http.patch<IFuncionalidade>(`${this.resourceUrl}/${this.getFuncionalidadeIdentifier(funcionalidade)}`, funcionalidade, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFuncionalidade>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFuncionalidade[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getFuncionalidadeIdentifier(funcionalidade: Pick<IFuncionalidade, 'id'>): number {
    return funcionalidade.id;
  }

  compareFuncionalidade(o1: Pick<IFuncionalidade, 'id'> | null, o2: Pick<IFuncionalidade, 'id'> | null): boolean {
    return o1 && o2 ? this.getFuncionalidadeIdentifier(o1) === this.getFuncionalidadeIdentifier(o2) : o1 === o2;
  }

  addFuncionalidadeToCollectionIfMissing<Type extends Pick<IFuncionalidade, 'id'>>(
    funcionalidadeCollection: Type[],
    ...funcionalidadesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const funcionalidades: Type[] = funcionalidadesToCheck.filter(isPresent);
    if (funcionalidades.length > 0) {
      const funcionalidadeCollectionIdentifiers = funcionalidadeCollection.map(funcionalidadeItem =>
        this.getFuncionalidadeIdentifier(funcionalidadeItem),
      );
      const funcionalidadesToAdd = funcionalidades.filter(funcionalidadeItem => {
        const funcionalidadeIdentifier = this.getFuncionalidadeIdentifier(funcionalidadeItem);
        if (funcionalidadeCollectionIdentifiers.includes(funcionalidadeIdentifier)) {
          return false;
        }
        funcionalidadeCollectionIdentifiers.push(funcionalidadeIdentifier);
        return true;
      });
      return [...funcionalidadesToAdd, ...funcionalidadeCollection];
    }
    return funcionalidadeCollection;
  }
}
