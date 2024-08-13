import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAgendaTarefaOrdemServicoExecucao } from '../agenda-tarefa-ordem-servico-execucao.model';
import { AgendaTarefaOrdemServicoExecucaoService } from '../service/agenda-tarefa-ordem-servico-execucao.service';

const agendaTarefaOrdemServicoExecucaoResolve = (route: ActivatedRouteSnapshot): Observable<null | IAgendaTarefaOrdemServicoExecucao> => {
  const id = route.params['id'];
  if (id) {
    return inject(AgendaTarefaOrdemServicoExecucaoService)
      .find(id)
      .pipe(
        mergeMap((agendaTarefaOrdemServicoExecucao: HttpResponse<IAgendaTarefaOrdemServicoExecucao>) => {
          if (agendaTarefaOrdemServicoExecucao.body) {
            return of(agendaTarefaOrdemServicoExecucao.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default agendaTarefaOrdemServicoExecucaoResolve;
