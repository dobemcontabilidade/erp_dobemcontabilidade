import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IFrequencia } from '../frequencia.model';
import { FrequenciaService } from '../service/frequencia.service';

const frequenciaResolve = (route: ActivatedRouteSnapshot): Observable<null | IFrequencia> => {
  const id = route.params['id'];
  if (id) {
    return inject(FrequenciaService)
      .find(id)
      .pipe(
        mergeMap((frequencia: HttpResponse<IFrequencia>) => {
          if (frequencia.body) {
            return of(frequencia.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default frequenciaResolve;
