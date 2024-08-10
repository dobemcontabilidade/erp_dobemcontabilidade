import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IDepartamentoEmpresa } from '../departamento-empresa.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../departamento-empresa.test-samples';

import { DepartamentoEmpresaService } from './departamento-empresa.service';

const requireRestSample: IDepartamentoEmpresa = {
  ...sampleWithRequiredData,
};

describe('DepartamentoEmpresa Service', () => {
  let service: DepartamentoEmpresaService;
  let httpMock: HttpTestingController;
  let expectedResult: IDepartamentoEmpresa | IDepartamentoEmpresa[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(DepartamentoEmpresaService);
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

    it('should create a DepartamentoEmpresa', () => {
      const departamentoEmpresa = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(departamentoEmpresa).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a DepartamentoEmpresa', () => {
      const departamentoEmpresa = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(departamentoEmpresa).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a DepartamentoEmpresa', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of DepartamentoEmpresa', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a DepartamentoEmpresa', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addDepartamentoEmpresaToCollectionIfMissing', () => {
      it('should add a DepartamentoEmpresa to an empty array', () => {
        const departamentoEmpresa: IDepartamentoEmpresa = sampleWithRequiredData;
        expectedResult = service.addDepartamentoEmpresaToCollectionIfMissing([], departamentoEmpresa);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(departamentoEmpresa);
      });

      it('should not add a DepartamentoEmpresa to an array that contains it', () => {
        const departamentoEmpresa: IDepartamentoEmpresa = sampleWithRequiredData;
        const departamentoEmpresaCollection: IDepartamentoEmpresa[] = [
          {
            ...departamentoEmpresa,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addDepartamentoEmpresaToCollectionIfMissing(departamentoEmpresaCollection, departamentoEmpresa);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a DepartamentoEmpresa to an array that doesn't contain it", () => {
        const departamentoEmpresa: IDepartamentoEmpresa = sampleWithRequiredData;
        const departamentoEmpresaCollection: IDepartamentoEmpresa[] = [sampleWithPartialData];
        expectedResult = service.addDepartamentoEmpresaToCollectionIfMissing(departamentoEmpresaCollection, departamentoEmpresa);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(departamentoEmpresa);
      });

      it('should add only unique DepartamentoEmpresa to an array', () => {
        const departamentoEmpresaArray: IDepartamentoEmpresa[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const departamentoEmpresaCollection: IDepartamentoEmpresa[] = [sampleWithRequiredData];
        expectedResult = service.addDepartamentoEmpresaToCollectionIfMissing(departamentoEmpresaCollection, ...departamentoEmpresaArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const departamentoEmpresa: IDepartamentoEmpresa = sampleWithRequiredData;
        const departamentoEmpresa2: IDepartamentoEmpresa = sampleWithPartialData;
        expectedResult = service.addDepartamentoEmpresaToCollectionIfMissing([], departamentoEmpresa, departamentoEmpresa2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(departamentoEmpresa);
        expect(expectedResult).toContain(departamentoEmpresa2);
      });

      it('should accept null and undefined values', () => {
        const departamentoEmpresa: IDepartamentoEmpresa = sampleWithRequiredData;
        expectedResult = service.addDepartamentoEmpresaToCollectionIfMissing([], null, departamentoEmpresa, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(departamentoEmpresa);
      });

      it('should return initial array if no DepartamentoEmpresa is added', () => {
        const departamentoEmpresaCollection: IDepartamentoEmpresa[] = [sampleWithRequiredData];
        expectedResult = service.addDepartamentoEmpresaToCollectionIfMissing(departamentoEmpresaCollection, undefined, null);
        expect(expectedResult).toEqual(departamentoEmpresaCollection);
      });
    });

    describe('compareDepartamentoEmpresa', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareDepartamentoEmpresa(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareDepartamentoEmpresa(entity1, entity2);
        const compareResult2 = service.compareDepartamentoEmpresa(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareDepartamentoEmpresa(entity1, entity2);
        const compareResult2 = service.compareDepartamentoEmpresa(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareDepartamentoEmpresa(entity1, entity2);
        const compareResult2 = service.compareDepartamentoEmpresa(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
