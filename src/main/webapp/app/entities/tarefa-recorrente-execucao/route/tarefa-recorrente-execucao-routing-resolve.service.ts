import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITarefaRecorrenteExecucao } from '../tarefa-recorrente-execucao.model';
import { TarefaRecorrenteExecucaoService } from '../service/tarefa-recorrente-execucao.service';

const tarefaRecorrenteExecucaoResolve = (route: ActivatedRouteSnapshot): Observable<null | ITarefaRecorrenteExecucao> => {
  const id = route.params['id'];
  if (id) {
    return inject(TarefaRecorrenteExecucaoService)
      .find(id)
      .pipe(
        mergeMap((tarefaRecorrenteExecucao: HttpResponse<ITarefaRecorrenteExecucao>) => {
          if (tarefaRecorrenteExecucao.body) {
            return of(tarefaRecorrenteExecucao.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default tarefaRecorrenteExecucaoResolve;
