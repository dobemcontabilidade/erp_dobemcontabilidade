import { TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { IContratoFuncionario } from '../contrato-funcionario.model';
import { ContratoFuncionarioService } from '../service/contrato-funcionario.service';

import contratoFuncionarioResolve from './contrato-funcionario-routing-resolve.service';

describe('ContratoFuncionario routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: ContratoFuncionarioService;
  let resultContratoFuncionario: IContratoFuncionario | null | undefined;

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
    service = TestBed.inject(ContratoFuncionarioService);
    resultContratoFuncionario = undefined;
  });

  describe('resolve', () => {
    it('should return IContratoFuncionario returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        contratoFuncionarioResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultContratoFuncionario = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultContratoFuncionario).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        contratoFuncionarioResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultContratoFuncionario = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultContratoFuncionario).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IContratoFuncionario>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        contratoFuncionarioResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultContratoFuncionario = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultContratoFuncionario).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
