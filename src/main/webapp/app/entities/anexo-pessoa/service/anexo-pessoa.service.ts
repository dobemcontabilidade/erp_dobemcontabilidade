import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAnexoPessoa, NewAnexoPessoa } from '../anexo-pessoa.model';

export type PartialUpdateAnexoPessoa = Partial<IAnexoPessoa> & Pick<IAnexoPessoa, 'id'>;

export type EntityResponseType = HttpResponse<IAnexoPessoa>;
export type EntityArrayResponseType = HttpResponse<IAnexoPessoa[]>;

@Injectable({ providedIn: 'root' })
export class AnexoPessoaService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/anexo-pessoas');

  create(anexoPessoa: NewAnexoPessoa): Observable<EntityResponseType> {
    return this.http.post<IAnexoPessoa>(this.resourceUrl, anexoPessoa, { observe: 'response' });
  }

  update(anexoPessoa: IAnexoPessoa): Observable<EntityResponseType> {
    return this.http.put<IAnexoPessoa>(`${this.resourceUrl}/${this.getAnexoPessoaIdentifier(anexoPessoa)}`, anexoPessoa, {
      observe: 'response',
    });
  }

  partialUpdate(anexoPessoa: PartialUpdateAnexoPessoa): Observable<EntityResponseType> {
    return this.http.patch<IAnexoPessoa>(`${this.resourceUrl}/${this.getAnexoPessoaIdentifier(anexoPessoa)}`, anexoPessoa, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAnexoPessoa>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAnexoPessoa[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAnexoPessoaIdentifier(anexoPessoa: Pick<IAnexoPessoa, 'id'>): number {
    return anexoPessoa.id;
  }

  compareAnexoPessoa(o1: Pick<IAnexoPessoa, 'id'> | null, o2: Pick<IAnexoPessoa, 'id'> | null): boolean {
    return o1 && o2 ? this.getAnexoPessoaIdentifier(o1) === this.getAnexoPessoaIdentifier(o2) : o1 === o2;
  }

  addAnexoPessoaToCollectionIfMissing<Type extends Pick<IAnexoPessoa, 'id'>>(
    anexoPessoaCollection: Type[],
    ...anexoPessoasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const anexoPessoas: Type[] = anexoPessoasToCheck.filter(isPresent);
    if (anexoPessoas.length > 0) {
      const anexoPessoaCollectionIdentifiers = anexoPessoaCollection.map(anexoPessoaItem => this.getAnexoPessoaIdentifier(anexoPessoaItem));
      const anexoPessoasToAdd = anexoPessoas.filter(anexoPessoaItem => {
        const anexoPessoaIdentifier = this.getAnexoPessoaIdentifier(anexoPessoaItem);
        if (anexoPessoaCollectionIdentifiers.includes(anexoPessoaIdentifier)) {
          return false;
        }
        anexoPessoaCollectionIdentifiers.push(anexoPessoaIdentifier);
        return true;
      });
      return [...anexoPessoasToAdd, ...anexoPessoaCollection];
    }
    return anexoPessoaCollection;
  }
}
