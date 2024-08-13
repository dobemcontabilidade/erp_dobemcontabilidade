import { TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { IAnexoRequeridoServicoContabil } from '../anexo-requerido-servico-contabil.model';
import { AnexoRequeridoServicoContabilService } from '../service/anexo-requerido-servico-contabil.service';

import anexoRequeridoServicoContabilResolve from './anexo-requerido-servico-contabil-routing-resolve.service';

describe('AnexoRequeridoServicoContabil routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: AnexoRequeridoServicoContabilService;
  let resultAnexoRequeridoServicoContabil: IAnexoRequeridoServicoContabil | null | undefined;

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
    service = TestBed.inject(AnexoRequeridoServicoContabilService);
    resultAnexoRequeridoServicoContabil = undefined;
  });

  describe('resolve', () => {
    it('should return IAnexoRequeridoServicoContabil returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        anexoRequeridoServicoContabilResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultAnexoRequeridoServicoContabil = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultAnexoRequeridoServicoContabil).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        anexoRequeridoServicoContabilResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultAnexoRequeridoServicoContabil = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultAnexoRequeridoServicoContabil).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IAnexoRequeridoServicoContabil>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        anexoRequeridoServicoContabilResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultAnexoRequeridoServicoContabil = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultAnexoRequeridoServicoContabil).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
