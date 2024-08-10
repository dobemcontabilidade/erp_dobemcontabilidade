import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IAvaliacaoContador } from '../avaliacao-contador.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../avaliacao-contador.test-samples';

import { AvaliacaoContadorService } from './avaliacao-contador.service';

const requireRestSample: IAvaliacaoContador = {
  ...sampleWithRequiredData,
};

describe('AvaliacaoContador Service', () => {
  let service: AvaliacaoContadorService;
  let httpMock: HttpTestingController;
  let expectedResult: IAvaliacaoContador | IAvaliacaoContador[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(AvaliacaoContadorService);
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

    it('should create a AvaliacaoContador', () => {
      const avaliacaoContador = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(avaliacaoContador).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a AvaliacaoContador', () => {
      const avaliacaoContador = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(avaliacaoContador).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a AvaliacaoContador', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of AvaliacaoContador', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a AvaliacaoContador', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addAvaliacaoContadorToCollectionIfMissing', () => {
      it('should add a AvaliacaoContador to an empty array', () => {
        const avaliacaoContador: IAvaliacaoContador = sampleWithRequiredData;
        expectedResult = service.addAvaliacaoContadorToCollectionIfMissing([], avaliacaoContador);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(avaliacaoContador);
      });

      it('should not add a AvaliacaoContador to an array that contains it', () => {
        const avaliacaoContador: IAvaliacaoContador = sampleWithRequiredData;
        const avaliacaoContadorCollection: IAvaliacaoContador[] = [
          {
            ...avaliacaoContador,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addAvaliacaoContadorToCollectionIfMissing(avaliacaoContadorCollection, avaliacaoContador);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a AvaliacaoContador to an array that doesn't contain it", () => {
        const avaliacaoContador: IAvaliacaoContador = sampleWithRequiredData;
        const avaliacaoContadorCollection: IAvaliacaoContador[] = [sampleWithPartialData];
        expectedResult = service.addAvaliacaoContadorToCollectionIfMissing(avaliacaoContadorCollection, avaliacaoContador);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(avaliacaoContador);
      });

      it('should add only unique AvaliacaoContador to an array', () => {
        const avaliacaoContadorArray: IAvaliacaoContador[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const avaliacaoContadorCollection: IAvaliacaoContador[] = [sampleWithRequiredData];
        expectedResult = service.addAvaliacaoContadorToCollectionIfMissing(avaliacaoContadorCollection, ...avaliacaoContadorArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const avaliacaoContador: IAvaliacaoContador = sampleWithRequiredData;
        const avaliacaoContador2: IAvaliacaoContador = sampleWithPartialData;
        expectedResult = service.addAvaliacaoContadorToCollectionIfMissing([], avaliacaoContador, avaliacaoContador2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(avaliacaoContador);
        expect(expectedResult).toContain(avaliacaoContador2);
      });

      it('should accept null and undefined values', () => {
        const avaliacaoContador: IAvaliacaoContador = sampleWithRequiredData;
        expectedResult = service.addAvaliacaoContadorToCollectionIfMissing([], null, avaliacaoContador, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(avaliacaoContador);
      });

      it('should return initial array if no AvaliacaoContador is added', () => {
        const avaliacaoContadorCollection: IAvaliacaoContador[] = [sampleWithRequiredData];
        expectedResult = service.addAvaliacaoContadorToCollectionIfMissing(avaliacaoContadorCollection, undefined, null);
        expect(expectedResult).toEqual(avaliacaoContadorCollection);
      });
    });

    describe('compareAvaliacaoContador', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareAvaliacaoContador(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareAvaliacaoContador(entity1, entity2);
        const compareResult2 = service.compareAvaliacaoContador(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareAvaliacaoContador(entity1, entity2);
        const compareResult2 = service.compareAvaliacaoContador(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareAvaliacaoContador(entity1, entity2);
        const compareResult2 = service.compareAvaliacaoContador(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
