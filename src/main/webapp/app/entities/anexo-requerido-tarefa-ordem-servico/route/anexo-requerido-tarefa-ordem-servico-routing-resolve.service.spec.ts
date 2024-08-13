import { TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { IAnexoRequeridoTarefaOrdemServico } from '../anexo-requerido-tarefa-ordem-servico.model';
import { AnexoRequeridoTarefaOrdemServicoService } from '../service/anexo-requerido-tarefa-ordem-servico.service';

import anexoRequeridoTarefaOrdemServicoResolve from './anexo-requerido-tarefa-ordem-servico-routing-resolve.service';

describe('AnexoRequeridoTarefaOrdemServico routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: AnexoRequeridoTarefaOrdemServicoService;
  let resultAnexoRequeridoTarefaOrdemServico: IAnexoRequeridoTarefaOrdemServico | null | undefined;

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
    service = TestBed.inject(AnexoRequeridoTarefaOrdemServicoService);
    resultAnexoRequeridoTarefaOrdemServico = undefined;
  });

  describe('resolve', () => {
    it('should return IAnexoRequeridoTarefaOrdemServico returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        anexoRequeridoTarefaOrdemServicoResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultAnexoRequeridoTarefaOrdemServico = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultAnexoRequeridoTarefaOrdemServico).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        anexoRequeridoTarefaOrdemServicoResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultAnexoRequeridoTarefaOrdemServico = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultAnexoRequeridoTarefaOrdemServico).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IAnexoRequeridoTarefaOrdemServico>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        anexoRequeridoTarefaOrdemServicoResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultAnexoRequeridoTarefaOrdemServico = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultAnexoRequeridoTarefaOrdemServico).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
