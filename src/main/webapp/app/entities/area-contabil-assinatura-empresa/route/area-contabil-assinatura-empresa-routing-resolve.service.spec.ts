import { TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { IAreaContabilAssinaturaEmpresa } from '../area-contabil-assinatura-empresa.model';
import { AreaContabilAssinaturaEmpresaService } from '../service/area-contabil-assinatura-empresa.service';

import areaContabilAssinaturaEmpresaResolve from './area-contabil-assinatura-empresa-routing-resolve.service';

describe('AreaContabilAssinaturaEmpresa routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: AreaContabilAssinaturaEmpresaService;
  let resultAreaContabilAssinaturaEmpresa: IAreaContabilAssinaturaEmpresa | null | undefined;

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
    service = TestBed.inject(AreaContabilAssinaturaEmpresaService);
    resultAreaContabilAssinaturaEmpresa = undefined;
  });

  describe('resolve', () => {
    it('should return IAreaContabilAssinaturaEmpresa returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        areaContabilAssinaturaEmpresaResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultAreaContabilAssinaturaEmpresa = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultAreaContabilAssinaturaEmpresa).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        areaContabilAssinaturaEmpresaResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultAreaContabilAssinaturaEmpresa = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultAreaContabilAssinaturaEmpresa).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IAreaContabilAssinaturaEmpresa>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        areaContabilAssinaturaEmpresaResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultAreaContabilAssinaturaEmpresa = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultAreaContabilAssinaturaEmpresa).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
