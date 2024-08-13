import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAvaliacaoContador } from '../avaliacao-contador.model';
import { AvaliacaoContadorService } from '../service/avaliacao-contador.service';

const avaliacaoContadorResolve = (route: ActivatedRouteSnapshot): Observable<null | IAvaliacaoContador> => {
  const id = route.params['id'];
  if (id) {
    return inject(AvaliacaoContadorService)
      .find(id)
      .pipe(
        mergeMap((avaliacaoContador: HttpResponse<IAvaliacaoContador>) => {
          if (avaliacaoContador.body) {
            return of(avaliacaoContador.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default avaliacaoContadorResolve;
