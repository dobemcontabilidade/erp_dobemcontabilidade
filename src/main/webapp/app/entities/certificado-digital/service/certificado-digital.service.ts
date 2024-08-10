import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICertificadoDigital, NewCertificadoDigital } from '../certificado-digital.model';

export type PartialUpdateCertificadoDigital = Partial<ICertificadoDigital> & Pick<ICertificadoDigital, 'id'>;

type RestOf<T extends ICertificadoDigital | NewCertificadoDigital> = Omit<T, 'dataContratacao'> & {
  dataContratacao?: string | null;
};

export type RestCertificadoDigital = RestOf<ICertificadoDigital>;

export type NewRestCertificadoDigital = RestOf<NewCertificadoDigital>;

export type PartialUpdateRestCertificadoDigital = RestOf<PartialUpdateCertificadoDigital>;

export type EntityResponseType = HttpResponse<ICertificadoDigital>;
export type EntityArrayResponseType = HttpResponse<ICertificadoDigital[]>;

@Injectable({ providedIn: 'root' })
export class CertificadoDigitalService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/certificado-digitals');

  create(certificadoDigital: NewCertificadoDigital): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(certificadoDigital);
    return this.http
      .post<RestCertificadoDigital>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(certificadoDigital: ICertificadoDigital): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(certificadoDigital);
    return this.http
      .put<RestCertificadoDigital>(`${this.resourceUrl}/${this.getCertificadoDigitalIdentifier(certificadoDigital)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(certificadoDigital: PartialUpdateCertificadoDigital): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(certificadoDigital);
    return this.http
      .patch<RestCertificadoDigital>(`${this.resourceUrl}/${this.getCertificadoDigitalIdentifier(certificadoDigital)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestCertificadoDigital>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestCertificadoDigital[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCertificadoDigitalIdentifier(certificadoDigital: Pick<ICertificadoDigital, 'id'>): number {
    return certificadoDigital.id;
  }

  compareCertificadoDigital(o1: Pick<ICertificadoDigital, 'id'> | null, o2: Pick<ICertificadoDigital, 'id'> | null): boolean {
    return o1 && o2 ? this.getCertificadoDigitalIdentifier(o1) === this.getCertificadoDigitalIdentifier(o2) : o1 === o2;
  }

  addCertificadoDigitalToCollectionIfMissing<Type extends Pick<ICertificadoDigital, 'id'>>(
    certificadoDigitalCollection: Type[],
    ...certificadoDigitalsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const certificadoDigitals: Type[] = certificadoDigitalsToCheck.filter(isPresent);
    if (certificadoDigitals.length > 0) {
      const certificadoDigitalCollectionIdentifiers = certificadoDigitalCollection.map(certificadoDigitalItem =>
        this.getCertificadoDigitalIdentifier(certificadoDigitalItem),
      );
      const certificadoDigitalsToAdd = certificadoDigitals.filter(certificadoDigitalItem => {
        const certificadoDigitalIdentifier = this.getCertificadoDigitalIdentifier(certificadoDigitalItem);
        if (certificadoDigitalCollectionIdentifiers.includes(certificadoDigitalIdentifier)) {
          return false;
        }
        certificadoDigitalCollectionIdentifiers.push(certificadoDigitalIdentifier);
        return true;
      });
      return [...certificadoDigitalsToAdd, ...certificadoDigitalCollection];
    }
    return certificadoDigitalCollection;
  }

  protected convertDateFromClient<T extends ICertificadoDigital | NewCertificadoDigital | PartialUpdateCertificadoDigital>(
    certificadoDigital: T,
  ): RestOf<T> {
    return {
      ...certificadoDigital,
      dataContratacao: certificadoDigital.dataContratacao?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restCertificadoDigital: RestCertificadoDigital): ICertificadoDigital {
    return {
      ...restCertificadoDigital,
      dataContratacao: restCertificadoDigital.dataContratacao ? dayjs(restCertificadoDigital.dataContratacao) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestCertificadoDigital>): HttpResponse<ICertificadoDigital> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestCertificadoDigital[]>): HttpResponse<ICertificadoDigital[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
