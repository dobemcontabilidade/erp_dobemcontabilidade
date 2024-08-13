import { TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { IAtorAvaliado } from '../ator-avaliado.model';
import { AtorAvaliadoService } from '../service/ator-avaliado.service';

import atorAvaliadoResolve from './ator-avaliado-routing-resolve.service';

describe('AtorAvaliado routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: AtorAvaliadoService;
  let resultAtorAvaliado: IAtorAvaliado | null | undefined;

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
    service = TestBed.inject(AtorAvaliadoService);
    resultAtorAvaliado = undefined;
  });

  describe('resolve', () => {
    it('should return IAtorAvaliado returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        atorAvaliadoResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultAtorAvaliado = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultAtorAvaliado).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        atorAvaliadoResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultAtorAvaliado = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultAtorAvaliado).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IAtorAvaliado>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        atorAvaliadoResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultAtorAvaliado = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultAtorAvaliado).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
