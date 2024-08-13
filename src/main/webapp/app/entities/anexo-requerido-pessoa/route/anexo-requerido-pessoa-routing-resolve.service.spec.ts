import { TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { IAnexoRequeridoPessoa } from '../anexo-requerido-pessoa.model';
import { AnexoRequeridoPessoaService } from '../service/anexo-requerido-pessoa.service';

import anexoRequeridoPessoaResolve from './anexo-requerido-pessoa-routing-resolve.service';

describe('AnexoRequeridoPessoa routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: AnexoRequeridoPessoaService;
  let resultAnexoRequeridoPessoa: IAnexoRequeridoPessoa | null | undefined;

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
    service = TestBed.inject(AnexoRequeridoPessoaService);
    resultAnexoRequeridoPessoa = undefined;
  });

  describe('resolve', () => {
    it('should return IAnexoRequeridoPessoa returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        anexoRequeridoPessoaResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultAnexoRequeridoPessoa = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultAnexoRequeridoPessoa).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        anexoRequeridoPessoaResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultAnexoRequeridoPessoa = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultAnexoRequeridoPessoa).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IAnexoRequeridoPessoa>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        anexoRequeridoPessoaResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultAnexoRequeridoPessoa = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultAnexoRequeridoPessoa).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
