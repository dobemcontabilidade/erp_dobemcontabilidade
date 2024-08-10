import { TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { IDepartamentoContador } from '../departamento-contador.model';
import { DepartamentoContadorService } from '../service/departamento-contador.service';

import departamentoContadorResolve from './departamento-contador-routing-resolve.service';

describe('DepartamentoContador routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: DepartamentoContadorService;
  let resultDepartamentoContador: IDepartamentoContador | null | undefined;

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
    service = TestBed.inject(DepartamentoContadorService);
    resultDepartamentoContador = undefined;
  });

  describe('resolve', () => {
    it('should return IDepartamentoContador returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        departamentoContadorResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultDepartamentoContador = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultDepartamentoContador).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        departamentoContadorResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultDepartamentoContador = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultDepartamentoContador).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IDepartamentoContador>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        departamentoContadorResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultDepartamentoContador = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultDepartamentoContador).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
