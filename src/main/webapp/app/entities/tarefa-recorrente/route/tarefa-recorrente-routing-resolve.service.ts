import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITarefaRecorrente } from '../tarefa-recorrente.model';
import { TarefaRecorrenteService } from '../service/tarefa-recorrente.service';

const tarefaRecorrenteResolve = (route: ActivatedRouteSnapshot): Observable<null | ITarefaRecorrente> => {
  const id = route.params['id'];
  if (id) {
    return inject(TarefaRecorrenteService)
      .find(id)
      .pipe(
        mergeMap((tarefaRecorrente: HttpResponse<ITarefaRecorrente>) => {
          if (tarefaRecorrente.body) {
            return of(tarefaRecorrente.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default tarefaRecorrenteResolve;
