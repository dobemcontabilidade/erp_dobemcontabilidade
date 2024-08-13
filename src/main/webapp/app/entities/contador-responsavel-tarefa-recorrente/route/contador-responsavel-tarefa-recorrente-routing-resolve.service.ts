import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IContadorResponsavelTarefaRecorrente } from '../contador-responsavel-tarefa-recorrente.model';
import { ContadorResponsavelTarefaRecorrenteService } from '../service/contador-responsavel-tarefa-recorrente.service';

const contadorResponsavelTarefaRecorrenteResolve = (
  route: ActivatedRouteSnapshot,
): Observable<null | IContadorResponsavelTarefaRecorrente> => {
  const id = route.params['id'];
  if (id) {
    return inject(ContadorResponsavelTarefaRecorrenteService)
      .find(id)
      .pipe(
        mergeMap((contadorResponsavelTarefaRecorrente: HttpResponse<IContadorResponsavelTarefaRecorrente>) => {
          if (contadorResponsavelTarefaRecorrente.body) {
            return of(contadorResponsavelTarefaRecorrente.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default contadorResponsavelTarefaRecorrenteResolve;
