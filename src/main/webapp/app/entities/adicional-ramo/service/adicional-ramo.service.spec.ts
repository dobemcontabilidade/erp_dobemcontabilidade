import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IAdicionalRamo } from '../adicional-ramo.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../adicional-ramo.test-samples';

import { AdicionalRamoService } from './adicional-ramo.service';

const requireRestSample: IAdicionalRamo = {
  ...sampleWithRequiredData,
};

describe('AdicionalRamo Service', () => {
  let service: AdicionalRamoService;
  let httpMock: HttpTestingController;
  let expectedResult: IAdicionalRamo | IAdicionalRamo[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(AdicionalRamoService);
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

    it('should create a AdicionalRamo', () => {
      const adicionalRamo = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(adicionalRamo).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a AdicionalRamo', () => {
      const adicionalRamo = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(adicionalRamo).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a AdicionalRamo', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of AdicionalRamo', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a AdicionalRamo', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addAdicionalRamoToCollectionIfMissing', () => {
      it('should add a AdicionalRamo to an empty array', () => {
        const adicionalRamo: IAdicionalRamo = sampleWithRequiredData;
        expectedResult = service.addAdicionalRamoToCollectionIfMissing([], adicionalRamo);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(adicionalRamo);
      });

      it('should not add a AdicionalRamo to an array that contains it', () => {
        const adicionalRamo: IAdicionalRamo = sampleWithRequiredData;
        const adicionalRamoCollection: IAdicionalRamo[] = [
          {
            ...adicionalRamo,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addAdicionalRamoToCollectionIfMissing(adicionalRamoCollection, adicionalRamo);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a AdicionalRamo to an array that doesn't contain it", () => {
        const adicionalRamo: IAdicionalRamo = sampleWithRequiredData;
        const adicionalRamoCollection: IAdicionalRamo[] = [sampleWithPartialData];
        expectedResult = service.addAdicionalRamoToCollectionIfMissing(adicionalRamoCollection, adicionalRamo);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(adicionalRamo);
      });

      it('should add only unique AdicionalRamo to an array', () => {
        const adicionalRamoArray: IAdicionalRamo[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const adicionalRamoCollection: IAdicionalRamo[] = [sampleWithRequiredData];
        expectedResult = service.addAdicionalRamoToCollectionIfMissing(adicionalRamoCollection, ...adicionalRamoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const adicionalRamo: IAdicionalRamo = sampleWithRequiredData;
        const adicionalRamo2: IAdicionalRamo = sampleWithPartialData;
        expectedResult = service.addAdicionalRamoToCollectionIfMissing([], adicionalRamo, adicionalRamo2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(adicionalRamo);
        expect(expectedResult).toContain(adicionalRamo2);
      });

      it('should accept null and undefined values', () => {
        const adicionalRamo: IAdicionalRamo = sampleWithRequiredData;
        expectedResult = service.addAdicionalRamoToCollectionIfMissing([], null, adicionalRamo, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(adicionalRamo);
      });

      it('should return initial array if no AdicionalRamo is added', () => {
        const adicionalRamoCollection: IAdicionalRamo[] = [sampleWithRequiredData];
        expectedResult = service.addAdicionalRamoToCollectionIfMissing(adicionalRamoCollection, undefined, null);
        expect(expectedResult).toEqual(adicionalRamoCollection);
      });
    });

    describe('compareAdicionalRamo', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareAdicionalRamo(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareAdicionalRamo(entity1, entity2);
        const compareResult2 = service.compareAdicionalRamo(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareAdicionalRamo(entity1, entity2);
        const compareResult2 = service.compareAdicionalRamo(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareAdicionalRamo(entity1, entity2);
        const compareResult2 = service.compareAdicionalRamo(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
