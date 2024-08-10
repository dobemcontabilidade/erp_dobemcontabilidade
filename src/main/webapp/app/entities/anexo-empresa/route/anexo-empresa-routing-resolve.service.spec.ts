import { TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { IAnexoEmpresa } from '../anexo-empresa.model';
import { AnexoEmpresaService } from '../service/anexo-empresa.service';

import anexoEmpresaResolve from './anexo-empresa-routing-resolve.service';

describe('AnexoEmpresa routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: AnexoEmpresaService;
  let resultAnexoEmpresa: IAnexoEmpresa | null | undefined;

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
    service = TestBed.inject(AnexoEmpresaService);
    resultAnexoEmpresa = undefined;
  });

  describe('resolve', () => {
    it('should return IAnexoEmpresa returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        anexoEmpresaResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultAnexoEmpresa = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultAnexoEmpresa).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        anexoEmpresaResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultAnexoEmpresa = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultAnexoEmpresa).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IAnexoEmpresa>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        anexoEmpresaResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultAnexoEmpresa = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultAnexoEmpresa).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
