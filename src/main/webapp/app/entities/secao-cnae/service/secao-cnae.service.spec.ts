import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { ISecaoCnae } from '../secao-cnae.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../secao-cnae.test-samples';

import { SecaoCnaeService } from './secao-cnae.service';

const requireRestSample: ISecaoCnae = {
  ...sampleWithRequiredData,
};

describe('SecaoCnae Service', () => {
  let service: SecaoCnaeService;
  let httpMock: HttpTestingController;
  let expectedResult: ISecaoCnae | ISecaoCnae[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(SecaoCnaeService);
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

    it('should create a SecaoCnae', () => {
      const secaoCnae = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(secaoCnae).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a SecaoCnae', () => {
      const secaoCnae = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(secaoCnae).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a SecaoCnae', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of SecaoCnae', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a SecaoCnae', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addSecaoCnaeToCollectionIfMissing', () => {
      it('should add a SecaoCnae to an empty array', () => {
        const secaoCnae: ISecaoCnae = sampleWithRequiredData;
        expectedResult = service.addSecaoCnaeToCollectionIfMissing([], secaoCnae);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(secaoCnae);
      });

      it('should not add a SecaoCnae to an array that contains it', () => {
        const secaoCnae: ISecaoCnae = sampleWithRequiredData;
        const secaoCnaeCollection: ISecaoCnae[] = [
          {
            ...secaoCnae,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addSecaoCnaeToCollectionIfMissing(secaoCnaeCollection, secaoCnae);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a SecaoCnae to an array that doesn't contain it", () => {
        const secaoCnae: ISecaoCnae = sampleWithRequiredData;
        const secaoCnaeCollection: ISecaoCnae[] = [sampleWithPartialData];
        expectedResult = service.addSecaoCnaeToCollectionIfMissing(secaoCnaeCollection, secaoCnae);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(secaoCnae);
      });

      it('should add only unique SecaoCnae to an array', () => {
        const secaoCnaeArray: ISecaoCnae[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const secaoCnaeCollection: ISecaoCnae[] = [sampleWithRequiredData];
        expectedResult = service.addSecaoCnaeToCollectionIfMissing(secaoCnaeCollection, ...secaoCnaeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const secaoCnae: ISecaoCnae = sampleWithRequiredData;
        const secaoCnae2: ISecaoCnae = sampleWithPartialData;
        expectedResult = service.addSecaoCnaeToCollectionIfMissing([], secaoCnae, secaoCnae2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(secaoCnae);
        expect(expectedResult).toContain(secaoCnae2);
      });

      it('should accept null and undefined values', () => {
        const secaoCnae: ISecaoCnae = sampleWithRequiredData;
        expectedResult = service.addSecaoCnaeToCollectionIfMissing([], null, secaoCnae, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(secaoCnae);
      });

      it('should return initial array if no SecaoCnae is added', () => {
        const secaoCnaeCollection: ISecaoCnae[] = [sampleWithRequiredData];
        expectedResult = service.addSecaoCnaeToCollectionIfMissing(secaoCnaeCollection, undefined, null);
        expect(expectedResult).toEqual(secaoCnaeCollection);
      });
    });

    describe('compareSecaoCnae', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareSecaoCnae(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareSecaoCnae(entity1, entity2);
        const compareResult2 = service.compareSecaoCnae(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareSecaoCnae(entity1, entity2);
        const compareResult2 = service.compareSecaoCnae(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareSecaoCnae(entity1, entity2);
        const compareResult2 = service.compareSecaoCnae(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
