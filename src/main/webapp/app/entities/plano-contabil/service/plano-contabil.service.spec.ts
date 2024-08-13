import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IPlanoContabil } from '../plano-contabil.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../plano-contabil.test-samples';

import { PlanoContabilService } from './plano-contabil.service';

const requireRestSample: IPlanoContabil = {
  ...sampleWithRequiredData,
};

describe('PlanoContabil Service', () => {
  let service: PlanoContabilService;
  let httpMock: HttpTestingController;
  let expectedResult: IPlanoContabil | IPlanoContabil[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(PlanoContabilService);
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

    it('should create a PlanoContabil', () => {
      const planoContabil = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(planoContabil).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PlanoContabil', () => {
      const planoContabil = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(planoContabil).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PlanoContabil', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PlanoContabil', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a PlanoContabil', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addPlanoContabilToCollectionIfMissing', () => {
      it('should add a PlanoContabil to an empty array', () => {
        const planoContabil: IPlanoContabil = sampleWithRequiredData;
        expectedResult = service.addPlanoContabilToCollectionIfMissing([], planoContabil);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(planoContabil);
      });

      it('should not add a PlanoContabil to an array that contains it', () => {
        const planoContabil: IPlanoContabil = sampleWithRequiredData;
        const planoContabilCollection: IPlanoContabil[] = [
          {
            ...planoContabil,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addPlanoContabilToCollectionIfMissing(planoContabilCollection, planoContabil);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PlanoContabil to an array that doesn't contain it", () => {
        const planoContabil: IPlanoContabil = sampleWithRequiredData;
        const planoContabilCollection: IPlanoContabil[] = [sampleWithPartialData];
        expectedResult = service.addPlanoContabilToCollectionIfMissing(planoContabilCollection, planoContabil);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(planoContabil);
      });

      it('should add only unique PlanoContabil to an array', () => {
        const planoContabilArray: IPlanoContabil[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const planoContabilCollection: IPlanoContabil[] = [sampleWithRequiredData];
        expectedResult = service.addPlanoContabilToCollectionIfMissing(planoContabilCollection, ...planoContabilArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const planoContabil: IPlanoContabil = sampleWithRequiredData;
        const planoContabil2: IPlanoContabil = sampleWithPartialData;
        expectedResult = service.addPlanoContabilToCollectionIfMissing([], planoContabil, planoContabil2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(planoContabil);
        expect(expectedResult).toContain(planoContabil2);
      });

      it('should accept null and undefined values', () => {
        const planoContabil: IPlanoContabil = sampleWithRequiredData;
        expectedResult = service.addPlanoContabilToCollectionIfMissing([], null, planoContabil, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(planoContabil);
      });

      it('should return initial array if no PlanoContabil is added', () => {
        const planoContabilCollection: IPlanoContabil[] = [sampleWithRequiredData];
        expectedResult = service.addPlanoContabilToCollectionIfMissing(planoContabilCollection, undefined, null);
        expect(expectedResult).toEqual(planoContabilCollection);
      });
    });

    describe('comparePlanoContabil', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.comparePlanoContabil(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.comparePlanoContabil(entity1, entity2);
        const compareResult2 = service.comparePlanoContabil(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.comparePlanoContabil(entity1, entity2);
        const compareResult2 = service.comparePlanoContabil(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.comparePlanoContabil(entity1, entity2);
        const compareResult2 = service.comparePlanoContabil(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
