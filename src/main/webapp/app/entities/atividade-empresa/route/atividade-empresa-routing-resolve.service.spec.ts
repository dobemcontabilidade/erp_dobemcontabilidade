import { TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { IAtividadeEmpresa } from '../atividade-empresa.model';
import { AtividadeEmpresaService } from '../service/atividade-empresa.service';

import atividadeEmpresaResolve from './atividade-empresa-routing-resolve.service';

describe('AtividadeEmpresa routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: AtividadeEmpresaService;
  let resultAtividadeEmpresa: IAtividadeEmpresa | null | undefined;

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
    service = TestBed.inject(AtividadeEmpresaService);
    resultAtividadeEmpresa = undefined;
  });

  describe('resolve', () => {
    it('should return IAtividadeEmpresa returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        atividadeEmpresaResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultAtividadeEmpresa = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultAtividadeEmpresa).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        atividadeEmpresaResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultAtividadeEmpresa = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultAtividadeEmpresa).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IAtividadeEmpresa>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        atividadeEmpresaResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultAtividadeEmpresa = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultAtividadeEmpresa).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
