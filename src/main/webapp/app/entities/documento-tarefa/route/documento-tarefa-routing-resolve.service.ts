import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IDocumentoTarefa } from '../documento-tarefa.model';
import { DocumentoTarefaService } from '../service/documento-tarefa.service';

const documentoTarefaResolve = (route: ActivatedRouteSnapshot): Observable<null | IDocumentoTarefa> => {
  const id = route.params['id'];
  if (id) {
    return inject(DocumentoTarefaService)
      .find(id)
      .pipe(
        mergeMap((documentoTarefa: HttpResponse<IDocumentoTarefa>) => {
          if (documentoTarefa.body) {
            return of(documentoTarefa.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default documentoTarefaResolve;
