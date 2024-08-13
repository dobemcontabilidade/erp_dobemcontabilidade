import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEscolaridade } from '../escolaridade.model';
import { EscolaridadeService } from '../service/escolaridade.service';

const escolaridadeResolve = (route: ActivatedRouteSnapshot): Observable<null | IEscolaridade> => {
  const id = route.params['id'];
  if (id) {
    return inject(EscolaridadeService)
      .find(id)
      .pipe(
        mergeMap((escolaridade: HttpResponse<IEscolaridade>) => {
          if (escolaridade.body) {
            return of(escolaridade.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default escolaridadeResolve;
