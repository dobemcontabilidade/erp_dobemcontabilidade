import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICriterioAvaliacaoAtor } from '../criterio-avaliacao-ator.model';
import { CriterioAvaliacaoAtorService } from '../service/criterio-avaliacao-ator.service';

const criterioAvaliacaoAtorResolve = (route: ActivatedRouteSnapshot): Observable<null | ICriterioAvaliacaoAtor> => {
  const id = route.params['id'];
  if (id) {
    return inject(CriterioAvaliacaoAtorService)
      .find(id)
      .pipe(
        mergeMap((criterioAvaliacaoAtor: HttpResponse<ICriterioAvaliacaoAtor>) => {
          if (criterioAvaliacaoAtor.body) {
            return of(criterioAvaliacaoAtor.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default criterioAvaliacaoAtorResolve;
