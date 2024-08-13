import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAnexoRequeridoTarefaOrdemServico } from '../anexo-requerido-tarefa-ordem-servico.model';
import { AnexoRequeridoTarefaOrdemServicoService } from '../service/anexo-requerido-tarefa-ordem-servico.service';

const anexoRequeridoTarefaOrdemServicoResolve = (route: ActivatedRouteSnapshot): Observable<null | IAnexoRequeridoTarefaOrdemServico> => {
  const id = route.params['id'];
  if (id) {
    return inject(AnexoRequeridoTarefaOrdemServicoService)
      .find(id)
      .pipe(
        mergeMap((anexoRequeridoTarefaOrdemServico: HttpResponse<IAnexoRequeridoTarefaOrdemServico>) => {
          if (anexoRequeridoTarefaOrdemServico.body) {
            return of(anexoRequeridoTarefaOrdemServico.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default anexoRequeridoTarefaOrdemServicoResolve;
