import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEnderecoPessoa, NewEnderecoPessoa } from '../endereco-pessoa.model';

export type PartialUpdateEnderecoPessoa = Partial<IEnderecoPessoa> & Pick<IEnderecoPessoa, 'id'>;

export type EntityResponseType = HttpResponse<IEnderecoPessoa>;
export type EntityArrayResponseType = HttpResponse<IEnderecoPessoa[]>;

@Injectable({ providedIn: 'root' })
export class EnderecoPessoaService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/endereco-pessoas');

  create(enderecoPessoa: NewEnderecoPessoa): Observable<EntityResponseType> {
    return this.http.post<IEnderecoPessoa>(this.resourceUrl, enderecoPessoa, { observe: 'response' });
  }

  update(enderecoPessoa: IEnderecoPessoa): Observable<EntityResponseType> {
    return this.http.put<IEnderecoPessoa>(`${this.resourceUrl}/${this.getEnderecoPessoaIdentifier(enderecoPessoa)}`, enderecoPessoa, {
      observe: 'response',
    });
  }

  partialUpdate(enderecoPessoa: PartialUpdateEnderecoPessoa): Observable<EntityResponseType> {
    return this.http.patch<IEnderecoPessoa>(`${this.resourceUrl}/${this.getEnderecoPessoaIdentifier(enderecoPessoa)}`, enderecoPessoa, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEnderecoPessoa>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEnderecoPessoa[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getEnderecoPessoaIdentifier(enderecoPessoa: Pick<IEnderecoPessoa, 'id'>): number {
    return enderecoPessoa.id;
  }

  compareEnderecoPessoa(o1: Pick<IEnderecoPessoa, 'id'> | null, o2: Pick<IEnderecoPessoa, 'id'> | null): boolean {
    return o1 && o2 ? this.getEnderecoPessoaIdentifier(o1) === this.getEnderecoPessoaIdentifier(o2) : o1 === o2;
  }

  addEnderecoPessoaToCollectionIfMissing<Type extends Pick<IEnderecoPessoa, 'id'>>(
    enderecoPessoaCollection: Type[],
    ...enderecoPessoasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const enderecoPessoas: Type[] = enderecoPessoasToCheck.filter(isPresent);
    if (enderecoPessoas.length > 0) {
      const enderecoPessoaCollectionIdentifiers = enderecoPessoaCollection.map(enderecoPessoaItem =>
        this.getEnderecoPessoaIdentifier(enderecoPessoaItem),
      );
      const enderecoPessoasToAdd = enderecoPessoas.filter(enderecoPessoaItem => {
        const enderecoPessoaIdentifier = this.getEnderecoPessoaIdentifier(enderecoPessoaItem);
        if (enderecoPessoaCollectionIdentifiers.includes(enderecoPessoaIdentifier)) {
          return false;
        }
        enderecoPessoaCollectionIdentifiers.push(enderecoPessoaIdentifier);
        return true;
      });
      return [...enderecoPessoasToAdd, ...enderecoPessoaCollection];
    }
    return enderecoPessoaCollection;
  }
}
