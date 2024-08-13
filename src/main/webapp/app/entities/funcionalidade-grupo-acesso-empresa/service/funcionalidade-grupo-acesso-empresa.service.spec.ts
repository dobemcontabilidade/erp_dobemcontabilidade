import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IFuncionalidadeGrupoAcessoEmpresa } from '../funcionalidade-grupo-acesso-empresa.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../funcionalidade-grupo-acesso-empresa.test-samples';

import {
  FuncionalidadeGrupoAcessoEmpresaService,
  RestFuncionalidadeGrupoAcessoEmpresa,
} from './funcionalidade-grupo-acesso-empresa.service';

const requireRestSample: RestFuncionalidadeGrupoAcessoEmpresa = {
  ...sampleWithRequiredData,
  dataExpiracao: sampleWithRequiredData.dataExpiracao?.toJSON(),
};

describe('FuncionalidadeGrupoAcessoEmpresa Service', () => {
  let service: FuncionalidadeGrupoAcessoEmpresaService;
  let httpMock: HttpTestingController;
  let expectedResult: IFuncionalidadeGrupoAcessoEmpresa | IFuncionalidadeGrupoAcessoEmpresa[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(FuncionalidadeGrupoAcessoEmpresaService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a FuncionalidadeGrupoAcessoEmpresa', () => {
      const funcionalidadeGrupoAcessoEmpresa = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(funcionalidadeGrupoAcessoEmpresa).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a FuncionalidadeGrupoAcessoEmpresa', () => {
      const funcionalidadeGrupoAcessoEmpresa = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(funcionalidadeGrupoAcessoEmpresa).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a FuncionalidadeGrupoAcessoEmpresa', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of FuncionalidadeGrupoAcessoEmpresa', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a FuncionalidadeGrupoAcessoEmpresa', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addFuncionalidadeGrupoAcessoEmpresaToCollectionIfMissing', () => {
      it('should add a FuncionalidadeGrupoAcessoEmpresa to an empty array', () => {
        const funcionalidadeGrupoAcessoEmpresa: IFuncionalidadeGrupoAcessoEmpresa = sampleWithRequiredData;
        expectedResult = service.addFuncionalidadeGrupoAcessoEmpresaToCollectionIfMissing([], funcionalidadeGrupoAcessoEmpresa);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(funcionalidadeGrupoAcessoEmpresa);
      });

      it('should not add a FuncionalidadeGrupoAcessoEmpresa to an array that contains it', () => {
        const funcionalidadeGrupoAcessoEmpresa: IFuncionalidadeGrupoAcessoEmpresa = sampleWithRequiredData;
        const funcionalidadeGrupoAcessoEmpresaCollection: IFuncionalidadeGrupoAcessoEmpresa[] = [
          {
            ...funcionalidadeGrupoAcessoEmpresa,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addFuncionalidadeGrupoAcessoEmpresaToCollectionIfMissing(
          funcionalidadeGrupoAcessoEmpresaCollection,
          funcionalidadeGrupoAcessoEmpresa,
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a FuncionalidadeGrupoAcessoEmpresa to an array that doesn't contain it", () => {
        const funcionalidadeGrupoAcessoEmpresa: IFuncionalidadeGrupoAcessoEmpresa = sampleWithRequiredData;
        const funcionalidadeGrupoAcessoEmpresaCollection: IFuncionalidadeGrupoAcessoEmpresa[] = [sampleWithPartialData];
        expectedResult = service.addFuncionalidadeGrupoAcessoEmpresaToCollectionIfMissing(
          funcionalidadeGrupoAcessoEmpresaCollection,
          funcionalidadeGrupoAcessoEmpresa,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(funcionalidadeGrupoAcessoEmpresa);
      });

      it('should add only unique FuncionalidadeGrupoAcessoEmpresa to an array', () => {
        const funcionalidadeGrupoAcessoEmpresaArray: IFuncionalidadeGrupoAcessoEmpresa[] = [
          sampleWithRequiredData,
          sampleWithPartialData,
          sampleWithFullData,
        ];
        const funcionalidadeGrupoAcessoEmpresaCollection: IFuncionalidadeGrupoAcessoEmpresa[] = [sampleWithRequiredData];
        expectedResult = service.addFuncionalidadeGrupoAcessoEmpresaToCollectionIfMissing(
          funcionalidadeGrupoAcessoEmpresaCollection,
          ...funcionalidadeGrupoAcessoEmpresaArray,
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const funcionalidadeGrupoAcessoEmpresa: IFuncionalidadeGrupoAcessoEmpresa = sampleWithRequiredData;
        const funcionalidadeGrupoAcessoEmpresa2: IFuncionalidadeGrupoAcessoEmpresa = sampleWithPartialData;
        expectedResult = service.addFuncionalidadeGrupoAcessoEmpresaToCollectionIfMissing(
          [],
          funcionalidadeGrupoAcessoEmpresa,
          funcionalidadeGrupoAcessoEmpresa2,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(funcionalidadeGrupoAcessoEmpresa);
        expect(expectedResult).toContain(funcionalidadeGrupoAcessoEmpresa2);
      });

      it('should accept null and undefined values', () => {
        const funcionalidadeGrupoAcessoEmpresa: IFuncionalidadeGrupoAcessoEmpresa = sampleWithRequiredData;
        expectedResult = service.addFuncionalidadeGrupoAcessoEmpresaToCollectionIfMissing(
          [],
          null,
          funcionalidadeGrupoAcessoEmpresa,
          undefined,
        );
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(funcionalidadeGrupoAcessoEmpresa);
      });

      it('should return initial array if no FuncionalidadeGrupoAcessoEmpresa is added', () => {
        const funcionalidadeGrupoAcessoEmpresaCollection: IFuncionalidadeGrupoAcessoEmpresa[] = [sampleWithRequiredData];
        expectedResult = service.addFuncionalidadeGrupoAcessoEmpresaToCollectionIfMissing(
          funcionalidadeGrupoAcessoEmpresaCollection,
          undefined,
          null,
        );
        expect(expectedResult).toEqual(funcionalidadeGrupoAcessoEmpresaCollection);
      });
    });

    describe('compareFuncionalidadeGrupoAcessoEmpresa', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareFuncionalidadeGrupoAcessoEmpresa(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareFuncionalidadeGrupoAcessoEmpresa(entity1, entity2);
        const compareResult2 = service.compareFuncionalidadeGrupoAcessoEmpresa(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareFuncionalidadeGrupoAcessoEmpresa(entity1, entity2);
        const compareResult2 = service.compareFuncionalidadeGrupoAcessoEmpresa(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareFuncionalidadeGrupoAcessoEmpresa(entity1, entity2);
        const compareResult2 = service.compareFuncionalidadeGrupoAcessoEmpresa(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
