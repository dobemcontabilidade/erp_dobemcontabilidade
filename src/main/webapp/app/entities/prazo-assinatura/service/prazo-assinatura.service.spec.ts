import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IPrazoAssinatura } from '../prazo-assinatura.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../prazo-assinatura.test-samples';

import { PrazoAssinaturaService } from './prazo-assinatura.service';

const requireRestSample: IPrazoAssinatura = {
  ...sampleWithRequiredData,
};

describe('PrazoAssinatura Service', () => {
  let service: PrazoAssinaturaService;
  let httpMock: HttpTestingController;
  let expectedResult: IPrazoAssinatura | IPrazoAssinatura[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(PrazoAssinaturaService);
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

    it('should create a PrazoAssinatura', () => {
      const prazoAssinatura = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(prazoAssinatura).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PrazoAssinatura', () => {
      const prazoAssinatura = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(prazoAssinatura).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PrazoAssinatura', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PrazoAssinatura', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a PrazoAssinatura', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addPrazoAssinaturaToCollectionIfMissing', () => {
      it('should add a PrazoAssinatura to an empty array', () => {
        const prazoAssinatura: IPrazoAssinatura = sampleWithRequiredData;
        expectedResult = service.addPrazoAssinaturaToCollectionIfMissing([], prazoAssinatura);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(prazoAssinatura);
      });

      it('should not add a PrazoAssinatura to an array that contains it', () => {
        const prazoAssinatura: IPrazoAssinatura = sampleWithRequiredData;
        const prazoAssinaturaCollection: IPrazoAssinatura[] = [
          {
            ...prazoAssinatura,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addPrazoAssinaturaToCollectionIfMissing(prazoAssinaturaCollection, prazoAssinatura);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PrazoAssinatura to an array that doesn't contain it", () => {
        const prazoAssinatura: IPrazoAssinatura = sampleWithRequiredData;
        const prazoAssinaturaCollection: IPrazoAssinatura[] = [sampleWithPartialData];
        expectedResult = service.addPrazoAssinaturaToCollectionIfMissing(prazoAssinaturaCollection, prazoAssinatura);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(prazoAssinatura);
      });

      it('should add only unique PrazoAssinatura to an array', () => {
        const prazoAssinaturaArray: IPrazoAssinatura[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const prazoAssinaturaCollection: IPrazoAssinatura[] = [sampleWithRequiredData];
        expectedResult = service.addPrazoAssinaturaToCollectionIfMissing(prazoAssinaturaCollection, ...prazoAssinaturaArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const prazoAssinatura: IPrazoAssinatura = sampleWithRequiredData;
        const prazoAssinatura2: IPrazoAssinatura = sampleWithPartialData;
        expectedResult = service.addPrazoAssinaturaToCollectionIfMissing([], prazoAssinatura, prazoAssinatura2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(prazoAssinatura);
        expect(expectedResult).toContain(prazoAssinatura2);
      });

      it('should accept null and undefined values', () => {
        const prazoAssinatura: IPrazoAssinatura = sampleWithRequiredData;
        expectedResult = service.addPrazoAssinaturaToCollectionIfMissing([], null, prazoAssinatura, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(prazoAssinatura);
      });

      it('should return initial array if no PrazoAssinatura is added', () => {
        const prazoAssinaturaCollection: IPrazoAssinatura[] = [sampleWithRequiredData];
        expectedResult = service.addPrazoAssinaturaToCollectionIfMissing(prazoAssinaturaCollection, undefined, null);
        expect(expectedResult).toEqual(prazoAssinaturaCollection);
      });
    });

    describe('comparePrazoAssinatura', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.comparePrazoAssinatura(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.comparePrazoAssinatura(entity1, entity2);
        const compareResult2 = service.comparePrazoAssinatura(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.comparePrazoAssinatura(entity1, entity2);
        const compareResult2 = service.comparePrazoAssinatura(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.comparePrazoAssinatura(entity1, entity2);
        const compareResult2 = service.comparePrazoAssinatura(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
