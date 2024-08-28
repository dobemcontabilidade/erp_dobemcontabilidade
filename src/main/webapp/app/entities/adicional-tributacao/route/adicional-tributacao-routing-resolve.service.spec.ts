import { TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { IAdicionalTributacao } from '../adicional-tributacao.model';
import { AdicionalTributacaoService } from '../service/adicional-tributacao.service';

import adicionalTributacaoResolve from './adicional-tributacao-routing-resolve.service';

describe('AdicionalTributacao routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: AdicionalTributacaoService;
  let resultAdicionalTributacao: IAdicionalTributacao | null | undefined;

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
    service = TestBed.inject(AdicionalTributacaoService);
    resultAdicionalTributacao = undefined;
  });

  describe('resolve', () => {
    it('should return IAdicionalTributacao returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        adicionalTributacaoResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultAdicionalTributacao = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultAdicionalTributacao).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        adicionalTributacaoResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultAdicionalTributacao = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultAdicionalTributacao).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IAdicionalTributacao>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        adicionalTributacaoResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultAdicionalTributacao = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultAdicionalTributacao).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
