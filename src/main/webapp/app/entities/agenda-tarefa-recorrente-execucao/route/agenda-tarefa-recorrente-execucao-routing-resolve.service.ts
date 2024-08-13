import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAgendaTarefaRecorrenteExecucao } from '../agenda-tarefa-recorrente-execucao.model';
import { AgendaTarefaRecorrenteExecucaoService } from '../service/agenda-tarefa-recorrente-execucao.service';

const agendaTarefaRecorrenteExecucaoResolve = (route: ActivatedRouteSnapshot): Observable<null | IAgendaTarefaRecorrenteExecucao> => {
  const id = route.params['id'];
  if (id) {
    return inject(AgendaTarefaRecorrenteExecucaoService)
      .find(id)
      .pipe(
        mergeMap((agendaTarefaRecorrenteExecucao: HttpResponse<IAgendaTarefaRecorrenteExecucao>) => {
          if (agendaTarefaRecorrenteExecucao.body) {
            return of(agendaTarefaRecorrenteExecucao.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default agendaTarefaRecorrenteExecucaoResolve;
