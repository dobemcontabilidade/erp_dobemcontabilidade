import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAtorAvaliado, NewAtorAvaliado } from '../ator-avaliado.model';

export type PartialUpdateAtorAvaliado = Partial<IAtorAvaliado> & Pick<IAtorAvaliado, 'id'>;

export type EntityResponseType = HttpResponse<IAtorAvaliado>;
export type EntityArrayResponseType = HttpResponse<IAtorAvaliado[]>;

@Injectable({ providedIn: 'root' })
export class AtorAvaliadoService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/ator-avaliados');

  create(atorAvaliado: NewAtorAvaliado): Observable<EntityResponseType> {
    return this.http.post<IAtorAvaliado>(this.resourceUrl, atorAvaliado, { observe: 'response' });
  }

  update(atorAvaliado: IAtorAvaliado): Observable<EntityResponseType> {
    return this.http.put<IAtorAvaliado>(`${this.resourceUrl}/${this.getAtorAvaliadoIdentifier(atorAvaliado)}`, atorAvaliado, {
      observe: 'response',
    });
  }

  partialUpdate(atorAvaliado: PartialUpdateAtorAvaliado): Observable<EntityResponseType> {
    return this.http.patch<IAtorAvaliado>(`${this.resourceUrl}/${this.getAtorAvaliadoIdentifier(atorAvaliado)}`, atorAvaliado, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAtorAvaliado>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAtorAvaliado[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAtorAvaliadoIdentifier(atorAvaliado: Pick<IAtorAvaliado, 'id'>): number {
    return atorAvaliado.id;
  }

  compareAtorAvaliado(o1: Pick<IAtorAvaliado, 'id'> | null, o2: Pick<IAtorAvaliado, 'id'> | null): boolean {
    return o1 && o2 ? this.getAtorAvaliadoIdentifier(o1) === this.getAtorAvaliadoIdentifier(o2) : o1 === o2;
  }

  addAtorAvaliadoToCollectionIfMissing<Type extends Pick<IAtorAvaliado, 'id'>>(
    atorAvaliadoCollection: Type[],
    ...atorAvaliadosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const atorAvaliados: Type[] = atorAvaliadosToCheck.filter(isPresent);
    if (atorAvaliados.length > 0) {
      const atorAvaliadoCollectionIdentifiers = atorAvaliadoCollection.map(atorAvaliadoItem =>
        this.getAtorAvaliadoIdentifier(atorAvaliadoItem),
      );
      const atorAvaliadosToAdd = atorAvaliados.filter(atorAvaliadoItem => {
        const atorAvaliadoIdentifier = this.getAtorAvaliadoIdentifier(atorAvaliadoItem);
        if (atorAvaliadoCollectionIdentifiers.includes(atorAvaliadoIdentifier)) {
          return false;
        }
        atorAvaliadoCollectionIdentifiers.push(atorAvaliadoIdentifier);
        return true;
      });
      return [...atorAvaliadosToAdd, ...atorAvaliadoCollection];
    }
    return atorAvaliadoCollection;
  }
}
