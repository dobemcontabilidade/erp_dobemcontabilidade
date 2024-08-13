import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDependentesFuncionario, NewDependentesFuncionario } from '../dependentes-funcionario.model';

export type PartialUpdateDependentesFuncionario = Partial<IDependentesFuncionario> & Pick<IDependentesFuncionario, 'id'>;

export type EntityResponseType = HttpResponse<IDependentesFuncionario>;
export type EntityArrayResponseType = HttpResponse<IDependentesFuncionario[]>;

@Injectable({ providedIn: 'root' })
export class DependentesFuncionarioService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/dependentes-funcionarios');

  create(dependentesFuncionario: NewDependentesFuncionario): Observable<EntityResponseType> {
    return this.http.post<IDependentesFuncionario>(this.resourceUrl, dependentesFuncionario, { observe: 'response' });
  }

  update(dependentesFuncionario: IDependentesFuncionario): Observable<EntityResponseType> {
    return this.http.put<IDependentesFuncionario>(
      `${this.resourceUrl}/${this.getDependentesFuncionarioIdentifier(dependentesFuncionario)}`,
      dependentesFuncionario,
      { observe: 'response' },
    );
  }

  partialUpdate(dependentesFuncionario: PartialUpdateDependentesFuncionario): Observable<EntityResponseType> {
    return this.http.patch<IDependentesFuncionario>(
      `${this.resourceUrl}/${this.getDependentesFuncionarioIdentifier(dependentesFuncionario)}`,
      dependentesFuncionario,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDependentesFuncionario>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDependentesFuncionario[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getDependentesFuncionarioIdentifier(dependentesFuncionario: Pick<IDependentesFuncionario, 'id'>): number {
    return dependentesFuncionario.id;
  }

  compareDependentesFuncionario(o1: Pick<IDependentesFuncionario, 'id'> | null, o2: Pick<IDependentesFuncionario, 'id'> | null): boolean {
    return o1 && o2 ? this.getDependentesFuncionarioIdentifier(o1) === this.getDependentesFuncionarioIdentifier(o2) : o1 === o2;
  }

  addDependentesFuncionarioToCollectionIfMissing<Type extends Pick<IDependentesFuncionario, 'id'>>(
    dependentesFuncionarioCollection: Type[],
    ...dependentesFuncionariosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const dependentesFuncionarios: Type[] = dependentesFuncionariosToCheck.filter(isPresent);
    if (dependentesFuncionarios.length > 0) {
      const dependentesFuncionarioCollectionIdentifiers = dependentesFuncionarioCollection.map(dependentesFuncionarioItem =>
        this.getDependentesFuncionarioIdentifier(dependentesFuncionarioItem),
      );
      const dependentesFuncionariosToAdd = dependentesFuncionarios.filter(dependentesFuncionarioItem => {
        const dependentesFuncionarioIdentifier = this.getDependentesFuncionarioIdentifier(dependentesFuncionarioItem);
        if (dependentesFuncionarioCollectionIdentifiers.includes(dependentesFuncionarioIdentifier)) {
          return false;
        }
        dependentesFuncionarioCollectionIdentifiers.push(dependentesFuncionarioIdentifier);
        return true;
      });
      return [...dependentesFuncionariosToAdd, ...dependentesFuncionarioCollection];
    }
    return dependentesFuncionarioCollection;
  }
}
