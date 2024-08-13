import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITarefaEmpresaModelo } from '../tarefa-empresa-modelo.model';
import { TarefaEmpresaModeloService } from '../service/tarefa-empresa-modelo.service';

const tarefaEmpresaModeloResolve = (route: ActivatedRouteSnapshot): Observable<null | ITarefaEmpresaModelo> => {
  const id = route.params['id'];
  if (id) {
    return inject(TarefaEmpresaModeloService)
      .find(id)
      .pipe(
        mergeMap((tarefaEmpresaModelo: HttpResponse<ITarefaEmpresaModelo>) => {
          if (tarefaEmpresaModelo.body) {
            return of(tarefaEmpresaModelo.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default tarefaEmpresaModeloResolve;
