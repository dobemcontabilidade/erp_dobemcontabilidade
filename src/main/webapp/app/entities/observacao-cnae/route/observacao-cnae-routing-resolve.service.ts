import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IObservacaoCnae } from '../observacao-cnae.model';
import { ObservacaoCnaeService } from '../service/observacao-cnae.service';

const observacaoCnaeResolve = (route: ActivatedRouteSnapshot): Observable<null | IObservacaoCnae> => {
  const id = route.params['id'];
  if (id) {
    return inject(ObservacaoCnaeService)
      .find(id)
      .pipe(
        mergeMap((observacaoCnae: HttpResponse<IObservacaoCnae>) => {
          if (observacaoCnae.body) {
            return of(observacaoCnae.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default observacaoCnaeResolve;
