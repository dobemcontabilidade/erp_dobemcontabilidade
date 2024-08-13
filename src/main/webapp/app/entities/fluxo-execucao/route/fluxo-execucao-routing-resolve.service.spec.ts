import { TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { IFluxoExecucao } from '../fluxo-execucao.model';
import { FluxoExecucaoService } from '../service/fluxo-execucao.service';

import fluxoExecucaoResolve from './fluxo-execucao-routing-resolve.service';

describe('FluxoExecucao routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: FluxoExecucaoService;
  let resultFluxoExecucao: IFluxoExecucao | null | undefined;

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
    service = TestBed.inject(FluxoExecucaoService);
    resultFluxoExecucao = undefined;
  });

  describe('resolve', () => {
    it('should return IFluxoExecucao returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        fluxoExecucaoResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultFluxoExecucao = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultFluxoExecucao).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        fluxoExecucaoResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultFluxoExecucao = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultFluxoExecucao).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IFluxoExecucao>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        fluxoExecucaoResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultFluxoExecucao = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultFluxoExecucao).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
