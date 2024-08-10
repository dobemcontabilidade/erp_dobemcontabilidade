import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IDepartamentoContador, NewDepartamentoContador } from '../departamento-contador.model';

export type PartialUpdateDepartamentoContador = Partial<IDepartamentoContador> & Pick<IDepartamentoContador, 'id'>;

export type EntityResponseType = HttpResponse<IDepartamentoContador>;
export type EntityArrayResponseType = HttpResponse<IDepartamentoContador[]>;

@Injectable({ providedIn: 'root' })
export class DepartamentoContadorService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/departamento-contadors');

  create(departamentoContador: NewDepartamentoContador): Observable<EntityResponseType> {
    return this.http.post<IDepartamentoContador>(this.resourceUrl, departamentoContador, { observe: 'response' });
  }

  update(departamentoContador: IDepartamentoContador): Observable<EntityResponseType> {
    return this.http.put<IDepartamentoContador>(
      `${this.resourceUrl}/${this.getDepartamentoContadorIdentifier(departamentoContador)}`,
      departamentoContador,
      { observe: 'response' },
    );
  }

  partialUpdate(departamentoContador: PartialUpdateDepartamentoContador): Observable<EntityResponseType> {
    return this.http.patch<IDepartamentoContador>(
      `${this.resourceUrl}/${this.getDepartamentoContadorIdentifier(departamentoContador)}`,
      departamentoContador,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDepartamentoContador>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDepartamentoContador[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getDepartamentoContadorIdentifier(departamentoContador: Pick<IDepartamentoContador, 'id'>): number {
    return departamentoContador.id;
  }

  compareDepartamentoContador(o1: Pick<IDepartamentoContador, 'id'> | null, o2: Pick<IDepartamentoContador, 'id'> | null): boolean {
    return o1 && o2 ? this.getDepartamentoContadorIdentifier(o1) === this.getDepartamentoContadorIdentifier(o2) : o1 === o2;
  }

  addDepartamentoContadorToCollectionIfMissing<Type extends Pick<IDepartamentoContador, 'id'>>(
    departamentoContadorCollection: Type[],
    ...departamentoContadorsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const departamentoContadors: Type[] = departamentoContadorsToCheck.filter(isPresent);
    if (departamentoContadors.length > 0) {
      const departamentoContadorCollectionIdentifiers = departamentoContadorCollection.map(departamentoContadorItem =>
        this.getDepartamentoContadorIdentifier(departamentoContadorItem),
      );
      const departamentoContadorsToAdd = departamentoContadors.filter(departamentoContadorItem => {
        const departamentoContadorIdentifier = this.getDepartamentoContadorIdentifier(departamentoContadorItem);
        if (departamentoContadorCollectionIdentifiers.includes(departamentoContadorIdentifier)) {
          return false;
        }
        departamentoContadorCollectionIdentifiers.push(departamentoContadorIdentifier);
        return true;
      });
      return [...departamentoContadorsToAdd, ...departamentoContadorCollection];
    }
    return departamentoContadorCollection;
  }
}
