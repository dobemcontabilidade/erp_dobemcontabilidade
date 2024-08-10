import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IEnquadramento } from '../enquadramento.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../enquadramento.test-samples';

import { EnquadramentoService } from './enquadramento.service';

const requireRestSample: IEnquadramento = {
  ...sampleWithRequiredData,
};

describe('Enquadramento Service', () => {
  let service: EnquadramentoService;
  let httpMock: HttpTestingController;
  let expectedResult: IEnquadramento | IEnquadramento[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(EnquadramentoService);
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

    it('should create a Enquadramento', () => {
      const enquadramento = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(enquadramento).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Enquadramento', () => {
      const enquadramento = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(enquadramento).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Enquadramento', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Enquadramento', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Enquadramento', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addEnquadramentoToCollectionIfMissing', () => {
      it('should add a Enquadramento to an empty array', () => {
        const enquadramento: IEnquadramento = sampleWithRequiredData;
        expectedResult = service.addEnquadramentoToCollectionIfMissing([], enquadramento);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(enquadramento);
      });

      it('should not add a Enquadramento to an array that contains it', () => {
        const enquadramento: IEnquadramento = sampleWithRequiredData;
        const enquadramentoCollection: IEnquadramento[] = [
          {
            ...enquadramento,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addEnquadramentoToCollectionIfMissing(enquadramentoCollection, enquadramento);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Enquadramento to an array that doesn't contain it", () => {
        const enquadramento: IEnquadramento = sampleWithRequiredData;
        const enquadramentoCollection: IEnquadramento[] = [sampleWithPartialData];
        expectedResult = service.addEnquadramentoToCollectionIfMissing(enquadramentoCollection, enquadramento);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(enquadramento);
      });

      it('should add only unique Enquadramento to an array', () => {
        const enquadramentoArray: IEnquadramento[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const enquadramentoCollection: IEnquadramento[] = [sampleWithRequiredData];
        expectedResult = service.addEnquadramentoToCollectionIfMissing(enquadramentoCollection, ...enquadramentoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const enquadramento: IEnquadramento = sampleWithRequiredData;
        const enquadramento2: IEnquadramento = sampleWithPartialData;
        expectedResult = service.addEnquadramentoToCollectionIfMissing([], enquadramento, enquadramento2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(enquadramento);
        expect(expectedResult).toContain(enquadramento2);
      });

      it('should accept null and undefined values', () => {
        const enquadramento: IEnquadramento = sampleWithRequiredData;
        expectedResult = service.addEnquadramentoToCollectionIfMissing([], null, enquadramento, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(enquadramento);
      });

      it('should return initial array if no Enquadramento is added', () => {
        const enquadramentoCollection: IEnquadramento[] = [sampleWithRequiredData];
        expectedResult = service.addEnquadramentoToCollectionIfMissing(enquadramentoCollection, undefined, null);
        expect(expectedResult).toEqual(enquadramentoCollection);
      });
    });

    describe('compareEnquadramento', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareEnquadramento(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareEnquadramento(entity1, entity2);
        const compareResult2 = service.compareEnquadramento(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareEnquadramento(entity1, entity2);
        const compareResult2 = service.compareEnquadramento(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareEnquadramento(entity1, entity2);
        const compareResult2 = service.compareEnquadramento(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
