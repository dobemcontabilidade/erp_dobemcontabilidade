import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IBancoPessoa, NewBancoPessoa } from '../banco-pessoa.model';

export type PartialUpdateBancoPessoa = Partial<IBancoPessoa> & Pick<IBancoPessoa, 'id'>;

export type EntityResponseType = HttpResponse<IBancoPessoa>;
export type EntityArrayResponseType = HttpResponse<IBancoPessoa[]>;

@Injectable({ providedIn: 'root' })
export class BancoPessoaService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/banco-pessoas');

  create(bancoPessoa: NewBancoPessoa): Observable<EntityResponseType> {
    return this.http.post<IBancoPessoa>(this.resourceUrl, bancoPessoa, { observe: 'response' });
  }

  update(bancoPessoa: IBancoPessoa): Observable<EntityResponseType> {
    return this.http.put<IBancoPessoa>(`${this.resourceUrl}/${this.getBancoPessoaIdentifier(bancoPessoa)}`, bancoPessoa, {
      observe: 'response',
    });
  }

  partialUpdate(bancoPessoa: PartialUpdateBancoPessoa): Observable<EntityResponseType> {
    return this.http.patch<IBancoPessoa>(`${this.resourceUrl}/${this.getBancoPessoaIdentifier(bancoPessoa)}`, bancoPessoa, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBancoPessoa>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBancoPessoa[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getBancoPessoaIdentifier(bancoPessoa: Pick<IBancoPessoa, 'id'>): number {
    return bancoPessoa.id;
  }

  compareBancoPessoa(o1: Pick<IBancoPessoa, 'id'> | null, o2: Pick<IBancoPessoa, 'id'> | null): boolean {
    return o1 && o2 ? this.getBancoPessoaIdentifier(o1) === this.getBancoPessoaIdentifier(o2) : o1 === o2;
  }

  addBancoPessoaToCollectionIfMissing<Type extends Pick<IBancoPessoa, 'id'>>(
    bancoPessoaCollection: Type[],
    ...bancoPessoasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const bancoPessoas: Type[] = bancoPessoasToCheck.filter(isPresent);
    if (bancoPessoas.length > 0) {
      const bancoPessoaCollectionIdentifiers = bancoPessoaCollection.map(bancoPessoaItem => this.getBancoPessoaIdentifier(bancoPessoaItem));
      const bancoPessoasToAdd = bancoPessoas.filter(bancoPessoaItem => {
        const bancoPessoaIdentifier = this.getBancoPessoaIdentifier(bancoPessoaItem);
        if (bancoPessoaCollectionIdentifiers.includes(bancoPessoaIdentifier)) {
          return false;
        }
        bancoPessoaCollectionIdentifiers.push(bancoPessoaIdentifier);
        return true;
      });
      return [...bancoPessoasToAdd, ...bancoPessoaCollection];
    }
    return bancoPessoaCollection;
  }
}
