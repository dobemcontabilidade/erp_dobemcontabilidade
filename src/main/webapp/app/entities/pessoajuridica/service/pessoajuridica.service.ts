import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPessoajuridica, NewPessoajuridica } from '../pessoajuridica.model';

export type PartialUpdatePessoajuridica = Partial<IPessoajuridica> & Pick<IPessoajuridica, 'id'>;

export type EntityResponseType = HttpResponse<IPessoajuridica>;
export type EntityArrayResponseType = HttpResponse<IPessoajuridica[]>;

@Injectable({ providedIn: 'root' })
export class PessoajuridicaService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/pessoajuridicas');

  create(pessoajuridica: NewPessoajuridica): Observable<EntityResponseType> {
    return this.http.post<IPessoajuridica>(this.resourceUrl, pessoajuridica, { observe: 'response' });
  }

  update(pessoajuridica: IPessoajuridica): Observable<EntityResponseType> {
    return this.http.put<IPessoajuridica>(`${this.resourceUrl}/${this.getPessoajuridicaIdentifier(pessoajuridica)}`, pessoajuridica, {
      observe: 'response',
    });
  }

  partialUpdate(pessoajuridica: PartialUpdatePessoajuridica): Observable<EntityResponseType> {
    return this.http.patch<IPessoajuridica>(`${this.resourceUrl}/${this.getPessoajuridicaIdentifier(pessoajuridica)}`, pessoajuridica, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IPessoajuridica>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IPessoajuridica[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPessoajuridicaIdentifier(pessoajuridica: Pick<IPessoajuridica, 'id'>): number {
    return pessoajuridica.id;
  }

  comparePessoajuridica(o1: Pick<IPessoajuridica, 'id'> | null, o2: Pick<IPessoajuridica, 'id'> | null): boolean {
    return o1 && o2 ? this.getPessoajuridicaIdentifier(o1) === this.getPessoajuridicaIdentifier(o2) : o1 === o2;
  }

  addPessoajuridicaToCollectionIfMissing<Type extends Pick<IPessoajuridica, 'id'>>(
    pessoajuridicaCollection: Type[],
    ...pessoajuridicasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const pessoajuridicas: Type[] = pessoajuridicasToCheck.filter(isPresent);
    if (pessoajuridicas.length > 0) {
      const pessoajuridicaCollectionIdentifiers = pessoajuridicaCollection.map(pessoajuridicaItem =>
        this.getPessoajuridicaIdentifier(pessoajuridicaItem),
      );
      const pessoajuridicasToAdd = pessoajuridicas.filter(pessoajuridicaItem => {
        const pessoajuridicaIdentifier = this.getPessoajuridicaIdentifier(pessoajuridicaItem);
        if (pessoajuridicaCollectionIdentifiers.includes(pessoajuridicaIdentifier)) {
          return false;
        }
        pessoajuridicaCollectionIdentifiers.push(pessoajuridicaIdentifier);
        return true;
      });
      return [...pessoajuridicasToAdd, ...pessoajuridicaCollection];
    }
    return pessoajuridicaCollection;
  }
}
