import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IFeedBackUsuarioParaContador } from '../feed-back-usuario-para-contador.model';
import { FeedBackUsuarioParaContadorService } from '../service/feed-back-usuario-para-contador.service';

const feedBackUsuarioParaContadorResolve = (route: ActivatedRouteSnapshot): Observable<null | IFeedBackUsuarioParaContador> => {
  const id = route.params['id'];
  if (id) {
    return inject(FeedBackUsuarioParaContadorService)
      .find(id)
      .pipe(
        mergeMap((feedBackUsuarioParaContador: HttpResponse<IFeedBackUsuarioParaContador>) => {
          if (feedBackUsuarioParaContador.body) {
            return of(feedBackUsuarioParaContador.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default feedBackUsuarioParaContadorResolve;
