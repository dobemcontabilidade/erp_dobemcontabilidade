import { TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { IAnexoRequeridoEmpresa } from '../anexo-requerido-empresa.model';
import { AnexoRequeridoEmpresaService } from '../service/anexo-requerido-empresa.service';

import anexoRequeridoEmpresaResolve from './anexo-requerido-empresa-routing-resolve.service';

describe('AnexoRequeridoEmpresa routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: AnexoRequeridoEmpresaService;
  let resultAnexoRequeridoEmpresa: IAnexoRequeridoEmpresa | null | undefined;

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
    service = TestBed.inject(AnexoRequeridoEmpresaService);
    resultAnexoRequeridoEmpresa = undefined;
  });

  describe('resolve', () => {
    it('should return IAnexoRequeridoEmpresa returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        anexoRequeridoEmpresaResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultAnexoRequeridoEmpresa = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultAnexoRequeridoEmpresa).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        anexoRequeridoEmpresaResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultAnexoRequeridoEmpresa = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultAnexoRequeridoEmpresa).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IAnexoRequeridoEmpresa>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        anexoRequeridoEmpresaResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultAnexoRequeridoEmpresa = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultAnexoRequeridoEmpresa).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
