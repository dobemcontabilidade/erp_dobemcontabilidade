import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IGrupoCnae } from '../grupo-cnae.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../grupo-cnae.test-samples';

import { GrupoCnaeService } from './grupo-cnae.service';

const requireRestSample: IGrupoCnae = {
  ...sampleWithRequiredData,
};

describe('GrupoCnae Service', () => {
  let service: GrupoCnaeService;
  let httpMock: HttpTestingController;
  let expectedResult: IGrupoCnae | IGrupoCnae[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(GrupoCnaeService);
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

    it('should create a GrupoCnae', () => {
      const grupoCnae = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(grupoCnae).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a GrupoCnae', () => {
      const grupoCnae = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(grupoCnae).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a GrupoCnae', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of GrupoCnae', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a GrupoCnae', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addGrupoCnaeToCollectionIfMissing', () => {
      it('should add a GrupoCnae to an empty array', () => {
        const grupoCnae: IGrupoCnae = sampleWithRequiredData;
        expectedResult = service.addGrupoCnaeToCollectionIfMissing([], grupoCnae);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(grupoCnae);
      });

      it('should not add a GrupoCnae to an array that contains it', () => {
        const grupoCnae: IGrupoCnae = sampleWithRequiredData;
        const grupoCnaeCollection: IGrupoCnae[] = [
          {
            ...grupoCnae,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addGrupoCnaeToCollectionIfMissing(grupoCnaeCollection, grupoCnae);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a GrupoCnae to an array that doesn't contain it", () => {
        const grupoCnae: IGrupoCnae = sampleWithRequiredData;
        const grupoCnaeCollection: IGrupoCnae[] = [sampleWithPartialData];
        expectedResult = service.addGrupoCnaeToCollectionIfMissing(grupoCnaeCollection, grupoCnae);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(grupoCnae);
      });

      it('should add only unique GrupoCnae to an array', () => {
        const grupoCnaeArray: IGrupoCnae[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const grupoCnaeCollection: IGrupoCnae[] = [sampleWithRequiredData];
        expectedResult = service.addGrupoCnaeToCollectionIfMissing(grupoCnaeCollection, ...grupoCnaeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const grupoCnae: IGrupoCnae = sampleWithRequiredData;
        const grupoCnae2: IGrupoCnae = sampleWithPartialData;
        expectedResult = service.addGrupoCnaeToCollectionIfMissing([], grupoCnae, grupoCnae2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(grupoCnae);
        expect(expectedResult).toContain(grupoCnae2);
      });

      it('should accept null and undefined values', () => {
        const grupoCnae: IGrupoCnae = sampleWithRequiredData;
        expectedResult = service.addGrupoCnaeToCollectionIfMissing([], null, grupoCnae, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(grupoCnae);
      });

      it('should return initial array if no GrupoCnae is added', () => {
        const grupoCnaeCollection: IGrupoCnae[] = [sampleWithRequiredData];
        expectedResult = service.addGrupoCnaeToCollectionIfMissing(grupoCnaeCollection, undefined, null);
        expect(expectedResult).toEqual(grupoCnaeCollection);
      });
    });

    describe('compareGrupoCnae', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareGrupoCnae(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareGrupoCnae(entity1, entity2);
        const compareResult2 = service.compareGrupoCnae(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareGrupoCnae(entity1, entity2);
        const compareResult2 = service.compareGrupoCnae(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareGrupoCnae(entity1, entity2);
        const compareResult2 = service.compareGrupoCnae(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
