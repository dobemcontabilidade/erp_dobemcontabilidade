import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IImpostoEmpresa } from '../imposto-empresa.model';
import { ImpostoEmpresaService } from '../service/imposto-empresa.service';

const impostoEmpresaResolve = (route: ActivatedRouteSnapshot): Observable<null | IImpostoEmpresa> => {
  const id = route.params['id'];
  if (id) {
    return inject(ImpostoEmpresaService)
      .find(id)
      .pipe(
        mergeMap((impostoEmpresa: HttpResponse<IImpostoEmpresa>) => {
          if (impostoEmpresa.body) {
            return of(impostoEmpresa.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default impostoEmpresaResolve;
