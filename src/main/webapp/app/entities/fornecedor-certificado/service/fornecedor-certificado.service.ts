import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IFornecedorCertificado, NewFornecedorCertificado } from '../fornecedor-certificado.model';

export type PartialUpdateFornecedorCertificado = Partial<IFornecedorCertificado> & Pick<IFornecedorCertificado, 'id'>;

export type EntityResponseType = HttpResponse<IFornecedorCertificado>;
export type EntityArrayResponseType = HttpResponse<IFornecedorCertificado[]>;

@Injectable({ providedIn: 'root' })
export class FornecedorCertificadoService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/fornecedor-certificados');

  create(fornecedorCertificado: NewFornecedorCertificado): Observable<EntityResponseType> {
    return this.http.post<IFornecedorCertificado>(this.resourceUrl, fornecedorCertificado, { observe: 'response' });
  }

  update(fornecedorCertificado: IFornecedorCertificado): Observable<EntityResponseType> {
    return this.http.put<IFornecedorCertificado>(
      `${this.resourceUrl}/${this.getFornecedorCertificadoIdentifier(fornecedorCertificado)}`,
      fornecedorCertificado,
      { observe: 'response' },
    );
  }

  partialUpdate(fornecedorCertificado: PartialUpdateFornecedorCertificado): Observable<EntityResponseType> {
    return this.http.patch<IFornecedorCertificado>(
      `${this.resourceUrl}/${this.getFornecedorCertificadoIdentifier(fornecedorCertificado)}`,
      fornecedorCertificado,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFornecedorCertificado>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFornecedorCertificado[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getFornecedorCertificadoIdentifier(fornecedorCertificado: Pick<IFornecedorCertificado, 'id'>): number {
    return fornecedorCertificado.id;
  }

  compareFornecedorCertificado(o1: Pick<IFornecedorCertificado, 'id'> | null, o2: Pick<IFornecedorCertificado, 'id'> | null): boolean {
    return o1 && o2 ? this.getFornecedorCertificadoIdentifier(o1) === this.getFornecedorCertificadoIdentifier(o2) : o1 === o2;
  }

  addFornecedorCertificadoToCollectionIfMissing<Type extends Pick<IFornecedorCertificado, 'id'>>(
    fornecedorCertificadoCollection: Type[],
    ...fornecedorCertificadosToCheck: (Type | null | undefined)[]
  ): Type[] {
    const fornecedorCertificados: Type[] = fornecedorCertificadosToCheck.filter(isPresent);
    if (fornecedorCertificados.length > 0) {
      const fornecedorCertificadoCollectionIdentifiers = fornecedorCertificadoCollection.map(fornecedorCertificadoItem =>
        this.getFornecedorCertificadoIdentifier(fornecedorCertificadoItem),
      );
      const fornecedorCertificadosToAdd = fornecedorCertificados.filter(fornecedorCertificadoItem => {
        const fornecedorCertificadoIdentifier = this.getFornecedorCertificadoIdentifier(fornecedorCertificadoItem);
        if (fornecedorCertificadoCollectionIdentifiers.includes(fornecedorCertificadoIdentifier)) {
          return false;
        }
        fornecedorCertificadoCollectionIdentifiers.push(fornecedorCertificadoIdentifier);
        return true;
      });
      return [...fornecedorCertificadosToAdd, ...fornecedorCertificadoCollection];
    }
    return fornecedorCertificadoCollection;
  }
}
