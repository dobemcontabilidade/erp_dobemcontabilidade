import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPerfilContadorDepartamento, NewPerfilContadorDepartamento } from '../perfil-contador-departamento.model';

export type PartialUpdatePerfilContadorDepartamento = Partial<IPerfilContadorDepartamento> & Pick<IPerfilContadorDepartamento, 'id'>;

export type EntityResponseType = HttpResponse<IPerfilContadorDepartamento>;
export type EntityArrayResponseType = HttpResponse<IPerfilContadorDepartamento[]>;

@Injectable({ providedIn: 'root' })
export class PerfilContadorDepartamentoService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/perfil-contador-departamentos');

  create(perfilContadorDepartamento: NewPerfilContadorDepartamento): Observable<EntityResponseType> {
    return this.http.post<IPerfilContadorDepartamento>(this.resourceUrl, perfilContadorDepartamento, { observe: 'response' });
  }

  update(perfilContadorDepartamento: IPerfilContadorDepartamento): Observable<EntityResponseType> {
    return this.http.put<IPerfilContadorDepartamento>(
      `${this.resourceUrl}/${this.getPerfilContadorDepartamentoIdentifier(perfilContadorDepartamento)}`,
      perfilContadorDepartamento,
      { observe: 'response' },
    );
  }

  partialUpdate(perfilContadorDepartamento: PartialUpdatePerfilContadorDepartamento): Observable<EntityResponseType> {
    return this.http.patch<IPerfilContadorDepartamento>(
      `${this.resourceUrl}/${this.getPerfilContadorDepartamentoIdentifier(perfilContadorDepartamento)}`,
      perfilContadorDepartamento,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPerfilContadorDepartamento>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPerfilContadorDepartamento[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPerfilContadorDepartamentoIdentifier(perfilContadorDepartamento: Pick<IPerfilContadorDepartamento, 'id'>): number {
    return perfilContadorDepartamento.id;
  }

  comparePerfilContadorDepartamento(
    o1: Pick<IPerfilContadorDepartamento, 'id'> | null,
    o2: Pick<IPerfilContadorDepartamento, 'id'> | null,
  ): boolean {
    return o1 && o2 ? this.getPerfilContadorDepartamentoIdentifier(o1) === this.getPerfilContadorDepartamentoIdentifier(o2) : o1 === o2;
  }

  addPerfilContadorDepartamentoToCollectionIfMissing<Type extends Pick<IPerfilContadorDepartamento, 'id'>>(
    perfilContadorDepartamentoCollection: Type[],
    ...perfilContadorDepartamentosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const perfilContadorDepartamentos: Type[] = perfilContadorDepartamentosToCheck.filter(isPresent);
    if (perfilContadorDepartamentos.length > 0) {
      const perfilContadorDepartamentoCollectionIdentifiers = perfilContadorDepartamentoCollection.map(perfilContadorDepartamentoItem =>
        this.getPerfilContadorDepartamentoIdentifier(perfilContadorDepartamentoItem),
      );
      const perfilContadorDepartamentosToAdd = perfilContadorDepartamentos.filter(perfilContadorDepartamentoItem => {
        const perfilContadorDepartamentoIdentifier = this.getPerfilContadorDepartamentoIdentifier(perfilContadorDepartamentoItem);
        if (perfilContadorDepartamentoCollectionIdentifiers.includes(perfilContadorDepartamentoIdentifier)) {
          return false;
        }
        perfilContadorDepartamentoCollectionIdentifiers.push(perfilContadorDepartamentoIdentifier);
        return true;
      });
      return [...perfilContadorDepartamentosToAdd, ...perfilContadorDepartamentoCollection];
    }
    return perfilContadorDepartamentoCollection;
  }
}
