import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IImpostoAPagarEmpresa } from '../imposto-a-pagar-empresa.model';
import { ImpostoAPagarEmpresaService } from '../service/imposto-a-pagar-empresa.service';

const impostoAPagarEmpresaResolve = (route: ActivatedRouteSnapshot): Observable<null | IImpostoAPagarEmpresa> => {
  const id = route.params['id'];
  if (id) {
    return inject(ImpostoAPagarEmpresaService)
      .find(id)
      .pipe(
        mergeMap((impostoAPagarEmpresa: HttpResponse<IImpostoAPagarEmpresa>) => {
          if (impostoAPagarEmpresa.body) {
            return of(impostoAPagarEmpresa.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default impostoAPagarEmpresaResolve;
