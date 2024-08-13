import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IFuncionalidade } from '../funcionalidade.model';
import { FuncionalidadeService } from '../service/funcionalidade.service';

const funcionalidadeResolve = (route: ActivatedRouteSnapshot): Observable<null | IFuncionalidade> => {
  const id = route.params['id'];
  if (id) {
    return inject(FuncionalidadeService)
      .find(id)
      .pipe(
        mergeMap((funcionalidade: HttpResponse<IFuncionalidade>) => {
          if (funcionalidade.body) {
            return of(funcionalidade.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default funcionalidadeResolve;
