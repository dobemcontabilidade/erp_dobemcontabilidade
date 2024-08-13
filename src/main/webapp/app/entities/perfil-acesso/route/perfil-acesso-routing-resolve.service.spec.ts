import { TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { IPerfilAcesso } from '../perfil-acesso.model';
import { PerfilAcessoService } from '../service/perfil-acesso.service';

import perfilAcessoResolve from './perfil-acesso-routing-resolve.service';

describe('PerfilAcesso routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: PerfilAcessoService;
  let resultPerfilAcesso: IPerfilAcesso | null | undefined;

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
    service = TestBed.inject(PerfilAcessoService);
    resultPerfilAcesso = undefined;
  });

  describe('resolve', () => {
    it('should return IPerfilAcesso returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        perfilAcessoResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultPerfilAcesso = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultPerfilAcesso).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        perfilAcessoResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultPerfilAcesso = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultPerfilAcesso).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IPerfilAcesso>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        perfilAcessoResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultPerfilAcesso = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultPerfilAcesso).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
