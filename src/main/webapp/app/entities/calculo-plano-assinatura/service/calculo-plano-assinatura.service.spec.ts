import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { ICalculoPlanoAssinatura } from '../calculo-plano-assinatura.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../calculo-plano-assinatura.test-samples';

import { CalculoPlanoAssinaturaService } from './calculo-plano-assinatura.service';

const requireRestSample: ICalculoPlanoAssinatura = {
  ...sampleWithRequiredData,
};

describe('CalculoPlanoAssinatura Service', () => {
  let service: CalculoPlanoAssinaturaService;
  let httpMock: HttpTestingController;
  let expectedResult: ICalculoPlanoAssinatura | ICalculoPlanoAssinatura[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(CalculoPlanoAssinaturaService);
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

    it('should create a CalculoPlanoAssinatura', () => {
      const calculoPlanoAssinatura = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(calculoPlanoAssinatura).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a CalculoPlanoAssinatura', () => {
      const calculoPlanoAssinatura = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(calculoPlanoAssinatura).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a CalculoPlanoAssinatura', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of CalculoPlanoAssinatura', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a CalculoPlanoAssinatura', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCalculoPlanoAssinaturaToCollectionIfMissing', () => {
      it('should add a CalculoPlanoAssinatura to an empty array', () => {
        const calculoPlanoAssinatura: ICalculoPlanoAssinatura = sampleWithRequiredData;
        expectedResult = service.addCalculoPlanoAssinaturaToCollectionIfMissing([], calculoPlanoAssinatura);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(calculoPlanoAssinatura);
      });

      it('should not add a CalculoPlanoAssinatura to an array that contains it', () => {
        const calculoPlanoAssinatura: ICalculoPlanoAssinatura = sampleWithRequiredData;
        const calculoPlanoAssinaturaCollection: ICalculoPlanoAssinatura[] = [
          {
            ...calculoPlanoAssinatura,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCalculoPlanoAssinaturaToCollectionIfMissing(calculoPlanoAssinaturaCollection, calculoPlanoAssinatura);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a CalculoPlanoAssinatura to an array that doesn't contain it", () => {
        const calculoPlanoAssinatura: ICalculoPlanoAssinatura = sampleWithRequiredData;
        const calculoPlanoAssinaturaCollection: ICalculoPlanoAssinatura[] = [sampleWithPartialData];
        expectedResult = service.addCalculoPlanoAssinaturaToCollectionIfMissing(calculoPlanoAssinaturaCollection, calculoPlanoAssinatura);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(calculoPlanoAssinatura);
      });

      it('should add only unique CalculoPlanoAssinatura to an array', () => {
        const calculoPlanoAssinaturaArray: ICalculoPlanoAssinatura[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const calculoPlanoAssinaturaCollection: ICalculoPlanoAssinatura[] = [sampleWithRequiredData];
        expectedResult = service.addCalculoPlanoAssinaturaToCollectionIfMissing(
          calculoPlanoAssinaturaCollection,
          ...calculoPlanoAssinaturaArray,
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const calculoPlanoAssinatura: ICalculoPlanoAssinatura = sampleWithRequiredData;
        const calculoPlanoAssinatura2: ICalculoPlanoAssinatura = sampleWithPartialData;
        expectedResult = service.addCalculoPlanoAssinaturaToCollectionIfMissing([], calculoPlanoAssinatura, calculoPlanoAssinatura2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(calculoPlanoAssinatura);
        expect(expectedResult).toContain(calculoPlanoAssinatura2);
      });

      it('should accept null and undefined values', () => {
        const calculoPlanoAssinatura: ICalculoPlanoAssinatura = sampleWithRequiredData;
        expectedResult = service.addCalculoPlanoAssinaturaToCollectionIfMissing([], null, calculoPlanoAssinatura, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(calculoPlanoAssinatura);
      });

      it('should return initial array if no CalculoPlanoAssinatura is added', () => {
        const calculoPlanoAssinaturaCollection: ICalculoPlanoAssinatura[] = [sampleWithRequiredData];
        expectedResult = service.addCalculoPlanoAssinaturaToCollectionIfMissing(calculoPlanoAssinaturaCollection, undefined, null);
        expect(expectedResult).toEqual(calculoPlanoAssinaturaCollection);
      });
    });

    describe('compareCalculoPlanoAssinatura', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCalculoPlanoAssinatura(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareCalculoPlanoAssinatura(entity1, entity2);
        const compareResult2 = service.compareCalculoPlanoAssinatura(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareCalculoPlanoAssinatura(entity1, entity2);
        const compareResult2 = service.compareCalculoPlanoAssinatura(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareCalculoPlanoAssinatura(entity1, entity2);
        const compareResult2 = service.compareCalculoPlanoAssinatura(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
