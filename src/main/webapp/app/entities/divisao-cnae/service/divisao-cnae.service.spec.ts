import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IDivisaoCnae } from '../divisao-cnae.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../divisao-cnae.test-samples';

import { DivisaoCnaeService } from './divisao-cnae.service';

const requireRestSample: IDivisaoCnae = {
  ...sampleWithRequiredData,
};

describe('DivisaoCnae Service', () => {
  let service: DivisaoCnaeService;
  let httpMock: HttpTestingController;
  let expectedResult: IDivisaoCnae | IDivisaoCnae[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(DivisaoCnaeService);
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

    it('should create a DivisaoCnae', () => {
      const divisaoCnae = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(divisaoCnae).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a DivisaoCnae', () => {
      const divisaoCnae = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(divisaoCnae).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a DivisaoCnae', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of DivisaoCnae', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a DivisaoCnae', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addDivisaoCnaeToCollectionIfMissing', () => {
      it('should add a DivisaoCnae to an empty array', () => {
        const divisaoCnae: IDivisaoCnae = sampleWithRequiredData;
        expectedResult = service.addDivisaoCnaeToCollectionIfMissing([], divisaoCnae);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(divisaoCnae);
      });

      it('should not add a DivisaoCnae to an array that contains it', () => {
        const divisaoCnae: IDivisaoCnae = sampleWithRequiredData;
        const divisaoCnaeCollection: IDivisaoCnae[] = [
          {
            ...divisaoCnae,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addDivisaoCnaeToCollectionIfMissing(divisaoCnaeCollection, divisaoCnae);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a DivisaoCnae to an array that doesn't contain it", () => {
        const divisaoCnae: IDivisaoCnae = sampleWithRequiredData;
        const divisaoCnaeCollection: IDivisaoCnae[] = [sampleWithPartialData];
        expectedResult = service.addDivisaoCnaeToCollectionIfMissing(divisaoCnaeCollection, divisaoCnae);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(divisaoCnae);
      });

      it('should add only unique DivisaoCnae to an array', () => {
        const divisaoCnaeArray: IDivisaoCnae[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const divisaoCnaeCollection: IDivisaoCnae[] = [sampleWithRequiredData];
        expectedResult = service.addDivisaoCnaeToCollectionIfMissing(divisaoCnaeCollection, ...divisaoCnaeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const divisaoCnae: IDivisaoCnae = sampleWithRequiredData;
        const divisaoCnae2: IDivisaoCnae = sampleWithPartialData;
        expectedResult = service.addDivisaoCnaeToCollectionIfMissing([], divisaoCnae, divisaoCnae2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(divisaoCnae);
        expect(expectedResult).toContain(divisaoCnae2);
      });

      it('should accept null and undefined values', () => {
        const divisaoCnae: IDivisaoCnae = sampleWithRequiredData;
        expectedResult = service.addDivisaoCnaeToCollectionIfMissing([], null, divisaoCnae, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(divisaoCnae);
      });

      it('should return initial array if no DivisaoCnae is added', () => {
        const divisaoCnaeCollection: IDivisaoCnae[] = [sampleWithRequiredData];
        expectedResult = service.addDivisaoCnaeToCollectionIfMissing(divisaoCnaeCollection, undefined, null);
        expect(expectedResult).toEqual(divisaoCnaeCollection);
      });
    });

    describe('compareDivisaoCnae', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareDivisaoCnae(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareDivisaoCnae(entity1, entity2);
        const compareResult2 = service.compareDivisaoCnae(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareDivisaoCnae(entity1, entity2);
        const compareResult2 = service.compareDivisaoCnae(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareDivisaoCnae(entity1, entity2);
        const compareResult2 = service.compareDivisaoCnae(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
