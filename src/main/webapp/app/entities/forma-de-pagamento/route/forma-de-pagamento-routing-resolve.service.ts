import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IFormaDePagamento } from '../forma-de-pagamento.model';
import { FormaDePagamentoService } from '../service/forma-de-pagamento.service';

const formaDePagamentoResolve = (route: ActivatedRouteSnapshot): Observable<null | IFormaDePagamento> => {
  const id = route.params['id'];
  if (id) {
    return inject(FormaDePagamentoService)
      .find(id)
      .pipe(
        mergeMap((formaDePagamento: HttpResponse<IFormaDePagamento>) => {
          if (formaDePagamento.body) {
            return of(formaDePagamento.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default formaDePagamentoResolve;
