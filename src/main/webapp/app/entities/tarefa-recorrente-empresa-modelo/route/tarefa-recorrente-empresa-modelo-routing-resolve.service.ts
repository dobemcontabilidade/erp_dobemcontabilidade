import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITarefaRecorrenteEmpresaModelo } from '../tarefa-recorrente-empresa-modelo.model';
import { TarefaRecorrenteEmpresaModeloService } from '../service/tarefa-recorrente-empresa-modelo.service';

const tarefaRecorrenteEmpresaModeloResolve = (route: ActivatedRouteSnapshot): Observable<null | ITarefaRecorrenteEmpresaModelo> => {
  const id = route.params['id'];
  if (id) {
    return inject(TarefaRecorrenteEmpresaModeloService)
      .find(id)
      .pipe(
        mergeMap((tarefaRecorrenteEmpresaModelo: HttpResponse<ITarefaRecorrenteEmpresaModelo>) => {
          if (tarefaRecorrenteEmpresaModelo.body) {
            return of(tarefaRecorrenteEmpresaModelo.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default tarefaRecorrenteEmpresaModeloResolve;
