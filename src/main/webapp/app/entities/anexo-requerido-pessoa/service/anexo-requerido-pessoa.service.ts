import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAnexoRequeridoPessoa, NewAnexoRequeridoPessoa } from '../anexo-requerido-pessoa.model';

export type PartialUpdateAnexoRequeridoPessoa = Partial<IAnexoRequeridoPessoa> & Pick<IAnexoRequeridoPessoa, 'id'>;

export type EntityResponseType = HttpResponse<IAnexoRequeridoPessoa>;
export type EntityArrayResponseType = HttpResponse<IAnexoRequeridoPessoa[]>;

@Injectable({ providedIn: 'root' })
export class AnexoRequeridoPessoaService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/anexo-requerido-pessoas');

  create(anexoRequeridoPessoa: NewAnexoRequeridoPessoa): Observable<EntityResponseType> {
    return this.http.post<IAnexoRequeridoPessoa>(this.resourceUrl, anexoRequeridoPessoa, { observe: 'response' });
  }

  update(anexoRequeridoPessoa: IAnexoRequeridoPessoa): Observable<EntityResponseType> {
    return this.http.put<IAnexoRequeridoPessoa>(
      `${this.resourceUrl}/${this.getAnexoRequeridoPessoaIdentifier(anexoRequeridoPessoa)}`,
      anexoRequeridoPessoa,
      { observe: 'response' },
    );
  }

  partialUpdate(anexoRequeridoPessoa: PartialUpdateAnexoRequeridoPessoa): Observable<EntityResponseType> {
    return this.http.patch<IAnexoRequeridoPessoa>(
      `${this.resourceUrl}/${this.getAnexoRequeridoPessoaIdentifier(anexoRequeridoPessoa)}`,
      anexoRequeridoPessoa,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAnexoRequeridoPessoa>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAnexoRequeridoPessoa[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getAnexoRequeridoPessoaIdentifier(anexoRequeridoPessoa: Pick<IAnexoRequeridoPessoa, 'id'>): number {
    return anexoRequeridoPessoa.id;
  }

  compareAnexoRequeridoPessoa(o1: Pick<IAnexoRequeridoPessoa, 'id'> | null, o2: Pick<IAnexoRequeridoPessoa, 'id'> | null): boolean {
    return o1 && o2 ? this.getAnexoRequeridoPessoaIdentifier(o1) === this.getAnexoRequeridoPessoaIdentifier(o2) : o1 === o2;
  }

  addAnexoRequeridoPessoaToCollectionIfMissing<Type extends Pick<IAnexoRequeridoPessoa, 'id'>>(
    anexoRequeridoPessoaCollection: Type[],
    ...anexoRequeridoPessoasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const anexoRequeridoPessoas: Type[] = anexoRequeridoPessoasToCheck.filter(isPresent);
    if (anexoRequeridoPessoas.length > 0) {
      const anexoRequeridoPessoaCollectionIdentifiers = anexoRequeridoPessoaCollection.map(anexoRequeridoPessoaItem =>
        this.getAnexoRequeridoPessoaIdentifier(anexoRequeridoPessoaItem),
      );
      const anexoRequeridoPessoasToAdd = anexoRequeridoPessoas.filter(anexoRequeridoPessoaItem => {
        const anexoRequeridoPessoaIdentifier = this.getAnexoRequeridoPessoaIdentifier(anexoRequeridoPessoaItem);
        if (anexoRequeridoPessoaCollectionIdentifiers.includes(anexoRequeridoPessoaIdentifier)) {
          return false;
        }
        anexoRequeridoPessoaCollectionIdentifiers.push(anexoRequeridoPessoaIdentifier);
        return true;
      });
      return [...anexoRequeridoPessoasToAdd, ...anexoRequeridoPessoaCollection];
    }
    return anexoRequeridoPessoaCollection;
  }
}
