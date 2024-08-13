import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IRamo } from '../ramo.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../ramo.test-samples';

import { RamoService } from './ramo.service';

const requireRestSample: IRamo = {
  ...sampleWithRequiredData,
};

describe('Ramo Service', () => {
  let service: RamoService;
  let httpMock: HttpTestingController;
  let expectedResult: IRamo | IRamo[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(RamoService);
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

    it('should create a Ramo', () => {
      const ramo = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(ramo).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Ramo', () => {
      const ramo = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(ramo).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Ramo', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Ramo', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Ramo', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addRamoToCollectionIfMissing', () => {
      it('should add a Ramo to an empty array', () => {
        const ramo: IRamo = sampleWithRequiredData;
        expectedResult = service.addRamoToCollectionIfMissing([], ramo);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(ramo);
      });

      it('should not add a Ramo to an array that contains it', () => {
        const ramo: IRamo = sampleWithRequiredData;
        const ramoCollection: IRamo[] = [
          {
            ...ramo,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addRamoToCollectionIfMissing(ramoCollection, ramo);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Ramo to an array that doesn't contain it", () => {
        const ramo: IRamo = sampleWithRequiredData;
        const ramoCollection: IRamo[] = [sampleWithPartialData];
        expectedResult = service.addRamoToCollectionIfMissing(ramoCollection, ramo);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(ramo);
      });

      it('should add only unique Ramo to an array', () => {
        const ramoArray: IRamo[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const ramoCollection: IRamo[] = [sampleWithRequiredData];
        expectedResult = service.addRamoToCollectionIfMissing(ramoCollection, ...ramoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const ramo: IRamo = sampleWithRequiredData;
        const ramo2: IRamo = sampleWithPartialData;
        expectedResult = service.addRamoToCollectionIfMissing([], ramo, ramo2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(ramo);
        expect(expectedResult).toContain(ramo2);
      });

      it('should accept null and undefined values', () => {
        const ramo: IRamo = sampleWithRequiredData;
        expectedResult = service.addRamoToCollectionIfMissing([], null, ramo, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(ramo);
      });

      it('should return initial array if no Ramo is added', () => {
        const ramoCollection: IRamo[] = [sampleWithRequiredData];
        expectedResult = service.addRamoToCollectionIfMissing(ramoCollection, undefined, null);
        expect(expectedResult).toEqual(ramoCollection);
      });
    });

    describe('compareRamo', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareRamo(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareRamo(entity1, entity2);
        const compareResult2 = service.compareRamo(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareRamo(entity1, entity2);
        const compareResult2 = service.compareRamo(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareRamo(entity1, entity2);
        const compareResult2 = service.compareRamo(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
