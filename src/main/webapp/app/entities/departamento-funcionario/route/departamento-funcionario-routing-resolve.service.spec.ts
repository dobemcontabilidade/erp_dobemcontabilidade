import { TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { IDepartamentoFuncionario } from '../departamento-funcionario.model';
import { DepartamentoFuncionarioService } from '../service/departamento-funcionario.service';

import departamentoFuncionarioResolve from './departamento-funcionario-routing-resolve.service';

describe('DepartamentoFuncionario routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: DepartamentoFuncionarioService;
  let resultDepartamentoFuncionario: IDepartamentoFuncionario | null | undefined;

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
    service = TestBed.inject(DepartamentoFuncionarioService);
    resultDepartamentoFuncionario = undefined;
  });

  describe('resolve', () => {
    it('should return IDepartamentoFuncionario returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        departamentoFuncionarioResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultDepartamentoFuncionario = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultDepartamentoFuncionario).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        departamentoFuncionarioResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultDepartamentoFuncionario = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultDepartamentoFuncionario).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IDepartamentoFuncionario>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        departamentoFuncionarioResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultDepartamentoFuncionario = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultDepartamentoFuncionario).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
