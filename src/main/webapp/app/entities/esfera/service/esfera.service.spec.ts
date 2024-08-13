import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IEsfera } from '../esfera.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../esfera.test-samples';

import { EsferaService } from './esfera.service';

const requireRestSample: IEsfera = {
  ...sampleWithRequiredData,
};

describe('Esfera Service', () => {
  let service: EsferaService;
  let httpMock: HttpTestingController;
  let expectedResult: IEsfera | IEsfera[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(EsferaService);
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

    it('should create a Esfera', () => {
      const esfera = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(esfera).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Esfera', () => {
      const esfera = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(esfera).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Esfera', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Esfera', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Esfera', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addEsferaToCollectionIfMissing', () => {
      it('should add a Esfera to an empty array', () => {
        const esfera: IEsfera = sampleWithRequiredData;
        expectedResult = service.addEsferaToCollectionIfMissing([], esfera);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(esfera);
      });

      it('should not add a Esfera to an array that contains it', () => {
        const esfera: IEsfera = sampleWithRequiredData;
        const esferaCollection: IEsfera[] = [
          {
            ...esfera,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addEsferaToCollectionIfMissing(esferaCollection, esfera);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Esfera to an array that doesn't contain it", () => {
        const esfera: IEsfera = sampleWithRequiredData;
        const esferaCollection: IEsfera[] = [sampleWithPartialData];
        expectedResult = service.addEsferaToCollectionIfMissing(esferaCollection, esfera);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(esfera);
      });

      it('should add only unique Esfera to an array', () => {
        const esferaArray: IEsfera[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const esferaCollection: IEsfera[] = [sampleWithRequiredData];
        expectedResult = service.addEsferaToCollectionIfMissing(esferaCollection, ...esferaArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const esfera: IEsfera = sampleWithRequiredData;
        const esfera2: IEsfera = sampleWithPartialData;
        expectedResult = service.addEsferaToCollectionIfMissing([], esfera, esfera2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(esfera);
        expect(expectedResult).toContain(esfera2);
      });

      it('should accept null and undefined values', () => {
        const esfera: IEsfera = sampleWithRequiredData;
        expectedResult = service.addEsferaToCollectionIfMissing([], null, esfera, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(esfera);
      });

      it('should return initial array if no Esfera is added', () => {
        const esferaCollection: IEsfera[] = [sampleWithRequiredData];
        expectedResult = service.addEsferaToCollectionIfMissing(esferaCollection, undefined, null);
        expect(expectedResult).toEqual(esferaCollection);
      });
    });

    describe('compareEsfera', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareEsfera(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareEsfera(entity1, entity2);
        const compareResult2 = service.compareEsfera(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareEsfera(entity1, entity2);
        const compareResult2 = service.compareEsfera(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareEsfera(entity1, entity2);
        const compareResult2 = service.compareEsfera(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
