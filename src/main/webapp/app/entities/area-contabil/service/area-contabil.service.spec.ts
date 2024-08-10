import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IAreaContabil } from '../area-contabil.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../area-contabil.test-samples';

import { AreaContabilService } from './area-contabil.service';

const requireRestSample: IAreaContabil = {
  ...sampleWithRequiredData,
};

describe('AreaContabil Service', () => {
  let service: AreaContabilService;
  let httpMock: HttpTestingController;
  let expectedResult: IAreaContabil | IAreaContabil[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(AreaContabilService);
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

    it('should create a AreaContabil', () => {
      const areaContabil = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(areaContabil).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a AreaContabil', () => {
      const areaContabil = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(areaContabil).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a AreaContabil', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of AreaContabil', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a AreaContabil', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addAreaContabilToCollectionIfMissing', () => {
      it('should add a AreaContabil to an empty array', () => {
        const areaContabil: IAreaContabil = sampleWithRequiredData;
        expectedResult = service.addAreaContabilToCollectionIfMissing([], areaContabil);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(areaContabil);
      });

      it('should not add a AreaContabil to an array that contains it', () => {
        const areaContabil: IAreaContabil = sampleWithRequiredData;
        const areaContabilCollection: IAreaContabil[] = [
          {
            ...areaContabil,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addAreaContabilToCollectionIfMissing(areaContabilCollection, areaContabil);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a AreaContabil to an array that doesn't contain it", () => {
        const areaContabil: IAreaContabil = sampleWithRequiredData;
        const areaContabilCollection: IAreaContabil[] = [sampleWithPartialData];
        expectedResult = service.addAreaContabilToCollectionIfMissing(areaContabilCollection, areaContabil);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(areaContabil);
      });

      it('should add only unique AreaContabil to an array', () => {
        const areaContabilArray: IAreaContabil[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const areaContabilCollection: IAreaContabil[] = [sampleWithRequiredData];
        expectedResult = service.addAreaContabilToCollectionIfMissing(areaContabilCollection, ...areaContabilArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const areaContabil: IAreaContabil = sampleWithRequiredData;
        const areaContabil2: IAreaContabil = sampleWithPartialData;
        expectedResult = service.addAreaContabilToCollectionIfMissing([], areaContabil, areaContabil2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(areaContabil);
        expect(expectedResult).toContain(areaContabil2);
      });

      it('should accept null and undefined values', () => {
        const areaContabil: IAreaContabil = sampleWithRequiredData;
        expectedResult = service.addAreaContabilToCollectionIfMissing([], null, areaContabil, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(areaContabil);
      });

      it('should return initial array if no AreaContabil is added', () => {
        const areaContabilCollection: IAreaContabil[] = [sampleWithRequiredData];
        expectedResult = service.addAreaContabilToCollectionIfMissing(areaContabilCollection, undefined, null);
        expect(expectedResult).toEqual(areaContabilCollection);
      });
    });

    describe('compareAreaContabil', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareAreaContabil(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareAreaContabil(entity1, entity2);
        const compareResult2 = service.compareAreaContabil(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareAreaContabil(entity1, entity2);
        const compareResult2 = service.compareAreaContabil(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareAreaContabil(entity1, entity2);
        const compareResult2 = service.compareAreaContabil(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
