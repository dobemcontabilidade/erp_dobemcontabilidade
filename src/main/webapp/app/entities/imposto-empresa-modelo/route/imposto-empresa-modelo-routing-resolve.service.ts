import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IImpostoEmpresaModelo } from '../imposto-empresa-modelo.model';
import { ImpostoEmpresaModeloService } from '../service/imposto-empresa-modelo.service';

const impostoEmpresaModeloResolve = (route: ActivatedRouteSnapshot): Observable<null | IImpostoEmpresaModelo> => {
  const id = route.params['id'];
  if (id) {
    return inject(ImpostoEmpresaModeloService)
      .find(id)
      .pipe(
        mergeMap((impostoEmpresaModelo: HttpResponse<IImpostoEmpresaModelo>) => {
          if (impostoEmpresaModelo.body) {
            return of(impostoEmpresaModelo.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default impostoEmpresaModeloResolve;
