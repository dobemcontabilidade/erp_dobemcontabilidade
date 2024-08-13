import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITarefaOrdemServico } from '../tarefa-ordem-servico.model';
import { TarefaOrdemServicoService } from '../service/tarefa-ordem-servico.service';

const tarefaOrdemServicoResolve = (route: ActivatedRouteSnapshot): Observable<null | ITarefaOrdemServico> => {
  const id = route.params['id'];
  if (id) {
    return inject(TarefaOrdemServicoService)
      .find(id)
      .pipe(
        mergeMap((tarefaOrdemServico: HttpResponse<ITarefaOrdemServico>) => {
          if (tarefaOrdemServico.body) {
            return of(tarefaOrdemServico.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default tarefaOrdemServicoResolve;
