import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IDepartamentoContador } from '../departamento-contador.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../departamento-contador.test-samples';

import { DepartamentoContadorService } from './departamento-contador.service';

const requireRestSample: IDepartamentoContador = {
  ...sampleWithRequiredData,
};

describe('DepartamentoContador Service', () => {
  let service: DepartamentoContadorService;
  let httpMock: HttpTestingController;
  let expectedResult: IDepartamentoContador | IDepartamentoContador[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(DepartamentoContadorService);
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

    it('should create a DepartamentoContador', () => {
      const departamentoContador = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(departamentoContador).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a DepartamentoContador', () => {
      const departamentoContador = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(departamentoContador).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a DepartamentoContador', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of DepartamentoContador', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a DepartamentoContador', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addDepartamentoContadorToCollectionIfMissing', () => {
      it('should add a DepartamentoContador to an empty array', () => {
        const departamentoContador: IDepartamentoContador = sampleWithRequiredData;
        expectedResult = service.addDepartamentoContadorToCollectionIfMissing([], departamentoContador);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(departamentoContador);
      });

      it('should not add a DepartamentoContador to an array that contains it', () => {
        const departamentoContador: IDepartamentoContador = sampleWithRequiredData;
        const departamentoContadorCollection: IDepartamentoContador[] = [
          {
            ...departamentoContador,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addDepartamentoContadorToCollectionIfMissing(departamentoContadorCollection, departamentoContador);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a DepartamentoContador to an array that doesn't contain it", () => {
        const departamentoContador: IDepartamentoContador = sampleWithRequiredData;
        const departamentoContadorCollection: IDepartamentoContador[] = [sampleWithPartialData];
        expectedResult = service.addDepartamentoContadorToCollectionIfMissing(departamentoContadorCollection, departamentoContador);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(departamentoContador);
      });

      it('should add only unique DepartamentoContador to an array', () => {
        const departamentoContadorArray: IDepartamentoContador[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const departamentoContadorCollection: IDepartamentoContador[] = [sampleWithRequiredData];
        expectedResult = service.addDepartamentoContadorToCollectionIfMissing(departamentoContadorCollection, ...departamentoContadorArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const departamentoContador: IDepartamentoContador = sampleWithRequiredData;
        const departamentoContador2: IDepartamentoContador = sampleWithPartialData;
        expectedResult = service.addDepartamentoContadorToCollectionIfMissing([], departamentoContador, departamentoContador2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(departamentoContador);
        expect(expectedResult).toContain(departamentoContador2);
      });

      it('should accept null and undefined values', () => {
        const departamentoContador: IDepartamentoContador = sampleWithRequiredData;
        expectedResult = service.addDepartamentoContadorToCollectionIfMissing([], null, departamentoContador, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(departamentoContador);
      });

      it('should return initial array if no DepartamentoContador is added', () => {
        const departamentoContadorCollection: IDepartamentoContador[] = [sampleWithRequiredData];
        expectedResult = service.addDepartamentoContadorToCollectionIfMissing(departamentoContadorCollection, undefined, null);
        expect(expectedResult).toEqual(departamentoContadorCollection);
      });
    });

    describe('compareDepartamentoContador', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareDepartamentoContador(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareDepartamentoContador(entity1, entity2);
        const compareResult2 = service.compareDepartamentoContador(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareDepartamentoContador(entity1, entity2);
        const compareResult2 = service.compareDepartamentoContador(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareDepartamentoContador(entity1, entity2);
        const compareResult2 = service.compareDepartamentoContador(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
