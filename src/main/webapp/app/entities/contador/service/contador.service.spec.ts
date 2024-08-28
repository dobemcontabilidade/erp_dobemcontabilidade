import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IContador } from '../contador.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../contador.test-samples';

import { ContadorService } from './contador.service';

const requireRestSample: IContador = {
  ...sampleWithRequiredData,
};

describe('Contador Service', () => {
  let service: ContadorService;
  let httpMock: HttpTestingController;
  let expectedResult: IContador | IContador[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(ContadorService);
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

    it('should create a Contador', () => {
      const contador = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(contador).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Contador', () => {
      const contador = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(contador).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Contador', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Contador', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Contador', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addContadorToCollectionIfMissing', () => {
      it('should add a Contador to an empty array', () => {
        const contador: IContador = sampleWithRequiredData;
        expectedResult = service.addContadorToCollectionIfMissing([], contador);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(contador);
      });

      it('should not add a Contador to an array that contains it', () => {
        const contador: IContador = sampleWithRequiredData;
        const contadorCollection: IContador[] = [
          {
            ...contador,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addContadorToCollectionIfMissing(contadorCollection, contador);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Contador to an array that doesn't contain it", () => {
        const contador: IContador = sampleWithRequiredData;
        const contadorCollection: IContador[] = [sampleWithPartialData];
        expectedResult = service.addContadorToCollectionIfMissing(contadorCollection, contador);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(contador);
      });

      it('should add only unique Contador to an array', () => {
        const contadorArray: IContador[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const contadorCollection: IContador[] = [sampleWithRequiredData];
        expectedResult = service.addContadorToCollectionIfMissing(contadorCollection, ...contadorArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const contador: IContador = sampleWithRequiredData;
        const contador2: IContador = sampleWithPartialData;
        expectedResult = service.addContadorToCollectionIfMissing([], contador, contador2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(contador);
        expect(expectedResult).toContain(contador2);
      });

      it('should accept null and undefined values', () => {
        const contador: IContador = sampleWithRequiredData;
        expectedResult = service.addContadorToCollectionIfMissing([], null, contador, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(contador);
      });

      it('should return initial array if no Contador is added', () => {
        const contadorCollection: IContador[] = [sampleWithRequiredData];
        expectedResult = service.addContadorToCollectionIfMissing(contadorCollection, undefined, null);
        expect(expectedResult).toEqual(contadorCollection);
      });
    });

    describe('compareContador', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareContador(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareContador(entity1, entity2);
        const compareResult2 = service.compareContador(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareContador(entity1, entity2);
        const compareResult2 = service.compareContador(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareContador(entity1, entity2);
        const compareResult2 = service.compareContador(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
