import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITributacao } from '../tributacao.model';
import { TributacaoService } from '../service/tributacao.service';

const tributacaoResolve = (route: ActivatedRouteSnapshot): Observable<null | ITributacao> => {
  const id = route.params['id'];
  if (id) {
    return inject(TributacaoService)
      .find(id)
      .pipe(
        mergeMap((tributacao: HttpResponse<ITributacao>) => {
          if (tributacao.body) {
            return of(tributacao.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default tributacaoResolve;
