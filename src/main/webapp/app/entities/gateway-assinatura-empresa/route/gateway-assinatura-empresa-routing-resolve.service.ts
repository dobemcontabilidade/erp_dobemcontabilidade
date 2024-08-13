import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IGatewayAssinaturaEmpresa } from '../gateway-assinatura-empresa.model';
import { GatewayAssinaturaEmpresaService } from '../service/gateway-assinatura-empresa.service';

const gatewayAssinaturaEmpresaResolve = (route: ActivatedRouteSnapshot): Observable<null | IGatewayAssinaturaEmpresa> => {
  const id = route.params['id'];
  if (id) {
    return inject(GatewayAssinaturaEmpresaService)
      .find(id)
      .pipe(
        mergeMap((gatewayAssinaturaEmpresa: HttpResponse<IGatewayAssinaturaEmpresa>) => {
          if (gatewayAssinaturaEmpresa.body) {
            return of(gatewayAssinaturaEmpresa.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default gatewayAssinaturaEmpresaResolve;
