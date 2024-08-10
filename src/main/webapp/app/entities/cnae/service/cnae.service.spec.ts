import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { ICnae } from '../cnae.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../cnae.test-samples';

import { CnaeService } from './cnae.service';

const requireRestSample: ICnae = {
  ...sampleWithRequiredData,
};

describe('Cnae Service', () => {
  let service: CnaeService;
  let httpMock: HttpTestingController;
  let expectedResult: ICnae | ICnae[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(CnaeService);
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

    it('should create a Cnae', () => {
      const cnae = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(cnae).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Cnae', () => {
      const cnae = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(cnae).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Cnae', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Cnae', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Cnae', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCnaeToCollectionIfMissing', () => {
      it('should add a Cnae to an empty array', () => {
        const cnae: ICnae = sampleWithRequiredData;
        expectedResult = service.addCnaeToCollectionIfMissing([], cnae);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(cnae);
      });

      it('should not add a Cnae to an array that contains it', () => {
        const cnae: ICnae = sampleWithRequiredData;
        const cnaeCollection: ICnae[] = [
          {
            ...cnae,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCnaeToCollectionIfMissing(cnaeCollection, cnae);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Cnae to an array that doesn't contain it", () => {
        const cnae: ICnae = sampleWithRequiredData;
        const cnaeCollection: ICnae[] = [sampleWithPartialData];
        expectedResult = service.addCnaeToCollectionIfMissing(cnaeCollection, cnae);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(cnae);
      });

      it('should add only unique Cnae to an array', () => {
        const cnaeArray: ICnae[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const cnaeCollection: ICnae[] = [sampleWithRequiredData];
        expectedResult = service.addCnaeToCollectionIfMissing(cnaeCollection, ...cnaeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const cnae: ICnae = sampleWithRequiredData;
        const cnae2: ICnae = sampleWithPartialData;
        expectedResult = service.addCnaeToCollectionIfMissing([], cnae, cnae2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(cnae);
        expect(expectedResult).toContain(cnae2);
      });

      it('should accept null and undefined values', () => {
        const cnae: ICnae = sampleWithRequiredData;
        expectedResult = service.addCnaeToCollectionIfMissing([], null, cnae, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(cnae);
      });

      it('should return initial array if no Cnae is added', () => {
        const cnaeCollection: ICnae[] = [sampleWithRequiredData];
        expectedResult = service.addCnaeToCollectionIfMissing(cnaeCollection, undefined, null);
        expect(expectedResult).toEqual(cnaeCollection);
      });
    });

    describe('compareCnae', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCnae(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareCnae(entity1, entity2);
        const compareResult2 = service.compareCnae(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareCnae(entity1, entity2);
        const compareResult2 = service.compareCnae(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareCnae(entity1, entity2);
        const compareResult2 = service.compareCnae(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
