import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISubtarefa } from '../subtarefa.model';
import { SubtarefaService } from '../service/subtarefa.service';

const subtarefaResolve = (route: ActivatedRouteSnapshot): Observable<null | ISubtarefa> => {
  const id = route.params['id'];
  if (id) {
    return inject(SubtarefaService)
      .find(id)
      .pipe(
        mergeMap((subtarefa: HttpResponse<ISubtarefa>) => {
          if (subtarefa.body) {
            return of(subtarefa.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default subtarefaResolve;
