import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IFeedBackContadorParaUsuario } from '../feed-back-contador-para-usuario.model';
import { FeedBackContadorParaUsuarioService } from '../service/feed-back-contador-para-usuario.service';

const feedBackContadorParaUsuarioResolve = (route: ActivatedRouteSnapshot): Observable<null | IFeedBackContadorParaUsuario> => {
  const id = route.params['id'];
  if (id) {
    return inject(FeedBackContadorParaUsuarioService)
      .find(id)
      .pipe(
        mergeMap((feedBackContadorParaUsuario: HttpResponse<IFeedBackContadorParaUsuario>) => {
          if (feedBackContadorParaUsuario.body) {
            return of(feedBackContadorParaUsuario.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default feedBackContadorParaUsuarioResolve;
