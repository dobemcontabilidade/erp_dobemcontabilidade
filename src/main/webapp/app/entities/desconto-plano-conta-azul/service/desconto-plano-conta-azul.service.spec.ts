import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IDescontoPlanoContaAzul } from '../desconto-plano-conta-azul.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../desconto-plano-conta-azul.test-samples';

import { DescontoPlanoContaAzulService } from './desconto-plano-conta-azul.service';

const requireRestSample: IDescontoPlanoContaAzul = {
  ...sampleWithRequiredData,
};

describe('DescontoPlanoContaAzul Service', () => {
  let service: DescontoPlanoContaAzulService;
  let httpMock: HttpTestingController;
  let expectedResult: IDescontoPlanoContaAzul | IDescontoPlanoContaAzul[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(DescontoPlanoContaAzulService);
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

    it('should create a DescontoPlanoContaAzul', () => {
      const descontoPlanoContaAzul = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(descontoPlanoContaAzul).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a DescontoPlanoContaAzul', () => {
      const descontoPlanoContaAzul = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(descontoPlanoContaAzul).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a DescontoPlanoContaAzul', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of DescontoPlanoContaAzul', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a DescontoPlanoContaAzul', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addDescontoPlanoContaAzulToCollectionIfMissing', () => {
      it('should add a DescontoPlanoContaAzul to an empty array', () => {
        const descontoPlanoContaAzul: IDescontoPlanoContaAzul = sampleWithRequiredData;
        expectedResult = service.addDescontoPlanoContaAzulToCollectionIfMissing([], descontoPlanoContaAzul);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(descontoPlanoContaAzul);
      });

      it('should not add a DescontoPlanoContaAzul to an array that contains it', () => {
        const descontoPlanoContaAzul: IDescontoPlanoContaAzul = sampleWithRequiredData;
        const descontoPlanoContaAzulCollection: IDescontoPlanoContaAzul[] = [
          {
            ...descontoPlanoContaAzul,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addDescontoPlanoContaAzulToCollectionIfMissing(descontoPlanoContaAzulCollection, descontoPlanoContaAzul);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a DescontoPlanoContaAzul to an array that doesn't contain it", () => {
        const descontoPlanoContaAzul: IDescontoPlanoContaAzul = sampleWithRequiredData;
        const descontoPlanoContaAzulCollection: IDescontoPlanoContaAzul[] = [sampleWithPartialData];
        expectedResult = service.addDescontoPlanoContaAzulToCollectionIfMissing(descontoPlanoContaAzulCollection, descontoPlanoContaAzul);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(descontoPlanoContaAzul);
      });

      it('should add only unique DescontoPlanoContaAzul to an array', () => {
        const descontoPlanoContaAzulArray: IDescontoPlanoContaAzul[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const descontoPlanoContaAzulCollection: IDescontoPlanoContaAzul[] = [sampleWithRequiredData];
        expectedResult = service.addDescontoPlanoContaAzulToCollectionIfMissing(
          descontoPlanoContaAzulCollection,
          ...descontoPlanoContaAzulArray,
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const descontoPlanoContaAzul: IDescontoPlanoContaAzul = sampleWithRequiredData;
        const descontoPlanoContaAzul2: IDescontoPlanoContaAzul = sampleWithPartialData;
        expectedResult = service.addDescontoPlanoContaAzulToCollectionIfMissing([], descontoPlanoContaAzul, descontoPlanoContaAzul2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(descontoPlanoContaAzul);
        expect(expectedResult).toContain(descontoPlanoContaAzul2);
      });

      it('should accept null and undefined values', () => {
        const descontoPlanoContaAzul: IDescontoPlanoContaAzul = sampleWithRequiredData;
        expectedResult = service.addDescontoPlanoContaAzulToCollectionIfMissing([], null, descontoPlanoContaAzul, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(descontoPlanoContaAzul);
      });

      it('should return initial array if no DescontoPlanoContaAzul is added', () => {
        const descontoPlanoContaAzulCollection: IDescontoPlanoContaAzul[] = [sampleWithRequiredData];
        expectedResult = service.addDescontoPlanoContaAzulToCollectionIfMissing(descontoPlanoContaAzulCollection, undefined, null);
        expect(expectedResult).toEqual(descontoPlanoContaAzulCollection);
      });
    });

    describe('compareDescontoPlanoContaAzul', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareDescontoPlanoContaAzul(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareDescontoPlanoContaAzul(entity1, entity2);
        const compareResult2 = service.compareDescontoPlanoContaAzul(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareDescontoPlanoContaAzul(entity1, entity2);
        const compareResult2 = service.compareDescontoPlanoContaAzul(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareDescontoPlanoContaAzul(entity1, entity2);
        const compareResult2 = service.compareDescontoPlanoContaAzul(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
