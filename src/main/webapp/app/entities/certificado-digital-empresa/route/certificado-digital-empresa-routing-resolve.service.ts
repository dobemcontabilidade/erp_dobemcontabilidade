import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICertificadoDigitalEmpresa } from '../certificado-digital-empresa.model';
import { CertificadoDigitalEmpresaService } from '../service/certificado-digital-empresa.service';

const certificadoDigitalEmpresaResolve = (route: ActivatedRouteSnapshot): Observable<null | ICertificadoDigitalEmpresa> => {
  const id = route.params['id'];
  if (id) {
    return inject(CertificadoDigitalEmpresaService)
      .find(id)
      .pipe(
        mergeMap((certificadoDigitalEmpresa: HttpResponse<ICertificadoDigitalEmpresa>) => {
          if (certificadoDigitalEmpresa.body) {
            return of(certificadoDigitalEmpresa.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default certificadoDigitalEmpresaResolve;
