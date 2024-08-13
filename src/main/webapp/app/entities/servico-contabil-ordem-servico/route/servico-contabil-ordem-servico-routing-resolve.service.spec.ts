import { TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { IServicoContabilOrdemServico } from '../servico-contabil-ordem-servico.model';
import { ServicoContabilOrdemServicoService } from '../service/servico-contabil-ordem-servico.service';

import servicoContabilOrdemServicoResolve from './servico-contabil-ordem-servico-routing-resolve.service';

describe('ServicoContabilOrdemServico routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: ServicoContabilOrdemServicoService;
  let resultServicoContabilOrdemServico: IServicoContabilOrdemServico | null | undefined;

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
    service = TestBed.inject(ServicoContabilOrdemServicoService);
    resultServicoContabilOrdemServico = undefined;
  });

  describe('resolve', () => {
    it('should return IServicoContabilOrdemServico returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        servicoContabilOrdemServicoResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultServicoContabilOrdemServico = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultServicoContabilOrdemServico).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        servicoContabilOrdemServicoResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultServicoContabilOrdemServico = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultServicoContabilOrdemServico).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IServicoContabilOrdemServico>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        servicoContabilOrdemServicoResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultServicoContabilOrdemServico = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultServicoContabilOrdemServico).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
