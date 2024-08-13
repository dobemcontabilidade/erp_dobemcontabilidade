import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IGatewayPagamento } from '../gateway-pagamento.model';
import { GatewayPagamentoService } from '../service/gateway-pagamento.service';

const gatewayPagamentoResolve = (route: ActivatedRouteSnapshot): Observable<null | IGatewayPagamento> => {
  const id = route.params['id'];
  if (id) {
    return inject(GatewayPagamentoService)
      .find(id)
      .pipe(
        mergeMap((gatewayPagamento: HttpResponse<IGatewayPagamento>) => {
          if (gatewayPagamento.body) {
            return of(gatewayPagamento.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default gatewayPagamentoResolve;
