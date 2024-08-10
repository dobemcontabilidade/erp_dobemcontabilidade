import { TestBed } from '@angular/core/testing';
import { provideHttpClientTesting, HttpTestingController } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { ITermoAdesaoContador } from '../termo-adesao-contador.model';
import {
  sampleWithRequiredData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithFullData,
} from '../termo-adesao-contador.test-samples';

import { TermoAdesaoContadorService, RestTermoAdesaoContador } from './termo-adesao-contador.service';

const requireRestSample: RestTermoAdesaoContador = {
  ...sampleWithRequiredData,
  dataAdesao: sampleWithRequiredData.dataAdesao?.toJSON(),
};

describe('TermoAdesaoContador Service', () => {
  let service: TermoAdesaoContadorService;
  let httpMock: HttpTestingController;
  let expectedResult: ITermoAdesaoContador | ITermoAdesaoContador[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(TermoAdesaoContadorService);
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

    it('should create a TermoAdesaoContador', () => {
      const termoAdesaoContador = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(termoAdesaoContador).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a TermoAdesaoContador', () => {
      const termoAdesaoContador = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(termoAdesaoContador).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a TermoAdesaoContador', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of TermoAdesaoContador', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a TermoAdesaoContador', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addTermoAdesaoContadorToCollectionIfMissing', () => {
      it('should add a TermoAdesaoContador to an empty array', () => {
        const termoAdesaoContador: ITermoAdesaoContador = sampleWithRequiredData;
        expectedResult = service.addTermoAdesaoContadorToCollectionIfMissing([], termoAdesaoContador);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(termoAdesaoContador);
      });

      it('should not add a TermoAdesaoContador to an array that contains it', () => {
        const termoAdesaoContador: ITermoAdesaoContador = sampleWithRequiredData;
        const termoAdesaoContadorCollection: ITermoAdesaoContador[] = [
          {
            ...termoAdesaoContador,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addTermoAdesaoContadorToCollectionIfMissing(termoAdesaoContadorCollection, termoAdesaoContador);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a TermoAdesaoContador to an array that doesn't contain it", () => {
        const termoAdesaoContador: ITermoAdesaoContador = sampleWithRequiredData;
        const termoAdesaoContadorCollection: ITermoAdesaoContador[] = [sampleWithPartialData];
        expectedResult = service.addTermoAdesaoContadorToCollectionIfMissing(termoAdesaoContadorCollection, termoAdesaoContador);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(termoAdesaoContador);
      });

      it('should add only unique TermoAdesaoContador to an array', () => {
        const termoAdesaoContadorArray: ITermoAdesaoContador[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const termoAdesaoContadorCollection: ITermoAdesaoContador[] = [sampleWithRequiredData];
        expectedResult = service.addTermoAdesaoContadorToCollectionIfMissing(termoAdesaoContadorCollection, ...termoAdesaoContadorArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const termoAdesaoContador: ITermoAdesaoContador = sampleWithRequiredData;
        const termoAdesaoContador2: ITermoAdesaoContador = sampleWithPartialData;
        expectedResult = service.addTermoAdesaoContadorToCollectionIfMissing([], termoAdesaoContador, termoAdesaoContador2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(termoAdesaoContador);
        expect(expectedResult).toContain(termoAdesaoContador2);
      });

      it('should accept null and undefined values', () => {
        const termoAdesaoContador: ITermoAdesaoContador = sampleWithRequiredData;
        expectedResult = service.addTermoAdesaoContadorToCollectionIfMissing([], null, termoAdesaoContador, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(termoAdesaoContador);
      });

      it('should return initial array if no TermoAdesaoContador is added', () => {
        const termoAdesaoContadorCollection: ITermoAdesaoContador[] = [sampleWithRequiredData];
        expectedResult = service.addTermoAdesaoContadorToCollectionIfMissing(termoAdesaoContadorCollection, undefined, null);
        expect(expectedResult).toEqual(termoAdesaoContadorCollection);
      });
    });

    describe('compareTermoAdesaoContador', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareTermoAdesaoContador(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareTermoAdesaoContador(entity1, entity2);
        const compareResult2 = service.compareTermoAdesaoContador(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareTermoAdesaoContador(entity1, entity2);
        const compareResult2 = service.compareTermoAdesaoContador(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareTermoAdesaoContador(entity1, entity2);
        const compareResult2 = service.compareTermoAdesaoContador(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
