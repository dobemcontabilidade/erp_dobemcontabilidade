import { TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { ISubTarefaOrdemServico } from '../sub-tarefa-ordem-servico.model';
import { SubTarefaOrdemServicoService } from '../service/sub-tarefa-ordem-servico.service';

import subTarefaOrdemServicoResolve from './sub-tarefa-ordem-servico-routing-resolve.service';

describe('SubTarefaOrdemServico routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: SubTarefaOrdemServicoService;
  let resultSubTarefaOrdemServico: ISubTarefaOrdemServico | null | undefined;

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
    service = TestBed.inject(SubTarefaOrdemServicoService);
    resultSubTarefaOrdemServico = undefined;
  });

  describe('resolve', () => {
    it('should return ISubTarefaOrdemServico returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        subTarefaOrdemServicoResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultSubTarefaOrdemServico = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultSubTarefaOrdemServico).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        subTarefaOrdemServicoResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultSubTarefaOrdemServico = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultSubTarefaOrdemServico).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<ISubTarefaOrdemServico>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        subTarefaOrdemServicoResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultSubTarefaOrdemServico = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultSubTarefaOrdemServico).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
