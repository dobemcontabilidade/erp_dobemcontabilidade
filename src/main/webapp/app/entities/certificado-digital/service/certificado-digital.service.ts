import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICertificadoDigital, NewCertificadoDigital } from '../certificado-digital.model';

export type PartialUpdateCertificadoDigital = Partial<ICertificadoDigital> & Pick<ICertificadoDigital, 'id'>;

export type EntityResponseType = HttpResponse<ICertificadoDigital>;
export type EntityArrayResponseType = HttpResponse<ICertificadoDigital[]>;

@Injectable({ providedIn: 'root' })
export class CertificadoDigitalService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/certificado-digitals');

  create(certificadoDigital: NewCertificadoDigital): Observable<EntityResponseType> {
    return this.http.post<ICertificadoDigital>(this.resourceUrl, certificadoDigital, { observe: 'response' });
  }

  update(certificadoDigital: ICertificadoDigital): Observable<EntityResponseType> {
    return this.http.put<ICertificadoDigital>(
      `${this.resourceUrl}/${this.getCertificadoDigitalIdentifier(certificadoDigital)}`,
      certificadoDigital,
      { observe: 'response' },
    );
  }

  partialUpdate(certificadoDigital: PartialUpdateCertificadoDigital): Observable<EntityResponseType> {
    return this.http.patch<ICertificadoDigital>(
      `${this.resourceUrl}/${this.getCertificadoDigitalIdentifier(certificadoDigital)}`,
      certificadoDigital,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICertificadoDigital>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICertificadoDigital[]>(this.resourceUrl, { params: options, observe: 'response' });
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
}
