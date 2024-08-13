import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAnexoRequeridoTarefaRecorrente } from '../anexo-requerido-tarefa-recorrente.model';
import { AnexoRequeridoTarefaRecorrenteService } from '../service/anexo-requerido-tarefa-recorrente.service';

const anexoRequeridoTarefaRecorrenteResolve = (route: ActivatedRouteSnapshot): Observable<null | IAnexoRequeridoTarefaRecorrente> => {
  const id = route.params['id'];
  if (id) {
    return inject(AnexoRequeridoTarefaRecorrenteService)
      .find(id)
      .pipe(
        mergeMap((anexoRequeridoTarefaRecorrente: HttpResponse<IAnexoRequeridoTarefaRecorrente>) => {
          if (anexoRequeridoTarefaRecorrente.body) {
            return of(anexoRequeridoTarefaRecorrente.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default anexoRequeridoTarefaRecorrenteResolve;
