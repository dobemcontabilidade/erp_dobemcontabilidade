import { TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { IAvaliacaoContador } from '../avaliacao-contador.model';
import { AvaliacaoContadorService } from '../service/avaliacao-contador.service';

import avaliacaoContadorResolve from './avaliacao-contador-routing-resolve.service';

describe('AvaliacaoContador routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: AvaliacaoContadorService;
  let resultAvaliacaoContador: IAvaliacaoContador | null | undefined;

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
    service = TestBed.inject(AvaliacaoContadorService);
    resultAvaliacaoContador = undefined;
  });

  describe('resolve', () => {
    it('should return IAvaliacaoContador returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        avaliacaoContadorResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultAvaliacaoContador = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultAvaliacaoContador).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        avaliacaoContadorResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultAvaliacaoContador = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultAvaliacaoContador).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IAvaliacaoContador>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        avaliacaoContadorResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultAvaliacaoContador = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultAvaliacaoContador).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
