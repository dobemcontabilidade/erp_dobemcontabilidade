import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEnquadramento } from '../enquadramento.model';
import { EnquadramentoService } from '../service/enquadramento.service';

const enquadramentoResolve = (route: ActivatedRouteSnapshot): Observable<null | IEnquadramento> => {
  const id = route.params['id'];
  if (id) {
    return inject(EnquadramentoService)
      .find(id)
      .pipe(
        mergeMap((enquadramento: HttpResponse<IEnquadramento>) => {
          if (enquadramento.body) {
            return of(enquadramento.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default enquadramentoResolve;
