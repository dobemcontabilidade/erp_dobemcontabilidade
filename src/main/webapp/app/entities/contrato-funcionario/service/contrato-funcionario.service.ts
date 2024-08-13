import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IContratoFuncionario, NewContratoFuncionario } from '../contrato-funcionario.model';

export type PartialUpdateContratoFuncionario = Partial<IContratoFuncionario> & Pick<IContratoFuncionario, 'id'>;

export type EntityResponseType = HttpResponse<IContratoFuncionario>;
export type EntityArrayResponseType = HttpResponse<IContratoFuncionario[]>;

@Injectable({ providedIn: 'root' })
export class ContratoFuncionarioService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/contrato-funcionarios');

  create(contratoFuncionario: NewContratoFuncionario): Observable<EntityResponseType> {
    return this.http.post<IContratoFuncionario>(this.resourceUrl, contratoFuncionario, { observe: 'response' });
  }

  update(contratoFuncionario: IContratoFuncionario): Observable<EntityResponseType> {
    return this.http.put<IContratoFuncionario>(
      `${this.resourceUrl}/${this.getContratoFuncionarioIdentifier(contratoFuncionario)}`,
      contratoFuncionario,
      { observe: 'response' },
    );
  }

  partialUpdate(contratoFuncionario: PartialUpdateContratoFuncionario): Observable<EntityResponseType> {
    return this.http.patch<IContratoFuncionario>(
      `${this.resourceUrl}/${this.getContratoFuncionarioIdentifier(contratoFuncionario)}`,
      contratoFuncionario,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IContratoFuncionario>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IContratoFuncionario[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getContratoFuncionarioIdentifier(contratoFuncionario: Pick<IContratoFuncionario, 'id'>): number {
    return contratoFuncionario.id;
  }

  compareContratoFuncionario(o1: Pick<IContratoFuncionario, 'id'> | null, o2: Pick<IContratoFuncionario, 'id'> | null): boolean {
    return o1 && o2 ? this.getContratoFuncionarioIdentifier(o1) === this.getContratoFuncionarioIdentifier(o2) : o1 === o2;
  }

  addContratoFuncionarioToCollectionIfMissing<Type extends Pick<IContratoFuncionario, 'id'>>(
    contratoFuncionarioCollection: Type[],
    ...contratoFuncionariosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const contratoFuncionarios: Type[] = contratoFuncionariosToCheck.filter(isPresent);
    if (contratoFuncionarios.length > 0) {
      const contratoFuncionarioCollectionIdentifiers = contratoFuncionarioCollection.map(contratoFuncionarioItem =>
        this.getContratoFuncionarioIdentifier(contratoFuncionarioItem),
      );
      const contratoFuncionariosToAdd = contratoFuncionarios.filter(contratoFuncionarioItem => {
        const contratoFuncionarioIdentifier = this.getContratoFuncionarioIdentifier(contratoFuncionarioItem);
        if (contratoFuncionarioCollectionIdentifiers.includes(contratoFuncionarioIdentifier)) {
          return false;
        }
        contratoFuncionarioCollectionIdentifiers.push(contratoFuncionarioIdentifier);
        return true;
      });
      return [...contratoFuncionariosToAdd, ...contratoFuncionarioCollection];
    }
    return contratoFuncionarioCollection;
  }
}
