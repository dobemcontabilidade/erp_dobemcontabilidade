import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IEstrangeiro } from '../estrangeiro.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../estrangeiro.test-samples';

import { EstrangeiroService } from './estrangeiro.service';

const requireRestSample: IEstrangeiro = {
  ...sampleWithRequiredData,
};

describe('Estrangeiro Service', () => {
  let service: EstrangeiroService;
  let httpMock: HttpTestingController;
  let expectedResult: IEstrangeiro | IEstrangeiro[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(EstrangeiroService);
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

    it('should create a Estrangeiro', () => {
      const estrangeiro = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(estrangeiro).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Estrangeiro', () => {
      const estrangeiro = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(estrangeiro).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Estrangeiro', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Estrangeiro', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Estrangeiro', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addEstrangeiroToCollectionIfMissing', () => {
      it('should add a Estrangeiro to an empty array', () => {
        const estrangeiro: IEstrangeiro = sampleWithRequiredData;
        expectedResult = service.addEstrangeiroToCollectionIfMissing([], estrangeiro);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(estrangeiro);
      });

      it('should not add a Estrangeiro to an array that contains it', () => {
        const estrangeiro: IEstrangeiro = sampleWithRequiredData;
        const estrangeiroCollection: IEstrangeiro[] = [
          {
            ...estrangeiro,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addEstrangeiroToCollectionIfMissing(estrangeiroCollection, estrangeiro);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Estrangeiro to an array that doesn't contain it", () => {
        const estrangeiro: IEstrangeiro = sampleWithRequiredData;
        const estrangeiroCollection: IEstrangeiro[] = [sampleWithPartialData];
        expectedResult = service.addEstrangeiroToCollectionIfMissing(estrangeiroCollection, estrangeiro);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(estrangeiro);
      });

      it('should add only unique Estrangeiro to an array', () => {
        const estrangeiroArray: IEstrangeiro[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const estrangeiroCollection: IEstrangeiro[] = [sampleWithRequiredData];
        expectedResult = service.addEstrangeiroToCollectionIfMissing(estrangeiroCollection, ...estrangeiroArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const estrangeiro: IEstrangeiro = sampleWithRequiredData;
        const estrangeiro2: IEstrangeiro = sampleWithPartialData;
        expectedResult = service.addEstrangeiroToCollectionIfMissing([], estrangeiro, estrangeiro2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(estrangeiro);
        expect(expectedResult).toContain(estrangeiro2);
      });

      it('should accept null and undefined values', () => {
        const estrangeiro: IEstrangeiro = sampleWithRequiredData;
        expectedResult = service.addEstrangeiroToCollectionIfMissing([], null, estrangeiro, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(estrangeiro);
      });

      it('should return initial array if no Estrangeiro is added', () => {
        const estrangeiroCollection: IEstrangeiro[] = [sampleWithRequiredData];
        expectedResult = service.addEstrangeiroToCollectionIfMissing(estrangeiroCollection, undefined, null);
        expect(expectedResult).toEqual(estrangeiroCollection);
      });
    });

    describe('compareEstrangeiro', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareEstrangeiro(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareEstrangeiro(entity1, entity2);
        const compareResult2 = service.compareEstrangeiro(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareEstrangeiro(entity1, entity2);
        const compareResult2 = service.compareEstrangeiro(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareEstrangeiro(entity1, entity2);
        const compareResult2 = service.compareEstrangeiro(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
