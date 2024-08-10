import { TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { ITermoAdesaoContador } from '../termo-adesao-contador.model';
import { TermoAdesaoContadorService } from '../service/termo-adesao-contador.service';

import termoAdesaoContadorResolve from './termo-adesao-contador-routing-resolve.service';

describe('TermoAdesaoContador routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: TermoAdesaoContadorService;
  let resultTermoAdesaoContador: ITermoAdesaoContador | null | undefined;

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
    service = TestBed.inject(TermoAdesaoContadorService);
    resultTermoAdesaoContador = undefined;
  });

  describe('resolve', () => {
    it('should return ITermoAdesaoContador returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        termoAdesaoContadorResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultTermoAdesaoContador = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultTermoAdesaoContador).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        termoAdesaoContadorResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultTermoAdesaoContador = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultTermoAdesaoContador).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<ITermoAdesaoContador>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        termoAdesaoContadorResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultTermoAdesaoContador = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultTermoAdesaoContador).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
