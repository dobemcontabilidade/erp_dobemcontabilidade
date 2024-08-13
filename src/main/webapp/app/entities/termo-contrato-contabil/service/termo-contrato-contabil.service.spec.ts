import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { ITermoContratoContabil } from '../termo-contrato-contabil.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../termo-contrato-contabil.test-samples';

import { TermoContratoContabilService } from './termo-contrato-contabil.service';

const requireRestSample: ITermoContratoContabil = {
  ...sampleWithRequiredData,
};

describe('TermoContratoContabil Service', () => {
  let service: TermoContratoContabilService;
  let httpMock: HttpTestingController;
  let expectedResult: ITermoContratoContabil | ITermoContratoContabil[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(TermoContratoContabilService);
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

    it('should create a TermoContratoContabil', () => {
      const termoContratoContabil = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(termoContratoContabil).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a TermoContratoContabil', () => {
      const termoContratoContabil = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(termoContratoContabil).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a TermoContratoContabil', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of TermoContratoContabil', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a TermoContratoContabil', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addTermoContratoContabilToCollectionIfMissing', () => {
      it('should add a TermoContratoContabil to an empty array', () => {
        const termoContratoContabil: ITermoContratoContabil = sampleWithRequiredData;
        expectedResult = service.addTermoContratoContabilToCollectionIfMissing([], termoContratoContabil);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(termoContratoContabil);
      });

      it('should not add a TermoContratoContabil to an array that contains it', () => {
        const termoContratoContabil: ITermoContratoContabil = sampleWithRequiredData;
        const termoContratoContabilCollection: ITermoContratoContabil[] = [
          {
            ...termoContratoContabil,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addTermoContratoContabilToCollectionIfMissing(termoContratoContabilCollection, termoContratoContabil);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a TermoContratoContabil to an array that doesn't contain it", () => {
        const termoContratoContabil: ITermoContratoContabil = sampleWithRequiredData;
        const termoContratoContabilCollection: ITermoContratoContabil[] = [sampleWithPartialData];
        expectedResult = service.addTermoContratoContabilToCollectionIfMissing(termoContratoContabilCollection, termoContratoContabil);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(termoContratoContabil);
      });

      it('should add only unique TermoContratoContabil to an array', () => {
        const termoContratoContabilArray: ITermoContratoContabil[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const termoContratoContabilCollection: ITermoContratoContabil[] = [sampleWithRequiredData];
        expectedResult = service.addTermoContratoContabilToCollectionIfMissing(
          termoContratoContabilCollection,
          ...termoContratoContabilArray,
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const termoContratoContabil: ITermoContratoContabil = sampleWithRequiredData;
        const termoContratoContabil2: ITermoContratoContabil = sampleWithPartialData;
        expectedResult = service.addTermoContratoContabilToCollectionIfMissing([], termoContratoContabil, termoContratoContabil2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(termoContratoContabil);
        expect(expectedResult).toContain(termoContratoContabil2);
      });

      it('should accept null and undefined values', () => {
        const termoContratoContabil: ITermoContratoContabil = sampleWithRequiredData;
        expectedResult = service.addTermoContratoContabilToCollectionIfMissing([], null, termoContratoContabil, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(termoContratoContabil);
      });

      it('should return initial array if no TermoContratoContabil is added', () => {
        const termoContratoContabilCollection: ITermoContratoContabil[] = [sampleWithRequiredData];
        expectedResult = service.addTermoContratoContabilToCollectionIfMissing(termoContratoContabilCollection, undefined, null);
        expect(expectedResult).toEqual(termoContratoContabilCollection);
      });
    });

    describe('compareTermoContratoContabil', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareTermoContratoContabil(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareTermoContratoContabil(entity1, entity2);
        const compareResult2 = service.compareTermoContratoContabil(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareTermoContratoContabil(entity1, entity2);
        const compareResult2 = service.compareTermoContratoContabil(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareTermoContratoContabil(entity1, entity2);
        const compareResult2 = service.compareTermoContratoContabil(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
