import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICobrancaEmpresa } from '../cobranca-empresa.model';
import { CobrancaEmpresaService } from '../service/cobranca-empresa.service';

const cobrancaEmpresaResolve = (route: ActivatedRouteSnapshot): Observable<null | ICobrancaEmpresa> => {
  const id = route.params['id'];
  if (id) {
    return inject(CobrancaEmpresaService)
      .find(id)
      .pipe(
        mergeMap((cobrancaEmpresa: HttpResponse<ICobrancaEmpresa>) => {
          if (cobrancaEmpresa.body) {
            return of(cobrancaEmpresa.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default cobrancaEmpresaResolve;
