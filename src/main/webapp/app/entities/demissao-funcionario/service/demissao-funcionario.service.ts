import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDemissaoFuncionario, NewDemissaoFuncionario } from '../demissao-funcionario.model';

export type PartialUpdateDemissaoFuncionario = Partial<IDemissaoFuncionario> & Pick<IDemissaoFuncionario, 'id'>;

export type EntityResponseType = HttpResponse<IDemissaoFuncionario>;
export type EntityArrayResponseType = HttpResponse<IDemissaoFuncionario[]>;

@Injectable({ providedIn: 'root' })
export class DemissaoFuncionarioService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/demissao-funcionarios');

  create(demissaoFuncionario: NewDemissaoFuncionario): Observable<EntityResponseType> {
    return this.http.post<IDemissaoFuncionario>(this.resourceUrl, demissaoFuncionario, { observe: 'response' });
  }

  update(demissaoFuncionario: IDemissaoFuncionario): Observable<EntityResponseType> {
    return this.http.put<IDemissaoFuncionario>(
      `${this.resourceUrl}/${this.getDemissaoFuncionarioIdentifier(demissaoFuncionario)}`,
      demissaoFuncionario,
      { observe: 'response' },
    );
  }

  partialUpdate(demissaoFuncionario: PartialUpdateDemissaoFuncionario): Observable<EntityResponseType> {
    return this.http.patch<IDemissaoFuncionario>(
      `${this.resourceUrl}/${this.getDemissaoFuncionarioIdentifier(demissaoFuncionario)}`,
      demissaoFuncionario,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDemissaoFuncionario>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDemissaoFuncionario[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getDemissaoFuncionarioIdentifier(demissaoFuncionario: Pick<IDemissaoFuncionario, 'id'>): number {
    return demissaoFuncionario.id;
  }

  compareDemissaoFuncionario(o1: Pick<IDemissaoFuncionario, 'id'> | null, o2: Pick<IDemissaoFuncionario, 'id'> | null): boolean {
    return o1 && o2 ? this.getDemissaoFuncionarioIdentifier(o1) === this.getDemissaoFuncionarioIdentifier(o2) : o1 === o2;
  }

  addDemissaoFuncionarioToCollectionIfMissing<Type extends Pick<IDemissaoFuncionario, 'id'>>(
    demissaoFuncionarioCollection: Type[],
    ...demissaoFuncionariosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const demissaoFuncionarios: Type[] = demissaoFuncionariosToCheck.filter(isPresent);
    if (demissaoFuncionarios.length > 0) {
      const demissaoFuncionarioCollectionIdentifiers = demissaoFuncionarioCollection.map(demissaoFuncionarioItem =>
        this.getDemissaoFuncionarioIdentifier(demissaoFuncionarioItem),
      );
      const demissaoFuncionariosToAdd = demissaoFuncionarios.filter(demissaoFuncionarioItem => {
        const demissaoFuncionarioIdentifier = this.getDemissaoFuncionarioIdentifier(demissaoFuncionarioItem);
        if (demissaoFuncionarioCollectionIdentifiers.includes(demissaoFuncionarioIdentifier)) {
          return false;
        }
        demissaoFuncionarioCollectionIdentifiers.push(demissaoFuncionarioIdentifier);
        return true;
      });
      return [...demissaoFuncionariosToAdd, ...demissaoFuncionarioCollection];
    }
    return demissaoFuncionarioCollection;
  }
}
