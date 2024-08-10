import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITarefa } from '../tarefa.model';
import { TarefaService } from '../service/tarefa.service';

const tarefaResolve = (route: ActivatedRouteSnapshot): Observable<null | ITarefa> => {
  const id = route.params['id'];
  if (id) {
    return inject(TarefaService)
      .find(id)
      .pipe(
        mergeMap((tarefa: HttpResponse<ITarefa>) => {
          if (tarefa.body) {
            return of(tarefa.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default tarefaResolve;
