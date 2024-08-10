import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDepartamentoFuncionario, NewDepartamentoFuncionario } from '../departamento-funcionario.model';

export type PartialUpdateDepartamentoFuncionario = Partial<IDepartamentoFuncionario> & Pick<IDepartamentoFuncionario, 'id'>;

export type EntityResponseType = HttpResponse<IDepartamentoFuncionario>;
export type EntityArrayResponseType = HttpResponse<IDepartamentoFuncionario[]>;

@Injectable({ providedIn: 'root' })
export class DepartamentoFuncionarioService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/departamento-funcionarios');

  create(departamentoFuncionario: NewDepartamentoFuncionario): Observable<EntityResponseType> {
    return this.http.post<IDepartamentoFuncionario>(this.resourceUrl, departamentoFuncionario, { observe: 'response' });
  }

  update(departamentoFuncionario: IDepartamentoFuncionario): Observable<EntityResponseType> {
    return this.http.put<IDepartamentoFuncionario>(
      `${this.resourceUrl}/${this.getDepartamentoFuncionarioIdentifier(departamentoFuncionario)}`,
      departamentoFuncionario,
      { observe: 'response' },
    );
  }

  partialUpdate(departamentoFuncionario: PartialUpdateDepartamentoFuncionario): Observable<EntityResponseType> {
    return this.http.patch<IDepartamentoFuncionario>(
      `${this.resourceUrl}/${this.getDepartamentoFuncionarioIdentifier(departamentoFuncionario)}`,
      departamentoFuncionario,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDepartamentoFuncionario>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDepartamentoFuncionario[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getDepartamentoFuncionarioIdentifier(departamentoFuncionario: Pick<IDepartamentoFuncionario, 'id'>): number {
    return departamentoFuncionario.id;
  }

  compareDepartamentoFuncionario(
    o1: Pick<IDepartamentoFuncionario, 'id'> | null,
    o2: Pick<IDepartamentoFuncionario, 'id'> | null,
  ): boolean {
    return o1 && o2 ? this.getDepartamentoFuncionarioIdentifier(o1) === this.getDepartamentoFuncionarioIdentifier(o2) : o1 === o2;
  }

  addDepartamentoFuncionarioToCollectionIfMissing<Type extends Pick<IDepartamentoFuncionario, 'id'>>(
    departamentoFuncionarioCollection: Type[],
    ...departamentoFuncionariosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const departamentoFuncionarios: Type[] = departamentoFuncionariosToCheck.filter(isPresent);
    if (departamentoFuncionarios.length > 0) {
      const departamentoFuncionarioCollectionIdentifiers = departamentoFuncionarioCollection.map(departamentoFuncionarioItem =>
        this.getDepartamentoFuncionarioIdentifier(departamentoFuncionarioItem),
      );
      const departamentoFuncionariosToAdd = departamentoFuncionarios.filter(departamentoFuncionarioItem => {
        const departamentoFuncionarioIdentifier = this.getDepartamentoFuncionarioIdentifier(departamentoFuncionarioItem);
        if (departamentoFuncionarioCollectionIdentifiers.includes(departamentoFuncionarioIdentifier)) {
          return false;
        }
        departamentoFuncionarioCollectionIdentifiers.push(departamentoFuncionarioIdentifier);
        return true;
      });
      return [...departamentoFuncionariosToAdd, ...departamentoFuncionarioCollection];
    }
    return departamentoFuncionarioCollection;
  }
}
