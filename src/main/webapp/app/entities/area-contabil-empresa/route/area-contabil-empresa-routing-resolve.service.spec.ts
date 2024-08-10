import { TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { IAreaContabilEmpresa } from '../area-contabil-empresa.model';
import { AreaContabilEmpresaService } from '../service/area-contabil-empresa.service';

import areaContabilEmpresaResolve from './area-contabil-empresa-routing-resolve.service';

describe('AreaContabilEmpresa routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: AreaContabilEmpresaService;
  let resultAreaContabilEmpresa: IAreaContabilEmpresa | null | undefined;

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
    service = TestBed.inject(AreaContabilEmpresaService);
    resultAreaContabilEmpresa = undefined;
  });

  describe('resolve', () => {
    it('should return IAreaContabilEmpresa returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        areaContabilEmpresaResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultAreaContabilEmpresa = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultAreaContabilEmpresa).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        areaContabilEmpresaResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultAreaContabilEmpresa = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultAreaContabilEmpresa).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IAreaContabilEmpresa>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        areaContabilEmpresaResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultAreaContabilEmpresa = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultAreaContabilEmpresa).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
