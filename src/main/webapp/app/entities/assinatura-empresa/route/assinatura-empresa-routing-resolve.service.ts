import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAssinaturaEmpresa } from '../assinatura-empresa.model';
import { AssinaturaEmpresaService } from '../service/assinatura-empresa.service';

const assinaturaEmpresaResolve = (route: ActivatedRouteSnapshot): Observable<null | IAssinaturaEmpresa> => {
  const id = route.params['id'];
  if (id) {
    return inject(AssinaturaEmpresaService)
      .find(id)
      .pipe(
        mergeMap((assinaturaEmpresa: HttpResponse<IAssinaturaEmpresa>) => {
          if (assinaturaEmpresa.body) {
            return of(assinaturaEmpresa.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default assinaturaEmpresaResolve;
