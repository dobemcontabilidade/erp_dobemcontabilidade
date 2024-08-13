import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISecaoCnae } from '../secao-cnae.model';
import { SecaoCnaeService } from '../service/secao-cnae.service';

const secaoCnaeResolve = (route: ActivatedRouteSnapshot): Observable<null | ISecaoCnae> => {
  const id = route.params['id'];
  if (id) {
    return inject(SecaoCnaeService)
      .find(id)
      .pipe(
        mergeMap((secaoCnae: HttpResponse<ISecaoCnae>) => {
          if (secaoCnae.body) {
            return of(secaoCnae.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default secaoCnaeResolve;
