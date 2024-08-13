import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISubTarefaRecorrente } from '../sub-tarefa-recorrente.model';
import { SubTarefaRecorrenteService } from '../service/sub-tarefa-recorrente.service';

const subTarefaRecorrenteResolve = (route: ActivatedRouteSnapshot): Observable<null | ISubTarefaRecorrente> => {
  const id = route.params['id'];
  if (id) {
    return inject(SubTarefaRecorrenteService)
      .find(id)
      .pipe(
        mergeMap((subTarefaRecorrente: HttpResponse<ISubTarefaRecorrente>) => {
          if (subTarefaRecorrente.body) {
            return of(subTarefaRecorrente.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default subTarefaRecorrenteResolve;
