import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISubTarefaOrdemServico } from '../sub-tarefa-ordem-servico.model';
import { SubTarefaOrdemServicoService } from '../service/sub-tarefa-ordem-servico.service';

const subTarefaOrdemServicoResolve = (route: ActivatedRouteSnapshot): Observable<null | ISubTarefaOrdemServico> => {
  const id = route.params['id'];
  if (id) {
    return inject(SubTarefaOrdemServicoService)
      .find(id)
      .pipe(
        mergeMap((subTarefaOrdemServico: HttpResponse<ISubTarefaOrdemServico>) => {
          if (subTarefaOrdemServico.body) {
            return of(subTarefaOrdemServico.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default subTarefaOrdemServicoResolve;
