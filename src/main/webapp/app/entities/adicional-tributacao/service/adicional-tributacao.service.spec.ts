import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IAdicionalTributacao } from '../adicional-tributacao.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../adicional-tributacao.test-samples';

import { AdicionalTributacaoService } from './adicional-tributacao.service';

const requireRestSample: IAdicionalTributacao = {
  ...sampleWithRequiredData,
};

describe('AdicionalTributacao Service', () => {
  let service: AdicionalTributacaoService;
  let httpMock: HttpTestingController;
  let expectedResult: IAdicionalTributacao | IAdicionalTributacao[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(AdicionalTributacaoService);
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

    it('should create a AdicionalTributacao', () => {
      const adicionalTributacao = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(adicionalTributacao).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a AdicionalTributacao', () => {
      const adicionalTributacao = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(adicionalTributacao).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a AdicionalTributacao', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of AdicionalTributacao', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a AdicionalTributacao', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addAdicionalTributacaoToCollectionIfMissing', () => {
      it('should add a AdicionalTributacao to an empty array', () => {
        const adicionalTributacao: IAdicionalTributacao = sampleWithRequiredData;
        expectedResult = service.addAdicionalTributacaoToCollectionIfMissing([], adicionalTributacao);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(adicionalTributacao);
      });

      it('should not add a AdicionalTributacao to an array that contains it', () => {
        const adicionalTributacao: IAdicionalTributacao = sampleWithRequiredData;
        const adicionalTributacaoCollection: IAdicionalTributacao[] = [
          {
            ...adicionalTributacao,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addAdicionalTributacaoToCollectionIfMissing(adicionalTributacaoCollection, adicionalTributacao);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a AdicionalTributacao to an array that doesn't contain it", () => {
        const adicionalTributacao: IAdicionalTributacao = sampleWithRequiredData;
        const adicionalTributacaoCollection: IAdicionalTributacao[] = [sampleWithPartialData];
        expectedResult = service.addAdicionalTributacaoToCollectionIfMissing(adicionalTributacaoCollection, adicionalTributacao);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(adicionalTributacao);
      });

      it('should add only unique AdicionalTributacao to an array', () => {
        const adicionalTributacaoArray: IAdicionalTributacao[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const adicionalTributacaoCollection: IAdicionalTributacao[] = [sampleWithRequiredData];
        expectedResult = service.addAdicionalTributacaoToCollectionIfMissing(adicionalTributacaoCollection, ...adicionalTributacaoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const adicionalTributacao: IAdicionalTributacao = sampleWithRequiredData;
        const adicionalTributacao2: IAdicionalTributacao = sampleWithPartialData;
        expectedResult = service.addAdicionalTributacaoToCollectionIfMissing([], adicionalTributacao, adicionalTributacao2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(adicionalTributacao);
        expect(expectedResult).toContain(adicionalTributacao2);
      });

      it('should accept null and undefined values', () => {
        const adicionalTributacao: IAdicionalTributacao = sampleWithRequiredData;
        expectedResult = service.addAdicionalTributacaoToCollectionIfMissing([], null, adicionalTributacao, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(adicionalTributacao);
      });

      it('should return initial array if no AdicionalTributacao is added', () => {
        const adicionalTributacaoCollection: IAdicionalTributacao[] = [sampleWithRequiredData];
        expectedResult = service.addAdicionalTributacaoToCollectionIfMissing(adicionalTributacaoCollection, undefined, null);
        expect(expectedResult).toEqual(adicionalTributacaoCollection);
      });
    });

    describe('compareAdicionalTributacao', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareAdicionalTributacao(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareAdicionalTributacao(entity1, entity2);
        const compareResult2 = service.compareAdicionalTributacao(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareAdicionalTributacao(entity1, entity2);
        const compareResult2 = service.compareAdicionalTributacao(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareAdicionalTributacao(entity1, entity2);
        const compareResult2 = service.compareAdicionalTributacao(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
