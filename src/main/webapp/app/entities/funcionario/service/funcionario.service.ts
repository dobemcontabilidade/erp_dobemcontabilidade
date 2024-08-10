import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IFuncionario, NewFuncionario } from '../funcionario.model';

export type PartialUpdateFuncionario = Partial<IFuncionario> & Pick<IFuncionario, 'id'>;

export type EntityResponseType = HttpResponse<IFuncionario>;
export type EntityArrayResponseType = HttpResponse<IFuncionario[]>;

@Injectable({ providedIn: 'root' })
export class FuncionarioService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/funcionarios');

  create(funcionario: NewFuncionario): Observable<EntityResponseType> {
    return this.http.post<IFuncionario>(this.resourceUrl, funcionario, { observe: 'response' });
  }

  update(funcionario: IFuncionario): Observable<EntityResponseType> {
    return this.http.put<IFuncionario>(`${this.resourceUrl}/${this.getFuncionarioIdentifier(funcionario)}`, funcionario, {
      observe: 'response',
    });
  }

  partialUpdate(funcionario: PartialUpdateFuncionario): Observable<EntityResponseType> {
    return this.http.patch<IFuncionario>(`${this.resourceUrl}/${this.getFuncionarioIdentifier(funcionario)}`, funcionario, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFuncionario>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFuncionario[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getFuncionarioIdentifier(funcionario: Pick<IFuncionario, 'id'>): number {
    return funcionario.id;
  }

  compareFuncionario(o1: Pick<IFuncionario, 'id'> | null, o2: Pick<IFuncionario, 'id'> | null): boolean {
    return o1 && o2 ? this.getFuncionarioIdentifier(o1) === this.getFuncionarioIdentifier(o2) : o1 === o2;
  }

  addFuncionarioToCollectionIfMissing<Type extends Pick<IFuncionario, 'id'>>(
    funcionarioCollection: Type[],
    ...funcionariosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const funcionarios: Type[] = funcionariosToCheck.filter(isPresent);
    if (funcionarios.length > 0) {
      const funcionarioCollectionIdentifiers = funcionarioCollection.map(funcionarioItem => this.getFuncionarioIdentifier(funcionarioItem));
      const funcionariosToAdd = funcionarios.filter(funcionarioItem => {
        const funcionarioIdentifier = this.getFuncionarioIdentifier(funcionarioItem);
        if (funcionarioCollectionIdentifiers.includes(funcionarioIdentifier)) {
          return false;
        }
        funcionarioCollectionIdentifiers.push(funcionarioIdentifier);
        return true;
      });
      return [...funcionariosToAdd, ...funcionarioCollection];
    }
    return funcionarioCollection;
  }
}
