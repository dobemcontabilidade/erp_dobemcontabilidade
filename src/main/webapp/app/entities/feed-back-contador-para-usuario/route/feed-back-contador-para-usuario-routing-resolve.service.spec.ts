import { TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { IFeedBackContadorParaUsuario } from '../feed-back-contador-para-usuario.model';
import { FeedBackContadorParaUsuarioService } from '../service/feed-back-contador-para-usuario.service';

import feedBackContadorParaUsuarioResolve from './feed-back-contador-para-usuario-routing-resolve.service';

describe('FeedBackContadorParaUsuario routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: FeedBackContadorParaUsuarioService;
  let resultFeedBackContadorParaUsuario: IFeedBackContadorParaUsuario | null | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        provideHttpClient(),
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    service = TestBed.inject(FeedBackContadorParaUsuarioService);
    resultFeedBackContadorParaUsuario = undefined;
  });

  describe('resolve', () => {
    it('should return IFeedBackContadorParaUsuario returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        feedBackContadorParaUsuarioResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultFeedBackContadorParaUsuario = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultFeedBackContadorParaUsuario).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        feedBackContadorParaUsuarioResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultFeedBackContadorParaUsuario = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultFeedBackContadorParaUsuario).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IFeedBackContadorParaUsuario>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        feedBackContadorParaUsuarioResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultFeedBackContadorParaUsuario = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultFeedBackContadorParaUsuario).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
