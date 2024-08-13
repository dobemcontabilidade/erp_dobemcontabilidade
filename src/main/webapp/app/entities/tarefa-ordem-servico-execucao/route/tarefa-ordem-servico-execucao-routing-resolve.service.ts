import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITarefaOrdemServicoExecucao } from '../tarefa-ordem-servico-execucao.model';
import { TarefaOrdemServicoExecucaoService } from '../service/tarefa-ordem-servico-execucao.service';

const tarefaOrdemServicoExecucaoResolve = (route: ActivatedRouteSnapshot): Observable<null | ITarefaOrdemServicoExecucao> => {
  const id = route.params['id'];
  if (id) {
    return inject(TarefaOrdemServicoExecucaoService)
      .find(id)
      .pipe(
        mergeMap((tarefaOrdemServicoExecucao: HttpResponse<ITarefaOrdemServicoExecucao>) => {
          if (tarefaOrdemServicoExecucao.body) {
            return of(tarefaOrdemServicoExecucao.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default tarefaOrdemServicoExecucaoResolve;
