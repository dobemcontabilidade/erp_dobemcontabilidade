import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IAdicionalEnquadramento } from '../adicional-enquadramento.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../adicional-enquadramento.test-samples';

import { AdicionalEnquadramentoService } from './adicional-enquadramento.service';

const requireRestSample: IAdicionalEnquadramento = {
  ...sampleWithRequiredData,
};

describe('AdicionalEnquadramento Service', () => {
  let service: AdicionalEnquadramentoService;
  let httpMock: HttpTestingController;
  let expectedResult: IAdicionalEnquadramento | IAdicionalEnquadramento[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(AdicionalEnquadramentoService);
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

    it('should create a AdicionalEnquadramento', () => {
      const adicionalEnquadramento = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(adicionalEnquadramento).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a AdicionalEnquadramento', () => {
      const adicionalEnquadramento = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(adicionalEnquadramento).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a AdicionalEnquadramento', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of AdicionalEnquadramento', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a AdicionalEnquadramento', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addAdicionalEnquadramentoToCollectionIfMissing', () => {
      it('should add a AdicionalEnquadramento to an empty array', () => {
        const adicionalEnquadramento: IAdicionalEnquadramento = sampleWithRequiredData;
        expectedResult = service.addAdicionalEnquadramentoToCollectionIfMissing([], adicionalEnquadramento);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(adicionalEnquadramento);
      });

      it('should not add a AdicionalEnquadramento to an array that contains it', () => {
        const adicionalEnquadramento: IAdicionalEnquadramento = sampleWithRequiredData;
        const adicionalEnquadramentoCollection: IAdicionalEnquadramento[] = [
          {
            ...adicionalEnquadramento,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addAdicionalEnquadramentoToCollectionIfMissing(adicionalEnquadramentoCollection, adicionalEnquadramento);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a AdicionalEnquadramento to an array that doesn't contain it", () => {
        const adicionalEnquadramento: IAdicionalEnquadramento = sampleWithRequiredData;
        const adicionalEnquadramentoCollection: IAdicionalEnquadramento[] = [sampleWithPartialData];
        expectedResult = service.addAdicionalEnquadramentoToCollectionIfMissing(adicionalEnquadramentoCollection, adicionalEnquadramento);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(adicionalEnquadramento);
      });

      it('should add only unique AdicionalEnquadramento to an array', () => {
        const adicionalEnquadramentoArray: IAdicionalEnquadramento[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const adicionalEnquadramentoCollection: IAdicionalEnquadramento[] = [sampleWithRequiredData];
        expectedResult = service.addAdicionalEnquadramentoToCollectionIfMissing(
          adicionalEnquadramentoCollection,
          ...adicionalEnquadramentoArray,
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const adicionalEnquadramento: IAdicionalEnquadramento = sampleWithRequiredData;
        const adicionalEnquadramento2: IAdicionalEnquadramento = sampleWithPartialData;
        expectedResult = service.addAdicionalEnquadramentoToCollectionIfMissing([], adicionalEnquadramento, adicionalEnquadramento2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(adicionalEnquadramento);
        expect(expectedResult).toContain(adicionalEnquadramento2);
      });

      it('should accept null and undefined values', () => {
        const adicionalEnquadramento: IAdicionalEnquadramento = sampleWithRequiredData;
        expectedResult = service.addAdicionalEnquadramentoToCollectionIfMissing([], null, adicionalEnquadramento, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(adicionalEnquadramento);
      });

      it('should return initial array if no AdicionalEnquadramento is added', () => {
        const adicionalEnquadramentoCollection: IAdicionalEnquadramento[] = [sampleWithRequiredData];
        expectedResult = service.addAdicionalEnquadramentoToCollectionIfMissing(adicionalEnquadramentoCollection, undefined, null);
        expect(expectedResult).toEqual(adicionalEnquadramentoCollection);
      });
    });

    describe('compareAdicionalEnquadramento', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareAdicionalEnquadramento(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareAdicionalEnquadramento(entity1, entity2);
        const compareResult2 = service.compareAdicionalEnquadramento(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareAdicionalEnquadramento(entity1, entity2);
        const compareResult2 = service.compareAdicionalEnquadramento(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareAdicionalEnquadramento(entity1, entity2);
        const compareResult2 = service.compareAdicionalEnquadramento(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
