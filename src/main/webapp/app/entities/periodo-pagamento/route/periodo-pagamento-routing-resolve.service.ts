import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPeriodoPagamento } from '../periodo-pagamento.model';
import { PeriodoPagamentoService } from '../service/periodo-pagamento.service';

const periodoPagamentoResolve = (route: ActivatedRouteSnapshot): Observable<null | IPeriodoPagamento> => {
  const id = route.params['id'];
  if (id) {
    return inject(PeriodoPagamentoService)
      .find(id)
      .pipe(
        mergeMap((periodoPagamento: HttpResponse<IPeriodoPagamento>) => {
          if (periodoPagamento.body) {
            return of(periodoPagamento.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default periodoPagamentoResolve;
