import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAnexoTarefaRecorrenteExecucao } from '../anexo-tarefa-recorrente-execucao.model';
import { AnexoTarefaRecorrenteExecucaoService } from '../service/anexo-tarefa-recorrente-execucao.service';

const anexoTarefaRecorrenteExecucaoResolve = (route: ActivatedRouteSnapshot): Observable<null | IAnexoTarefaRecorrenteExecucao> => {
  const id = route.params['id'];
  if (id) {
    return inject(AnexoTarefaRecorrenteExecucaoService)
      .find(id)
      .pipe(
        mergeMap((anexoTarefaRecorrenteExecucao: HttpResponse<IAnexoTarefaRecorrenteExecucao>) => {
          if (anexoTarefaRecorrenteExecucao.body) {
            return of(anexoTarefaRecorrenteExecucao.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default anexoTarefaRecorrenteExecucaoResolve;
