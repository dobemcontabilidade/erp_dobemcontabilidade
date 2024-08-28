import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICertificadoDigitalEmpresa, NewCertificadoDigitalEmpresa } from '../certificado-digital-empresa.model';

export type PartialUpdateCertificadoDigitalEmpresa = Partial<ICertificadoDigitalEmpresa> & Pick<ICertificadoDigitalEmpresa, 'id'>;

type RestOf<T extends ICertificadoDigitalEmpresa | NewCertificadoDigitalEmpresa> = Omit<T, 'dataContratacao' | 'dataVencimento'> & {
  dataContratacao?: string | null;
  dataVencimento?: string | null;
};

export type RestCertificadoDigitalEmpresa = RestOf<ICertificadoDigitalEmpresa>;

export type NewRestCertificadoDigitalEmpresa = RestOf<NewCertificadoDigitalEmpresa>;

export type PartialUpdateRestCertificadoDigitalEmpresa = RestOf<PartialUpdateCertificadoDigitalEmpresa>;

export type EntityResponseType = HttpResponse<ICertificadoDigitalEmpresa>;
export type EntityArrayResponseType = HttpResponse<ICertificadoDigitalEmpresa[]>;

@Injectable({ providedIn: 'root' })
export class CertificadoDigitalEmpresaService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/certificado-digital-empresas');

  create(certificadoDigitalEmpresa: NewCertificadoDigitalEmpresa): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(certificadoDigitalEmpresa);
    return this.http
      .post<RestCertificadoDigitalEmpresa>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(certificadoDigitalEmpresa: ICertificadoDigitalEmpresa): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(certificadoDigitalEmpresa);
    return this.http
      .put<RestCertificadoDigitalEmpresa>(
        `${this.resourceUrl}/${this.getCertificadoDigitalEmpresaIdentifier(certificadoDigitalEmpresa)}`,
        copy,
        { observe: 'response' },
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(certificadoDigitalEmpresa: PartialUpdateCertificadoDigitalEmpresa): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(certificadoDigitalEmpresa);
    return this.http
      .patch<RestCertificadoDigitalEmpresa>(
        `${this.resourceUrl}/${this.getCertificadoDigitalEmpresaIdentifier(certificadoDigitalEmpresa)}`,
        copy,
        { observe: 'response' },
      )
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestCertificadoDigitalEmpresa>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestCertificadoDigitalEmpresa[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCertificadoDigitalEmpresaIdentifier(certificadoDigitalEmpresa: Pick<ICertificadoDigitalEmpresa, 'id'>): number {
    return certificadoDigitalEmpresa.id;
  }

  compareCertificadoDigitalEmpresa(
    o1: Pick<ICertificadoDigitalEmpresa, 'id'> | null,
    o2: Pick<ICertificadoDigitalEmpresa, 'id'> | null,
  ): boolean {
    return o1 && o2 ? this.getCertificadoDigitalEmpresaIdentifier(o1) === this.getCertificadoDigitalEmpresaIdentifier(o2) : o1 === o2;
  }

  addCertificadoDigitalEmpresaToCollectionIfMissing<Type extends Pick<ICertificadoDigitalEmpresa, 'id'>>(
    certificadoDigitalEmpresaCollection: Type[],
    ...certificadoDigitalEmpresasToCheck: (Type | null | undefined)[]
  ): Type[] {
    const certificadoDigitalEmpresas: Type[] = certificadoDigitalEmpresasToCheck.filter(isPresent);
    if (certificadoDigitalEmpresas.length > 0) {
      const certificadoDigitalEmpresaCollectionIdentifiers = certificadoDigitalEmpresaCollection.map(certificadoDigitalEmpresaItem =>
        this.getCertificadoDigitalEmpresaIdentifier(certificadoDigitalEmpresaItem),
      );
      const certificadoDigitalEmpresasToAdd = certificadoDigitalEmpresas.filter(certificadoDigitalEmpresaItem => {
        const certificadoDigitalEmpresaIdentifier = this.getCertificadoDigitalEmpresaIdentifier(certificadoDigitalEmpresaItem);
        if (certificadoDigitalEmpresaCollectionIdentifiers.includes(certificadoDigitalEmpresaIdentifier)) {
          return false;
        }
        certificadoDigitalEmpresaCollectionIdentifiers.push(certificadoDigitalEmpresaIdentifier);
        return true;
      });
      return [...certificadoDigitalEmpresasToAdd, ...certificadoDigitalEmpresaCollection];
    }
    return certificadoDigitalEmpresaCollection;
  }

  protected convertDateFromClient<
    T extends ICertificadoDigitalEmpresa | NewCertificadoDigitalEmpresa | PartialUpdateCertificadoDigitalEmpresa,
  >(certificadoDigitalEmpresa: T): RestOf<T> {
    return {
      ...certificadoDigitalEmpresa,
      dataContratacao: certificadoDigitalEmpresa.dataContratacao?.toJSON() ?? null,
      dataVencimento: certificadoDigitalEmpresa.dataVencimento?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restCertificadoDigitalEmpresa: RestCertificadoDigitalEmpresa): ICertificadoDigitalEmpresa {
    return {
      ...restCertificadoDigitalEmpresa,
      dataContratacao: restCertificadoDigitalEmpresa.dataContratacao ? dayjs(restCertificadoDigitalEmpresa.dataContratacao) : undefined,
      dataVencimento: restCertificadoDigitalEmpresa.dataVencimento ? dayjs(restCertificadoDigitalEmpresa.dataVencimento) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestCertificadoDigitalEmpresa>): HttpResponse<ICertificadoDigitalEmpresa> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestCertificadoDigitalEmpresa[]>): HttpResponse<ICertificadoDigitalEmpresa[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
