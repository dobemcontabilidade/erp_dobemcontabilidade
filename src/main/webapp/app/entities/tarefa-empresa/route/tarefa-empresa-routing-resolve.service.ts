import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ITarefaEmpresa } from '../tarefa-empresa.model';
import { TarefaEmpresaService } from '../service/tarefa-empresa.service';

const tarefaEmpresaResolve = (route: ActivatedRouteSnapshot): Observable<null | ITarefaEmpresa> => {
  const id = route.params['id'];
  if (id) {
    return inject(TarefaEmpresaService)
      .find(id)
      .pipe(
        mergeMap((tarefaEmpresa: HttpResponse<ITarefaEmpresa>) => {
          if (tarefaEmpresa.body) {
            return of(tarefaEmpresa.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default tarefaEmpresaResolve;
