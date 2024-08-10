import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { ITermoDeAdesao } from '../termo-de-adesao.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../termo-de-adesao.test-samples';

import { TermoDeAdesaoService } from './termo-de-adesao.service';

const requireRestSample: ITermoDeAdesao = {
  ...sampleWithRequiredData,
};

describe('TermoDeAdesao Service', () => {
  let service: TermoDeAdesaoService;
  let httpMock: HttpTestingController;
  let expectedResult: ITermoDeAdesao | ITermoDeAdesao[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(TermoDeAdesaoService);
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

    it('should create a TermoDeAdesao', () => {
      const termoDeAdesao = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(termoDeAdesao).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a TermoDeAdesao', () => {
      const termoDeAdesao = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(termoDeAdesao).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a TermoDeAdesao', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of TermoDeAdesao', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a TermoDeAdesao', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addTermoDeAdesaoToCollectionIfMissing', () => {
      it('should add a TermoDeAdesao to an empty array', () => {
        const termoDeAdesao: ITermoDeAdesao = sampleWithRequiredData;
        expectedResult = service.addTermoDeAdesaoToCollectionIfMissing([], termoDeAdesao);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(termoDeAdesao);
      });

      it('should not add a TermoDeAdesao to an array that contains it', () => {
        const termoDeAdesao: ITermoDeAdesao = sampleWithRequiredData;
        const termoDeAdesaoCollection: ITermoDeAdesao[] = [
          {
            ...termoDeAdesao,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addTermoDeAdesaoToCollectionIfMissing(termoDeAdesaoCollection, termoDeAdesao);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a TermoDeAdesao to an array that doesn't contain it", () => {
        const termoDeAdesao: ITermoDeAdesao = sampleWithRequiredData;
        const termoDeAdesaoCollection: ITermoDeAdesao[] = [sampleWithPartialData];
        expectedResult = service.addTermoDeAdesaoToCollectionIfMissing(termoDeAdesaoCollection, termoDeAdesao);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(termoDeAdesao);
      });

      it('should add only unique TermoDeAdesao to an array', () => {
        const termoDeAdesaoArray: ITermoDeAdesao[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const termoDeAdesaoCollection: ITermoDeAdesao[] = [sampleWithRequiredData];
        expectedResult = service.addTermoDeAdesaoToCollectionIfMissing(termoDeAdesaoCollection, ...termoDeAdesaoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const termoDeAdesao: ITermoDeAdesao = sampleWithRequiredData;
        const termoDeAdesao2: ITermoDeAdesao = sampleWithPartialData;
        expectedResult = service.addTermoDeAdesaoToCollectionIfMissing([], termoDeAdesao, termoDeAdesao2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(termoDeAdesao);
        expect(expectedResult).toContain(termoDeAdesao2);
      });

      it('should accept null and undefined values', () => {
        const termoDeAdesao: ITermoDeAdesao = sampleWithRequiredData;
        expectedResult = service.addTermoDeAdesaoToCollectionIfMissing([], null, termoDeAdesao, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(termoDeAdesao);
      });

      it('should return initial array if no TermoDeAdesao is added', () => {
        const termoDeAdesaoCollection: ITermoDeAdesao[] = [sampleWithRequiredData];
        expectedResult = service.addTermoDeAdesaoToCollectionIfMissing(termoDeAdesaoCollection, undefined, null);
        expect(expectedResult).toEqual(termoDeAdesaoCollection);
      });
    });

    describe('compareTermoDeAdesao', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareTermoDeAdesao(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareTermoDeAdesao(entity1, entity2);
        const compareResult2 = service.compareTermoDeAdesao(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareTermoDeAdesao(entity1, entity2);
        const compareResult2 = service.compareTermoDeAdesao(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareTermoDeAdesao(entity1, entity2);
        const compareResult2 = service.compareTermoDeAdesao(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
