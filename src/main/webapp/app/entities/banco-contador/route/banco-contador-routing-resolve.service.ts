import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IBancoContador } from '../banco-contador.model';
import { BancoContadorService } from '../service/banco-contador.service';

const bancoContadorResolve = (route: ActivatedRouteSnapshot): Observable<null | IBancoContador> => {
  const id = route.params['id'];
  if (id) {
    return inject(BancoContadorService)
      .find(id)
      .pipe(
        mergeMap((bancoContador: HttpResponse<IBancoContador>) => {
          if (bancoContador.body) {
            return of(bancoContador.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default bancoContadorResolve;
