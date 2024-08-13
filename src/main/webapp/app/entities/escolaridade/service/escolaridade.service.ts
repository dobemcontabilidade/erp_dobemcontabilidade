import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEscolaridade, NewEscolaridade } from '../escolaridade.model';

export type PartialUpdateEscolaridade = Partial<IEscolaridade> & Pick<IEscolaridade, 'id'>;

export type EntityResponseType = HttpResponse<IEscolaridade>;
export type EntityArrayResponseType = HttpResponse<IEscolaridade[]>;

@Injectable({ providedIn: 'root' })
export class EscolaridadeService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/escolaridades');

  create(escolaridade: NewEscolaridade): Observable<EntityResponseType> {
    return this.http.post<IEscolaridade>(this.resourceUrl, escolaridade, { observe: 'response' });
  }

  update(escolaridade: IEscolaridade): Observable<EntityResponseType> {
    return this.http.put<IEscolaridade>(`${this.resourceUrl}/${this.getEscolaridadeIdentifier(escolaridade)}`, escolaridade, {
      observe: 'response',
    });
  }

  partialUpdate(escolaridade: PartialUpdateEscolaridade): Observable<EntityResponseType> {
    return this.http.patch<IEscolaridade>(`${this.resourceUrl}/${this.getEscolaridadeIdentifier(escolaridade)}`, escolaridade, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEscolaridade>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEscolaridade[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getEscolaridadeIdentifier(escolaridade: Pick<IEscolaridade, 'id'>): number {
    return escolaridade.id;
  }

  compareEscolaridade(o1: Pick<IEscolaridade, 'id'> | null, o2: Pick<IEscolaridade, 'id'> | null): boolean {
    return o1 && o2 ? this.getEscolaridadeIdentifier(o1) === this.getEscolaridadeIdentifier(o2) : o1 === o2;
  }

  addEscolaridadeToCollectionIfMissing<Type extends Pick<IEscolaridade, 'id'>>(
    escolaridadeCollection: Type[],
    ...escolaridadesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const escolaridades: Type[] = escolaridadesToCheck.filter(isPresent);
    if (escolaridades.length > 0) {
      const escolaridadeCollectionIdentifiers = escolaridadeCollection.map(escolaridadeItem =>
        this.getEscolaridadeIdentifier(escolaridadeItem),
      );
      const escolaridadesToAdd = escolaridades.filter(escolaridadeItem => {
        const escolaridadeIdentifier = this.getEscolaridadeIdentifier(escolaridadeItem);
        if (escolaridadeCollectionIdentifiers.includes(escolaridadeIdentifier)) {
          return false;
        }
        escolaridadeCollectionIdentifiers.push(escolaridadeIdentifier);
        return true;
      });
      return [...escolaridadesToAdd, ...escolaridadeCollection];
    }
    return escolaridadeCollection;
  }
}
