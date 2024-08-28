import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDescontoPeriodoPagamento } from '../desconto-periodo-pagamento.model';
import { DescontoPeriodoPagamentoService } from '../service/desconto-periodo-pagamento.service';

const descontoPeriodoPagamentoResolve = (route: ActivatedRouteSnapshot): Observable<null | IDescontoPeriodoPagamento> => {
  const id = route.params['id'];
  if (id) {
    return inject(DescontoPeriodoPagamentoService)
      .find(id)
      .pipe(
        mergeMap((descontoPeriodoPagamento: HttpResponse<IDescontoPeriodoPagamento>) => {
          if (descontoPeriodoPagamento.body) {
            return of(descontoPeriodoPagamento.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default descontoPeriodoPagamentoResolve;
