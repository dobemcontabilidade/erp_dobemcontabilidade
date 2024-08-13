import { TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { IFeedBackUsuarioParaContador } from '../feed-back-usuario-para-contador.model';
import { FeedBackUsuarioParaContadorService } from '../service/feed-back-usuario-para-contador.service';

import feedBackUsuarioParaContadorResolve from './feed-back-usuario-para-contador-routing-resolve.service';

describe('FeedBackUsuarioParaContador routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: FeedBackUsuarioParaContadorService;
  let resultFeedBackUsuarioParaContador: IFeedBackUsuarioParaContador | null | undefined;

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
    service = TestBed.inject(FeedBackUsuarioParaContadorService);
    resultFeedBackUsuarioParaContador = undefined;
  });

  describe('resolve', () => {
    it('should return IFeedBackUsuarioParaContador returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        feedBackUsuarioParaContadorResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultFeedBackUsuarioParaContador = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultFeedBackUsuarioParaContador).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        feedBackUsuarioParaContadorResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultFeedBackUsuarioParaContador = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultFeedBackUsuarioParaContador).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IFeedBackUsuarioParaContador>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        feedBackUsuarioParaContadorResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultFeedBackUsuarioParaContador = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultFeedBackUsuarioParaContador).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
