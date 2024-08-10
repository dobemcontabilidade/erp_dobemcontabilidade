import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICertificadoDigital } from '../certificado-digital.model';
import { CertificadoDigitalService } from '../service/certificado-digital.service';

const certificadoDigitalResolve = (route: ActivatedRouteSnapshot): Observable<null | ICertificadoDigital> => {
  const id = route.params['id'];
  if (id) {
    return inject(CertificadoDigitalService)
      .find(id)
      .pipe(
        mergeMap((certificadoDigital: HttpResponse<ICertificadoDigital>) => {
          if (certificadoDigital.body) {
            return of(certificadoDigital.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default certificadoDigitalResolve;
