import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { ISegmentoCnae } from '../segmento-cnae.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../segmento-cnae.test-samples';

import { SegmentoCnaeService } from './segmento-cnae.service';

const requireRestSample: ISegmentoCnae = {
  ...sampleWithRequiredData,
};

describe('SegmentoCnae Service', () => {
  let service: SegmentoCnaeService;
  let httpMock: HttpTestingController;
  let expectedResult: ISegmentoCnae | ISegmentoCnae[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(SegmentoCnaeService);
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

    it('should create a SegmentoCnae', () => {
      const segmentoCnae = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(segmentoCnae).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a SegmentoCnae', () => {
      const segmentoCnae = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(segmentoCnae).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a SegmentoCnae', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of SegmentoCnae', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a SegmentoCnae', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addSegmentoCnaeToCollectionIfMissing', () => {
      it('should add a SegmentoCnae to an empty array', () => {
        const segmentoCnae: ISegmentoCnae = sampleWithRequiredData;
        expectedResult = service.addSegmentoCnaeToCollectionIfMissing([], segmentoCnae);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(segmentoCnae);
      });

      it('should not add a SegmentoCnae to an array that contains it', () => {
        const segmentoCnae: ISegmentoCnae = sampleWithRequiredData;
        const segmentoCnaeCollection: ISegmentoCnae[] = [
          {
            ...segmentoCnae,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addSegmentoCnaeToCollectionIfMissing(segmentoCnaeCollection, segmentoCnae);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a SegmentoCnae to an array that doesn't contain it", () => {
        const segmentoCnae: ISegmentoCnae = sampleWithRequiredData;
        const segmentoCnaeCollection: ISegmentoCnae[] = [sampleWithPartialData];
        expectedResult = service.addSegmentoCnaeToCollectionIfMissing(segmentoCnaeCollection, segmentoCnae);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(segmentoCnae);
      });

      it('should add only unique SegmentoCnae to an array', () => {
        const segmentoCnaeArray: ISegmentoCnae[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const segmentoCnaeCollection: ISegmentoCnae[] = [sampleWithRequiredData];
        expectedResult = service.addSegmentoCnaeToCollectionIfMissing(segmentoCnaeCollection, ...segmentoCnaeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const segmentoCnae: ISegmentoCnae = sampleWithRequiredData;
        const segmentoCnae2: ISegmentoCnae = sampleWithPartialData;
        expectedResult = service.addSegmentoCnaeToCollectionIfMissing([], segmentoCnae, segmentoCnae2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(segmentoCnae);
        expect(expectedResult).toContain(segmentoCnae2);
      });

      it('should accept null and undefined values', () => {
        const segmentoCnae: ISegmentoCnae = sampleWithRequiredData;
        expectedResult = service.addSegmentoCnaeToCollectionIfMissing([], null, segmentoCnae, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(segmentoCnae);
      });

      it('should return initial array if no SegmentoCnae is added', () => {
        const segmentoCnaeCollection: ISegmentoCnae[] = [sampleWithRequiredData];
        expectedResult = service.addSegmentoCnaeToCollectionIfMissing(segmentoCnaeCollection, undefined, null);
        expect(expectedResult).toEqual(segmentoCnaeCollection);
      });
    });

    describe('compareSegmentoCnae', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareSegmentoCnae(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareSegmentoCnae(entity1, entity2);
        const compareResult2 = service.compareSegmentoCnae(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareSegmentoCnae(entity1, entity2);
        const compareResult2 = service.compareSegmentoCnae(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareSegmentoCnae(entity1, entity2);
        const compareResult2 = service.compareSegmentoCnae(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
