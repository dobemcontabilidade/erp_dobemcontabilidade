import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IProfissao } from '../profissao.model';
import { ProfissaoService } from '../service/profissao.service';

const profissaoResolve = (route: ActivatedRouteSnapshot): Observable<null | IProfissao> => {
  const id = route.params['id'];
  if (id) {
    return inject(ProfissaoService)
      .find(id)
      .pipe(
        mergeMap((profissao: HttpResponse<IProfissao>) => {
          if (profissao.body) {
            return of(profissao.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default profissaoResolve;
