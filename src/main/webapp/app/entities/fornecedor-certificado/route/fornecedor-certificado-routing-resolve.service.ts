import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IFornecedorCertificado } from '../fornecedor-certificado.model';
import { FornecedorCertificadoService } from '../service/fornecedor-certificado.service';

const fornecedorCertificadoResolve = (route: ActivatedRouteSnapshot): Observable<null | IFornecedorCertificado> => {
  const id = route.params['id'];
  if (id) {
    return inject(FornecedorCertificadoService)
      .find(id)
      .pipe(
        mergeMap((fornecedorCertificado: HttpResponse<IFornecedorCertificado>) => {
          if (fornecedorCertificado.body) {
            return of(fornecedorCertificado.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default fornecedorCertificadoResolve;
