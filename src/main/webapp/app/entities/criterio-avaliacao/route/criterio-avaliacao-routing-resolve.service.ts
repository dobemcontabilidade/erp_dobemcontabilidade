import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICriterioAvaliacao } from '../criterio-avaliacao.model';
import { CriterioAvaliacaoService } from '../service/criterio-avaliacao.service';

const criterioAvaliacaoResolve = (route: ActivatedRouteSnapshot): Observable<null | ICriterioAvaliacao> => {
  const id = route.params['id'];
  if (id) {
    return inject(CriterioAvaliacaoService)
      .find(id)
      .pipe(
        mergeMap((criterioAvaliacao: HttpResponse<ICriterioAvaliacao>) => {
          if (criterioAvaliacao.body) {
            return of(criterioAvaliacao.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default criterioAvaliacaoResolve;
